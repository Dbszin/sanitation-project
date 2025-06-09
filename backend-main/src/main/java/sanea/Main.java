package sanea;

import com.sun.net.httpserver.HttpServer;
import sanea.controller.*;
import sanea.util.AuthMiddleware;
import sanea.util.CorsHandler;
import sanea.util.DatabaseInitializer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        try {
            // Inicializa o banco de dados
            DatabaseInitializer.initialize();

            System.out.println("[Main] Iniciando servidor na porta 8080...");
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
            
            // Configura o executor para lidar com múltiplas requisições
            server.setExecutor(Executors.newFixedThreadPool(10));
            
            // Adiciona os handlers com CORS e Autenticação
            System.out.println("[Main] Registrando endpoint /cadastroUsuario");
            server.createContext("/cadastroUsuario", new CorsHandler(new cadastroUsuario()));
            System.out.println("[Main] Registrando endpoint /loginUsuario");
            server.createContext("/loginUsuario", new CorsHandler(new loginUsuario()));
            
            // Rotas protegidas
            System.out.println("[Main] Registrando rotas protegidas");
            server.createContext("/perfil", new CorsHandler(new AuthMiddleware(new perfilUsuario())));
            server.createContext("/relatar", new CorsHandler(new AuthMiddleware(new relatar())));
            
            // Endpoint de teste para CORS
            System.out.println("[Main] Registrando endpoint de teste /testeCors");
            server.createContext("/testeCors", new CorsHandler(exchange -> {
                System.out.println("[testeCors] Recebendo requisição " + exchange.getRequestMethod());
                
                if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                    System.out.println("[testeCors] Respondendo preflight");
                    exchange.sendResponseHeaders(204, -1);
                    return;
                }
                
                String resp = "{\"teste\":\"ok\"}";
                byte[] bytes = resp.getBytes(java.nio.charset.StandardCharsets.UTF_8);
                exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
                exchange.sendResponseHeaders(200, bytes.length);
                try (var os = exchange.getResponseBody()) {
                    os.write(bytes);
                }
                System.out.println("[testeCors] Resposta enviada com sucesso");
            }));
            
            server.start();
            System.out.println("[Main] Servidor iniciado com sucesso na porta 8080");
        } catch (IOException e) {
            System.err.println("[Main] Erro ao iniciar o servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    static class CorsHandler implements com.sun.net.httpserver.HttpHandler {
        private final com.sun.net.httpserver.HttpHandler handler;
        
        public CorsHandler(com.sun.net.httpserver.HttpHandler handler) {
            this.handler = handler;
        }
        
        @Override
        public void handle(com.sun.net.httpserver.HttpExchange exchange) throws IOException {
            System.out.println("[CorsHandler] Método=" + exchange.getRequestMethod() + " URI=" + exchange.getRequestURI());
            
            // Adiciona os headers CORS para todas as requisições
            exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "http://localhost:3000");
            exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type, Authorization, Accept, X-Requested-With, Origin");
            exchange.getResponseHeaders().set("Access-Control-Allow-Credentials", "true");
            exchange.getResponseHeaders().set("Access-Control-Max-Age", "3600");
            exchange.getResponseHeaders().set("Access-Control-Expose-Headers", "Authorization");
            
            // Se for uma requisição OPTIONS (preflight), retorna 204 No Content
            if (exchange.getRequestMethod().equals("OPTIONS")) {
                System.out.println("[CorsHandler] Tratando requisição OPTIONS (preflight)");
                exchange.sendResponseHeaders(204, -1);
                exchange.close();
                return;
            }
            
            try {
                System.out.println("[CorsHandler] Encaminhando requisição para o handler original");
                // Chama o handler original
                handler.handle(exchange);
                System.out.println("[CorsHandler] Handler original executado com sucesso");
            } catch (Exception e) {
                System.err.println("[CorsHandler] Erro ao executar handler original: " + e.getMessage());
                e.printStackTrace();
                
                // Em caso de erro, garante que os headers CORS ainda estejam presentes
                exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "http://localhost:3000");
                exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
                exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type, Authorization, Accept, X-Requested-With, Origin");
                exchange.getResponseHeaders().set("Access-Control-Allow-Credentials", "true");
                exchange.getResponseHeaders().set("Access-Control-Max-Age", "3600");
                exchange.getResponseHeaders().set("Access-Control-Expose-Headers", "Authorization");
                
                String errorResponse = "{\"error\": \"" + e.getMessage() + "\"}";
                exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
                exchange.sendResponseHeaders(500, errorResponse.getBytes().length);
                try (var os = exchange.getResponseBody()) {
                    os.write(errorResponse.getBytes());
                }
            }
        }
    }
} 