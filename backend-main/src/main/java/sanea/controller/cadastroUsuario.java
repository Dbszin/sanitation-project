package sanea.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import sanea.model.Usuario;
import sanea.util.CorsHandler;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

public class cadastroUsuario implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Adiciona headers CORS
        CorsHandler.handleCors(exchange);

        // Verifica se é uma requisição OPTIONS (preflight)
        if (CorsHandler.isPreflightRequest(exchange)) {
            CorsHandler.handlePreflight(exchange);
            return;
        }
        
        if (!exchange.getRequestMethod().equals("POST")) {
            System.out.println("[cadastroUsuario] Método não permitido: " + exchange.getRequestMethod());
            exchange.sendResponseHeaders(405, -1); // Method Not Allowed
            return;
        }
        
        try {
            // Lê o corpo da requisição como JSON
            String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            System.out.println("[cadastroUsuario] Corpo da requisição: " + requestBody);
            
            JsonReader jsonReader = Json.createReader(new StringReader(requestBody));
            JsonObject jsonObject = jsonReader.readObject();
            
            Usuario usuario = new Usuario();
            usuario.setNome(jsonObject.getString("nome"));
            usuario.setEmail(jsonObject.getString("email"));
            usuario.setSenha(jsonObject.getString("senha"));
            usuario.setTelefone(jsonObject.getString("telefone"));
            usuario.setCpf(jsonObject.getString("cpf"));
            
            boolean sucesso = usuario.cadastrar();
            
            String response;
            int statusCode;
            
            if (sucesso) {
                response = "{\"message\": \"Usuário cadastrado com sucesso\"}";
                statusCode = 201; // Created
            } else {
                response = "{\"error\": \"Erro ao cadastrar usuário\"}";
                statusCode = 400; // Bad Request
            }
            
            // Configura os headers da resposta
            exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
            
            // Envia os headers e o corpo da resposta
            exchange.sendResponseHeaders(statusCode, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
            
            System.out.println("[cadastroUsuario] Resposta enviada com sucesso");
        } catch (Exception e) {
            System.err.println("[cadastroUsuario] Erro ao processar requisição: " + e.getMessage());
            e.printStackTrace();
            
            // Configura os headers da resposta de erro
            exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
            
            String response = "{\"error\": \"Erro ao cadastrar usuário: " + e.getMessage() + "\"}";
            exchange.sendResponseHeaders(500, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }
} 