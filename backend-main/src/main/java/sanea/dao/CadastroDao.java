package sanea.dao;

import java.sql.*;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;

import sanea.model.Usuario;

public class CadastroDao {
	
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
	
	public void cadastrarUsuario(Usuario usuario) {
		String sql = "INSERT INTO usuarios (nome, email, telefone, cpf, senha_hash) VALUES (?,?,?,?,?)";
		
		try {
			Connection conn = MySqlConnection.conectar();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps = conn.prepareStatement(sql);
			ps.setString(1, usuario.getNome());
			ps.setString(2, usuario.getEmail());
			ps.setString(3, usuario.getTelefone());
			ps.setString(4, usuario.getCpf());
			ps.setString(5, hashSenha(usuario.getSenha()));
			ps.execute();
		} catch(Exception e) {
			System.err.println("Erro ao adicionar usuário: " + e.getMessage());
			throw new RuntimeException("Erro ao cadastrar usuário", e);
		}
	}
	
}
