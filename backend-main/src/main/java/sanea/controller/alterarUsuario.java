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
 * Servlet implementation class alterarUsuario
 */
@WebServlet("/alterarUsuario")
public class alterarUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public alterarUsuario() {
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
		String action = request.getParameter("formAction");
		System.out.println(action);
		Usuario usuario = new Usuario();
		
		
		HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("userId") != null) {
        	String UserId = (String) session.getAttribute("userId");
        	usuario.setIdUsuario(Integer.parseInt(UserId));
             
        	switch (action) {
				case "/alterarEmail":
					usuario.setEmail(request.getParameter("novo-email"));
					usuario.setSenha(request.getParameter("senha-atual"));
					usuario.alterar();
				break;
				case "/alterarTelefone":
					usuario.setTelefone(request.getParameter("novo-telefone"));
					usuario.setSenha(request.getParameter("senha-atual"));
					usuario.alterar();
				break;
				case "/alterarSenha":
					String SenhaAtual = (request.getParameter("senha-atual"));
					String NovaSenha = (request.getParameter("nova-senha"));
					String RepetirSenha = (request.getParameter("repetir-nova-senha"));
					if (NovaSenha.equals(RepetirSenha)) {
						usuario.setSenha(request.getParameter("nova-senha"));
						usuario.alterarSenha(SenhaAtual);
					} else {
						response.getWriter().write("As senhas não conferem...");
					}
				break;
				default:
		        response.getWriter().write("Ação inválida");
		}
            
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Usuário não está logado
        }
		
	}

}
