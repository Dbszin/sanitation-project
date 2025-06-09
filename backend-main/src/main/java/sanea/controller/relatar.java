package sanea.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import sanea.model.Relato;
import sanea.util.CorsHandler;
import sanea.util.JwtUtil;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

public class relatar implements HttpHandler {
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

        if (exchange.getRequestMethod().equals("POST")) {
            handlePost(exchange);
        } else {
            exchange.sendResponseHeaders(405, -1); // Method Not Allowed
        }
    }

    private void handlePost(HttpExchange exchange) throws IOException {
        try {
            String userId = (String) exchange.getAttribute("userId");
            if (userId == null) {
                throw new Exception("ID do usuário não encontrado");
            }

            String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            
            JsonReader jsonReader = Json.createReader(new StringReader(requestBody));
            JsonObject jsonObject = jsonReader.readObject();
            
            Relato relato = new Relato();
            relato.setIdUsuario(Integer.parseInt(userId));
            relato.setTipoProblema(jsonObject.getString("tipo_problema"));
            relato.setDescricao(jsonObject.getString("descricao"));
            relato.setDataOcorrido(jsonObject.getString("data_ocorrido"));
            relato.setCep(jsonObject.getString("cep"));
            relato.setRua(jsonObject.getString("rua"));
            relato.setNumero(jsonObject.getString("numero"));
            relato.setBairro(jsonObject.getString("bairro"));
            relato.setCidade(jsonObject.getString("cidade"));
            relato.setEstado(jsonObject.getString("estado"));
            
            boolean success = relato.cadastrar();
            
            if (success) {
                String response = "{\"message\": \"Relato cadastrado com sucesso\"}";
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, response.getBytes().length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            } else {
                throw new Exception("Erro ao cadastrar relato");
            }
        } catch (Exception e) {
            System.err.println("[relatar] Erro ao cadastrar relato: " + e.getMessage());
            e.printStackTrace();
            
            String response = "{\"error\": \"Erro ao cadastrar relato: " + e.getMessage() + "\"}";
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(500, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }
} 