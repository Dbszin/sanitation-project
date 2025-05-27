package sanea.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.math.*;

import sanea.model.Reclamacoes;

public class CadastroRelatorioDAO {
	public void cadastrarRelatorio(Reclamacoes reclamacoes) {
		String sql = "INSERT INTO reclamacoes (usuario_id, tipo_problema_id, regiao_id, endereco, descricao, latitude, longitude, status, anexo_patch) VALUES (?,?,?,?,?,?,?,?,?)";
		
		try {
			Connection conn = MySqlConnection.conectar();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps = conn.prepareStatement(sql);
			ps.setInt(1, reclamacoes.getIdUsuario());
			ps.setInt(2, reclamacoes.getIdTipoProblema());
			ps.setInt(3, reclamacoes.getIdRegiao());
			ps.setString(4, reclamacoes.getEndereco());
			ps.setString(5, reclamacoes.getDescricao());
			ps.setBigDecimal(6, reclamacoes.getLatitude());
			ps.setBigDecimal(7, reclamacoes.getLongitude());
			ps.setString(8, reclamacoes.getStatus());
			ps.setString(9, reclamacoes.getAnexo());
			ps.execute();
		} catch(Exception e) {
			System.err.println("Erro ao adicionar usuário: " + e.getMessage());
		}
	}
}
