package sanea.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import sanea.model.Usuario;

public class AlterarDao {
	public void alterarUsuario(Usuario usuario) {
		
		System.out.println(usuario.getTelefone());
		System.out.println(usuario.getEmail());
		if(usuario.getTelefone() == null || usuario.getTelefone().isEmpty()) {
			System.out.println("Ta no if..");
			String sql = "UPDATE usuarios SET email = ? WHERE ?";
			try {
				Connection conn = MySqlConnection.conectar();
				PreparedStatement ps = conn.prepareStatement(sql);
				ps = conn.prepareStatement(sql);
				ps.setString(1, usuario.getEmail());
				ps.setInt(2, usuario.getIdUsuario());
				ps.executeUpdate();
			} catch(Exception e) {
				System.err.println("Erro ao alterar usuário: " + e.getMessage());
			}
		} else {
			System.out.println("Ta no else..");
			String sql = "UPDATE usuarios SET telefone = ? WHERE ?";
			try {
				Connection conn = MySqlConnection.conectar();
				PreparedStatement ps = conn.prepareStatement(sql);
				ps = conn.prepareStatement(sql);
				ps.setString(1, usuario.getTelefone());
				ps.setInt(2, usuario.getIdUsuario());
				ps.executeUpdate();
			} catch(Exception e) {
				System.err.println("Erro ao alterar usuário: " + e.getMessage());
			}
		}
	}
}
