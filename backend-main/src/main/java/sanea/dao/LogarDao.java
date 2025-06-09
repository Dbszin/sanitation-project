package sanea.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;

import sanea.model.Usuario;

public class LogarDao {
	
	private String hashSenha(String senha) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(senha.getBytes(StandardCharsets.UTF_8));
			StringBuilder hexString = new StringBuilder();
			for (byte b : hash) {
				String hex = Integer.toHexString(0xff & b);
				if (hex.length() == 1) hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
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
				String senhaDigitadaHash = hashSenha(usuario.getSenha());
				
				if (senhaHash.equals(senhaDigitadaHash)) {
					System.out.println("LOGADO COM SUCESSO");
					UserID = Integer.toString(rs.getInt("id"));
				} else {
					System.out.println("SENHA INCORRETA");
				}
			} else {
				System.out.println("EMAIL NÃO ENCONTRADO");
			}
			
		} catch(Exception e) {
			System.err.println("Erro ao buscar o login do usuário: " + e.getMessage());
		}
		
		return UserID;
	}
	
}
