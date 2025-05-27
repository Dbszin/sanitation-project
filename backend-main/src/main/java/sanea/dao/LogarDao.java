package sanea.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import sanea.model.Usuario;

public class LogarDao {
	
	public String logarUsuario(Usuario usuario) {
		String sql = "SELECT id, senha_hash FROM usuarios WHERE email = ?";
		
		String UserID = null;
		
		try {
			Connection conn = MySqlConnection.conectar();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, usuario.getEmail());
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
			    String senhaHash = rs.getString("senha_hash");
			    System.out.println("Senha CADASTRADA: " + senhaHash);
			    System.out.println("Senha DIGITADA: " + usuario.getSenha());
			    
			    if (usuario.getSenha().equals(senhaHash)) {
			        // Login bem-sucedido
			    	System.out.println("LOGADO COM SUCESSO");
			    	UserID = Integer.toString(rs.getInt("id"));
			    	
			    }
			} else {
			    // Email ou senha incorretos
				System.out.println("FALHA AO LOGAR");
			}

			
		} catch(Exception e) {
			System.err.println("Erro ao buscar o login do usuário: " + e.getMessage());
		}
		
		return UserID;
	}
	
}
