package sanea.dao;

import java.sql.*;
import sanea.model.Relato;

public class CadastrarRelatoDao {
    public void cadastrarRelato(Relato relato) {
        String sql = "INSERT INTO relatos (id_usuario, tipo_problema, descricao, data_ocorrido, cep, rua, numero, bairro, cidade, estado) VALUES (?,?,?,?,?,?,?,?,?,?)";
        
        try {
            Connection conn = MySqlConnection.conectar();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, relato.getIdUsuario());
            ps.setString(2, relato.getTipoProblema());
            ps.setString(3, relato.getDescricao());
            ps.setString(4, relato.getDataOcorrido());
            ps.setString(5, relato.getCep());
            ps.setString(6, relato.getRua());
            ps.setString(7, relato.getNumero());
            ps.setString(8, relato.getBairro());
            ps.setString(9, relato.getCidade());
            ps.setString(10, relato.getEstado());
            ps.execute();
        } catch(Exception e) {
            System.err.println("Erro ao cadastrar relato: " + e.getMessage());
            throw new RuntimeException("Erro ao cadastrar relato", e);
        }
    }
} 