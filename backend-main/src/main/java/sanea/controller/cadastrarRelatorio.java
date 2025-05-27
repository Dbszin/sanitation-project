package sanea.controller;

import jakarta.servlet.ServletException;
import org.apache.commons.text.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import sanea.model.Reclamacoes;
import sanea.model.RegioesSantos;
import sanea.model.TiposProblema;
import sanea.model.Usuario;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * Servlet implementation class cadastrarRelatorio
 */
@WebServlet("/cadastrarRelatorio")
public class cadastrarRelatorio extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIR = "img/";

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public cadastrarRelatorio() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		System.out.println("REQUISICAO CHAMADA");
		TiposProblema TProblema = new TiposProblema();
		RegioesSantos RSantos = new RegioesSantos();
		
		Part filePart = request.getPart("file");
        String fileName = filePart.getSubmittedFileName();
        String filePath = UPLOAD_DIR + fileName;

        // Criar diretório se não existir
        File directory = new File(UPLOAD_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Salvar arquivo
        File file = new File(filePath);
        Files.copy(filePart.getInputStream(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);

        // Gerar URL da imagem
        String fileUrl = "/img/" + fileName;
        
        
        
        Reclamacoes reclamacoes = new Reclamacoes();
        
        int UserID = (int) request.getAttribute("userId");
        
        TProblema.setNome(request.getParameter("tipo_problema"));
        int TProblemaID = TProblema.getProblemaID();
        
        RSantos.setNome(request.getParameter("bairro"));
        int RSantosID = RSantos.getRegiaoID();
        
        String Rua = request.getParameter("rua");
        String Numero = request.getParameter("numero");
        String Bairro = request.getParameter("bairro");
        String Cidade = request.getParameter("cidade");
        String Estado = request.getParameter("estado");
        String Endereco = formatarEndereco(Rua, Numero, Bairro, Cidade, Estado);
        
        String Descricao = request.getParameter("descricao");
        
        RSantos.setId(RSantosID);
        BigDecimal[] LatiLong = new BigDecimal[2];
        LatiLong = RSantos.getLatitudeLongitude();
        BigDecimal Latitude = LatiLong [0];
        BigDecimal Longitude = LatiLong [1];
        
        String Status = "pendente";
		
		reclamacoes.setIdUsuario(UserID);
		reclamacoes.setIdTipoProblema(TProblemaID);
		reclamacoes.setIdRegiao(RSantosID);
		reclamacoes.setEndereco(Endereco);
		reclamacoes.setDescricao(Descricao);
		reclamacoes.setLatitude(Latitude);
		reclamacoes.setLongitude(Longitude);
		reclamacoes.setStatus(Status);
		reclamacoes.setAnexo(fileUrl);
		
		
		
		reclamacoes.cadastrar();

	}
	
	public String formatarEndereco(String rua, String numero, String bairro, String cidade, String estado) {
		return rua + ", " + numero + " - " + bairro + ", " + cidade + " - " + estado;
	}

}
