package sanea.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import sanea.model.Usuario;

public class GetUserNameDao {
		
		public String GetUserName(Usuario usuario) {
			String sql = "SELECT nome FROM usuarios WHERE id = ?";
			String UserName = null;
			
			try {
				Connection conn = MySqlConnection.conectar();
				PreparedStatement ps = conn.prepareStatement(sql);
				ps = conn.prepareStatement(sql);
				
				ps.setInt(1, usuario.getIdUsuario());
				ResultSet rs = ps.executeQuery();
				
				if (rs.next()) {
				    UserName = rs.getString("nome");
				    System.out.println("NOME do Usuario LOGADO: " + UserName);
				    
				    
				} else {
				    // ID não reconhecido
					System.out.println("ID não encontrado...");
					System.out.println("ID do Usuario: " + usuario.getIdUsuario());
				}

				
			} catch(Exception e) {
				System.err.println("Erro ao buscar nome no Banco: " + e.getMessage());
			}
			
			return UserName;
		}
		
	}
