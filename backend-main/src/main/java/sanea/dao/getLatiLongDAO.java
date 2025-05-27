package sanea.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.math.*;

import sanea.model.RegioesSantos;

public class getLatiLongDAO {
	public BigDecimal[] getLatitudeLongitude(RegioesSantos latilong) {
		String sql = "SELECT latitude, longitude FROM regioes_santos WHERE id = ?";
		BigDecimal[] LatitudeLongitude = {new BigDecimal (0.0), new BigDecimal (0.0)};
		
		try {
			Connection conn = MySqlConnection.conectar();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, latilong.getId());
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
			    LatitudeLongitude[0] = rs.getBigDecimal("latitude");
			    LatitudeLongitude[1] = rs.getBigDecimal("longitude");
			    System.out.println("[LOG] Latitude do ID da Região BUSCADA: " + LatitudeLongitude[0]);
			    System.out.println("[LOG] Longitude do ID da Região BUSCADA: " + LatitudeLongitude[1]);
			    
			    
			} else {
			    // ID da Região não reconhecida...
				System.out.println("[LOG] ID da Região não encontrado...");
				System.out.println("[LOG] ID da Região: " + latilong.getId());
			}

			
		} catch(Exception e) {
			System.err.println("Erro ao buscar nome no Banco: " + e.getMessage());
		}
		
		return LatitudeLongitude;
	}
}
