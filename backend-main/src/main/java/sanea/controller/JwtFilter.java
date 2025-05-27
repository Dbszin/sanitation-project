package sanea.controller;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Cookie;
import java.io.IOException;

public class JwtFilter implements Filter {
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        // Recuperando token do cabeçalho
        String token = httpRequest.getHeader("Authorization");

        // Se o token não estiver no cabeçalho, tenta recuperar do Cookie
        if (token == null || !token.startsWith("Bearer ")) {
            Cookie[] cookies = httpRequest.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("authToken".equals(cookie.getName())) {
                        token = cookie.getValue();
                        break;
                    }
                }
            }
        } else {
            token = token.substring(7); // Remove "Bearer "
        }

        // Verifica se temos um token válido
        if (token != null) {
            int userId = JwtUtil.validateToken(token); // Valida o token e recupera ID

            request.setAttribute("userId", userId); // Disponibiliza ID para outros servlets
        } else {
            response.getWriter().write("{\"error\": \"Usuário não autenticado\"}");
            return;
        }

        chain.doFilter(request, response);
    }
}