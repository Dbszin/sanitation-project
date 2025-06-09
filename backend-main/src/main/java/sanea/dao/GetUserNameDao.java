package sanea.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import sanea.model.Usuario;

public class GetUserNameDao {
		
		public Usuario GetUserName(Usuario usuario) {
			String sql = "SELECT nome, email, telefone, cpf FROM usuarios WHERE id = ?";
			
			try {
				Connection conn = MySqlConnection.conectar();
				PreparedStatement ps = conn.prepareStatement(sql);
				ps = conn.prepareStatement(sql);
				
				ps.setInt(1, usuario.getIdUsuario());
				ResultSet rs = ps.executeQuery();
				
				if (rs.next()) {
				    usuario.setNome(rs.getString("nome"));
				    usuario.setEmail(rs.getString("email"));
				    usuario.setTelefone(rs.getString("telefone"));
				    usuario.setCpf(rs.getString("cpf"));
				    System.out.println("Dados do Usuario LOGADO: " + usuario.getNome());
				} else {
				    // ID não reconhecido
					System.out.println("ID não encontrado...");
					System.out.println("ID do Usuario: " + usuario.getIdUsuario());
					return null;
				}
				
			} catch(Exception e) {
				System.err.println("Erro ao buscar dados no Banco: " + e.getMessage());
				return null;
			}
			
			return usuario;
		}
		
	}
