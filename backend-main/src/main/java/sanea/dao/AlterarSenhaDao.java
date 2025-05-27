package sanea.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import sanea.model.Usuario;

public class AlterarSenhaDao {
	public void alterarSenhaUsuario(Usuario usuario, String SenhaAtual) {
String sql = "SELECT senha_hash FROM usuarios WHERE id = ?";
		
		
		try {
			Connection conn = MySqlConnection.conectar();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, usuario.getIdUsuario());
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
			    String senhaHash = rs.getString("senha_hash");
			    
			    if (SenhaAtual.equals(senhaHash)) {
			        
			    	sql = "UPDATE usuarios SET senha_hash = ? WHERE ?";
					try {
						conn = MySqlConnection.conectar();
						ps = conn.prepareStatement(sql);
						ps.setString(1, usuario.getSenha());
						ps.setInt(2, usuario.getIdUsuario());
						ps.executeUpdate();
					} catch(Exception e) {
						System.err.println("Erro ao alterar senha do usuário: " + e.getMessage());
					}
			    }
			} else {
				System.out.println("SENHA ATUAL ESTÁ INCORRETA");
			}

			
		} catch(Exception e) {
			System.err.println("Erro ao buscar o login do usuário: " + e.getMessage());
		}
	}
}
