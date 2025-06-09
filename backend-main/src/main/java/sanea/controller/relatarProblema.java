package sanea.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import sanea.model.Relato;
import sanea.dao.CadastrarRelatoDao;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class relatarProblema implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().equals("POST")) {
            try {
                String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                Map<String, String> params = parseFormData(requestBody);
                
                String userId = (String) exchange.getAttribute("userId");
                
                Relato relato = new Relato();
                relato.setIdUsuario(Integer.parseInt(userId));
                relato.setTipoProblema(params.get("tipo_problema"));
                relato.setDescricao(params.get("descricao"));
                relato.setDataOcorrido(params.get("data_ocorrido"));
                relato.setCep(params.get("cep"));
                relato.setRua(params.get("rua"));
                relato.setNumero(params.get("numero"));
                relato.setBairro(params.get("bairro"));
                relato.setCidade(params.get("cidade"));
                relato.setEstado(params.get("estado"));
                
                boolean sucesso = relato.cadastrar();
                
                String response;
                int statusCode;
                
                if (sucesso) {
                    response = "{\"message\": \"Problema relatado com sucesso\"}";
                    statusCode = 201;
                } else {
                    response = "{\"error\": \"Erro ao relatar problema\"}";
                    statusCode = 400;
                }
                
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(statusCode, response.getBytes().length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            } catch (Exception e) {
                String response = "{\"error\": \"Erro ao relatar problema: " + e.getMessage() + "\"}";
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(500, response.getBytes().length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            }
        } else {
            exchange.sendResponseHeaders(405, -1); // Method Not Allowed
        }
    }
    
    private Map<String, String> parseFormData(String formData) {
        Map<String, String> params = new HashMap<>();
        String[] pairs = formData.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                String key = URLDecoder.decode(keyValue[0], StandardCharsets.UTF_8);
                String value = URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8);
                params.put(key, value);
            }
        }
        return params;
    }
} 