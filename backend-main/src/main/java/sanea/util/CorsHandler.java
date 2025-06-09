package sanea.util;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;

public class CorsHandler {
    public static void handleCors(HttpExchange exchange) throws IOException {
        // Remove headers CORS existentes para evitar duplicação
        exchange.getResponseHeaders().remove("Access-Control-Allow-Origin");
        exchange.getResponseHeaders().remove("Access-Control-Allow-Methods");
        exchange.getResponseHeaders().remove("Access-Control-Allow-Headers");
        exchange.getResponseHeaders().remove("Access-Control-Allow-Credentials");
        exchange.getResponseHeaders().remove("Access-Control-Max-Age");

        // Adiciona os headers CORS
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "http://localhost:3000");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type, Accept, Authorization, X-Requested-With");
        exchange.getResponseHeaders().add("Access-Control-Allow-Credentials", "true");
        exchange.getResponseHeaders().add("Access-Control-Max-Age", "3600");
    }

    public static boolean isPreflightRequest(HttpExchange exchange) {
        return exchange.getRequestMethod().equals("OPTIONS");
    }

    public static void handlePreflight(HttpExchange exchange) throws IOException {
        handleCors(exchange);
        exchange.sendResponseHeaders(204, -1);
        exchange.close();
    }
} 