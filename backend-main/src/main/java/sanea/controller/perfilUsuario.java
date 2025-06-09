package sanea.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import sanea.model.Usuario;
import sanea.dao.GetUserNameDao;
import sanea.util.CorsHandler;
import sanea.util.JwtUtil;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

public class perfilUsuario implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Adiciona headers CORS
        CorsHandler.handleCors(exchange);

        // Verifica se é uma requisição OPTIONS (preflight)
        if (CorsHandler.isPreflightRequest(exchange)) {
            CorsHandler.handlePreflight(exchange);
            return;
        }

        // Verifica o token JWT
        String authHeader = exchange.getRequestHeaders().getFirst("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            String response = "{\"error\": \"Token não fornecido\"}";
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(401, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
            return;
        }

        String token = authHeader.substring(7); // Remove "Bearer "
        try {
            String userId = JwtUtil.validateToken(token);
            exchange.setAttribute("userId", userId);
        } catch (Exception e) {
            String response = "{\"error\": \"Token inválido\"}";
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(401, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
            return;
        }

        if (exchange.getRequestMethod().equals("GET")) {
            handleGet(exchange);
        } else if (exchange.getRequestMethod().equals("PUT")) {
            handlePut(exchange);
        } else {
            exchange.sendResponseHeaders(405, -1); // Method Not Allowed
        }
    }

    private void handleGet(HttpExchange exchange) throws IOException {
        try {
            String userId = (String) exchange.getAttribute("userId");
            if (userId == null) {
                throw new Exception("ID do usuário não encontrado");
            }

            Usuario usuario = new Usuario();
            usuario.setIdUsuario(Integer.parseInt(userId));
            
            // Busca os dados do usuário
            usuario = usuario.Username();
            if (usuario == null) {
                throw new Exception("Usuário não encontrado");
            }
            
            // Constrói o objeto JSON com todos os dados do usuário
            JsonObjectBuilder jsonBuilder = Json.createObjectBuilder()
                .add("id", usuario.getIdUsuario())
                .add("nome", usuario.getNome())
                .add("email", usuario.getEmail())
                .add("telefone", usuario.getTelefone())
                .add("cpf", usuario.getCpf());
            
            String response = jsonBuilder.build().toString();
            
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        } catch (Exception e) {
            System.err.println("[perfilUsuario] Erro ao buscar perfil: " + e.getMessage());
            e.printStackTrace();
            
            String response = "{\"error\": \"Erro ao buscar perfil: " + e.getMessage() + "\"}";
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(500, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }

    private void handlePut(HttpExchange exchange) throws IOException {
        try {
            String userId = (String) exchange.getAttribute("userId");
            if (userId == null) {
                throw new Exception("ID do usuário não encontrado");
            }

            String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            
            JsonReader jsonReader = Json.createReader(new StringReader(requestBody));
            JsonObject jsonObject = jsonReader.readObject();
            
            Usuario usuario = new Usuario();
            usuario.setIdUsuario(Integer.parseInt(userId));
            usuario.setNome(jsonObject.getString("nome"));
            usuario.setEmail(jsonObject.getString("email"));
            usuario.setTelefone(jsonObject.getString("telefone"));
            
            usuario.alterar();
            
            String response = "{\"message\": \"Perfil atualizado com sucesso\"}";
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        } catch (Exception e) {
            System.err.println("[perfilUsuario] Erro ao atualizar perfil: " + e.getMessage());
            e.printStackTrace();
            
            String response = "{\"error\": \"Erro ao atualizar perfil: " + e.getMessage() + "\"}";
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(500, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }
} 