package sanea.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import sanea.model.RegioesSantos;

public class getRegioesIdDAO {
	public int getRegioesID(RegioesSantos regioes) {
		String sql = "SELECT id FROM regioes_santos WHERE nome_regiao = ?";
		int RegiaoID = 0;
		
		try {
			Connection conn = MySqlConnection.conectar();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, regioes.getNome());
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
			    RegiaoID = rs.getInt("id");
			    System.out.println("[LOG] ID da Região BUSCADA: " + RegiaoID);
			    
			    
			} else {
			    // Região não reconhecida...
				System.out.println("[LOG] Regiao não encontrada...");
				System.out.println("[LOG] Nome da Região: " + regioes.getNome());
			}

			
		} catch(Exception e) {
			System.err.println("Erro ao buscar nome no Banco: " + e.getMessage());
		}
		
		return RegiaoID;
	}
}
