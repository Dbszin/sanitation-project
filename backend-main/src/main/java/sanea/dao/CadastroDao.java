package sanea.dao;

import java.sql.*;

import sanea.model.Usuario;

public class CadastroDao {
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
			ps.setString(5, usuario.getSenha());
			ps.execute();
		} catch(Exception e) {
			System.err.println("Erro ao adicionar usuário: " + e.getMessage());
		}
	}
	
}
