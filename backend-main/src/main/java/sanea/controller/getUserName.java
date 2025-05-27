package sanea.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import sanea.model.Usuario;

import java.io.IOException;

/**
 * Servlet implementation class getUserName
 */
@WebServlet("/getUserName")
public class getUserName extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getUserName() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		Usuario usuario = new Usuario();
		
		
		HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("userId") != null) {
            String UserId = (String) session.getAttribute("userId");
             usuario.setIdUsuario(Integer.parseInt(UserId));
             String UserName = usuario.Username();
            
            
            response.setContentType("application/json");
            response.getWriter().write("{\"userName\": \"" + UserName + "\"}");
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Usuário não está logado
        }

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		
	}

}
