package sanea.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import sanea.model.TiposProblema;

public class getProblemaIdDAO {
	public int getProblemaID(TiposProblema tipos) {
		String sql = "SELECT id FROM tipos_problema WHERE nome = ?";
		int ProblemaID = 0;
		
		try {
			Connection conn = MySqlConnection.conectar();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, tipos.getNome());
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
			    ProblemaID = rs.getInt("id");
			    System.out.println("[LOG] ID do Tipo de Problema BUSCADO: " + ProblemaID);
			    
			    
			} else {
			    // Tipo de Problema não reconhecido...
				System.out.println("[LOG] Problema não encontrado...");
				System.out.println("[LOG] Nome do Tipo de Problema: " + tipos.getNome());
			}

			
		} catch(Exception e) {
			System.err.println("Erro ao buscar nome no Banco: " + e.getMessage());
		}
		
		return ProblemaID;
	}
}
