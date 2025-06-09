package sanea.util;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;

public class AuthMiddleware implements HttpHandler {
    private final HttpHandler next;
    
    public AuthMiddleware(HttpHandler next) {
        this.next = next;
    }
    
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Ignora autenticação para rotas públicas
        if (exchange.getRequestURI().getPath().equals("/loginUsuario") || 
            exchange.getRequestURI().getPath().equals("/cadastroUsuario")) {
            next.handle(exchange);
            return;
        }
        
        // Verifica o token
        String authHeader = exchange.getRequestHeaders().getFirst("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            String response = "{\"error\": \"Token não fornecido\"}";
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(401, response.getBytes().length);
            try (var os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
            return;
        }
        
        String token = authHeader.substring(7);
        String userId = JwtUtil.validateToken(token);
        
        if (userId == null) {
            String response = "{\"error\": \"Token inválido\"}";
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(401, response.getBytes().length);
            try (var os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
            return;
        }
        
        // Adiciona o userId ao contexto da requisição
        exchange.setAttribute("userId", userId);
        
        // Continua com o handler original
        next.handle(exchange);
    }
} 