package sanea.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import sanea.model.Usuario;
import sanea.util.CorsHandler;
import sanea.util.JwtUtil;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

public class loginUsuario implements HttpHandler {
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
            System.out.println("[loginUsuario] Método não permitido: " + exchange.getRequestMethod());
            exchange.sendResponseHeaders(405, -1); // Method Not Allowed
            return;
        }

        try {
            // Lê o corpo da requisição como JSON
            String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            System.out.println("[loginUsuario] Corpo da requisição: " + requestBody);
            
            JsonReader jsonReader = Json.createReader(new StringReader(requestBody));
            JsonObject jsonObject = jsonReader.readObject();
            
            // Valida os campos obrigatórios
            if (!jsonObject.containsKey("email") || !jsonObject.containsKey("senha")) {
                String response = "{\"error\": \"Email e senha são obrigatórios\"}";
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(400, response.getBytes().length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
                return;
            }
            
            String email = jsonObject.getString("email");
            String senha = jsonObject.getString("senha");
            
            // Valida o formato do email
            if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                String response = "{\"error\": \"Formato de email inválido\"}";
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(400, response.getBytes().length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
                return;
            }
            
            // Valida o tamanho mínimo da senha
            if (senha.length() < 6) {
                String response = "{\"error\": \"A senha deve ter pelo menos 6 caracteres\"}";
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(400, response.getBytes().length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
                return;
            }
            
            Usuario usuario = new Usuario();
            usuario.setEmail(email);
            usuario.setSenha(senha);
            
            String userId = usuario.logar();
            
            String response;
            int statusCode;
            
            if (userId != null) {
                String token = JwtUtil.generateToken(userId);
                response = String.format(
                    "{\"message\": \"Login realizado com sucesso\", \"token\": \"%s\", \"userId\": \"%s\"}",
                    token,
                    userId
                );
                statusCode = 200;
                System.out.println("[loginUsuario] Login bem-sucedido para userId: " + userId); // Debug
            } else {
                response = "{\"error\": \"Email ou senha inválidos\"}";
                statusCode = 401;
                System.out.println("[loginUsuario] Tentativa de login falhou - credenciais inválidas"); // Debug
            }
            
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(statusCode, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
            System.out.println("[loginUsuario] Resposta enviada: " + response); // Debug
        } catch (Exception e) {
            System.err.println("[loginUsuario] Erro ao processar requisição: " + e.getMessage());
            e.printStackTrace();
            
            String response = "{\"error\": \"Erro ao fazer login: " + e.getMessage() + "\"}";
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(500, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }
}
