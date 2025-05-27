package sanea.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import sanea.model.Usuario;

import java.io.IOException;

/**
 * Servlet implementation class loginUsuario
 */
@WebServlet("/loginUsuario")
public class loginUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public loginUsuario() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		System.out.println("[LOG] Usuario Logando... ");
		
		Usuario usuario = new Usuario();
		
		usuario.setEmail(request.getParameter("email"));
		usuario.setSenha(request.getParameter("senha"));
		
		int UserID = Integer.parseInt(usuario.logar());
		
		
		
		if (UserID > 0) { // Se o usuário for válido
            String token = JwtUtil.generateToken(UserID); // Gera JWT
            
            Cookie jwtCookie = new Cookie("authToken", token);
            jwtCookie.setHttpOnly(true); // Impede acesso via JS
            jwtCookie.setSecure(true); // Apenas via HTTPS
            jwtCookie.setMaxAge(3600); // Expira em 1 hora
            response.addCookie(jwtCookie);
            
            response.setContentType("application/json");
            System.out.println("[LOG] ID do USUARIO LOGADO: " + UserID);
        }
		
	}

}
