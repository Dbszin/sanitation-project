package sanea.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.commons.text.similarity.LevenshteinDistance;

public class RegiaoFinder {
	public String getRegioesID(String RegiaoDigitada) {
		String sql = "SELECT nome_regiao FROM regioes_santos";
		String RegiaoEncontrada = null;
		
		try {
			Connection conn = MySqlConnection.conectar();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			 
		        int menorDistancia = Integer.MAX_VALUE;
		        LevenshteinDistance ld = new LevenshteinDistance();

		        while (rs.next()) {
		            String nomeRegiao = rs.getString("nome_regiao");
		            int distancia = ld.apply(RegiaoDigitada, nomeRegiao);

		            if (distancia < menorDistancia) {
		                menorDistancia = distancia;
		                RegiaoEncontrada = nomeRegiao;
		            }
		        }



			
		} catch(Exception e) {
			System.err.println("Erro ao buscar nome no Banco: " + e.getMessage());
		}
		
		return RegiaoEncontrada;
	}
}
