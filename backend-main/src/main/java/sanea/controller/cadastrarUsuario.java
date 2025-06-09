package sanea.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import sanea.model.Usuario;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class cadastrarUsuario implements HttpHandler {
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		if (exchange.getRequestMethod().equals("POST")) {
			// Lê o corpo da requisição
			String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
			Map<String, String> params = parseFormData(requestBody);
			
			try {
				Usuario usuario = new Usuario();
				usuario.setNome(params.get("nome"));
				usuario.setEmail(params.get("email"));
				usuario.setTelefone(params.get("telefone"));
				usuario.setCpf(params.get("cpf"));
				usuario.setSenha(params.get("senha"));
				
				System.out.println(usuario.getNome());
				System.out.println(usuario.getEmail());
				System.out.println(usuario.getTelefone());
				System.out.println(usuario.getCpf());
				System.out.println(usuario.getSenha());
				
				usuario.cadastrar();
				
				// Resposta de sucesso
				String response = "{\"message\": \"Usuário cadastrado com sucesso\"}";
				exchange.getResponseHeaders().set("Content-Type", "application/json");
				exchange.sendResponseHeaders(200, response.getBytes().length);
				try (OutputStream os = exchange.getResponseBody()) {
					os.write(response.getBytes());
				}
			} catch (Exception e) {
				// Resposta de erro
				String response = "{\"error\": \"Erro ao cadastrar usuário: " + e.getMessage() + "\"}";
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
