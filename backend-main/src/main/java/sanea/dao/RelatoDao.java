package sanea.dao;

import sanea.model.Relato;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RelatoDao {
    private Connection connection;

    public RelatoDao() throws SQLException {
        this.connection = MySqlConnection.conectar();
    }

    public boolean cadastrarRelato(Relato relato) {
        String sql = "INSERT INTO relatos (id_usuario, tipo_problema, descricao, data_ocorrido, cep, rua, numero, bairro, cidade, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, relato.getIdUsuario());
            stmt.setString(2, relato.getTipoProblema());
            stmt.setString(3, relato.getDescricao());
            stmt.setString(4, relato.getDataOcorrido());
            stmt.setString(5, relato.getCep());
            stmt.setString(6, relato.getRua());
            stmt.setString(7, relato.getNumero());
            stmt.setString(8, relato.getBairro());
            stmt.setString(9, relato.getCidade());
            stmt.setString(10, relato.getEstado());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar relato: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
} 