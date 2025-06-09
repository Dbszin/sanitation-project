package sanea.model;

import sanea.dao.RelatoDao;
import java.sql.SQLException;

public class Relato {
    private int idUsuario;
    private String tipoProblema;
    private String descricao;
    private String dataOcorrido;
    private String cep;
    private String rua;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
    
    public int getIdUsuario() {
        return idUsuario;
    }
    
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    public String getTipoProblema() {
        return tipoProblema;
    }
    
    public void setTipoProblema(String tipoProblema) {
        this.tipoProblema = tipoProblema;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public String getDataOcorrido() {
        return dataOcorrido;
    }
    
    public void setDataOcorrido(String dataOcorrido) {
        this.dataOcorrido = dataOcorrido;
    }
    
    public String getCep() {
        return cep;
    }
    
    public void setCep(String cep) {
        this.cep = cep;
    }
    
    public String getRua() {
        return rua;
    }
    
    public void setRua(String rua) {
        this.rua = rua;
    }
    
    public String getNumero() {
        return numero;
    }
    
    public void setNumero(String numero) {
        this.numero = numero;
    }
    
    public String getBairro() {
        return bairro;
    }
    
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }
    
    public String getCidade() {
        return cidade;
    }
    
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public boolean cadastrar() {
        try {
            RelatoDao relatoDao = new RelatoDao();
            return relatoDao.cadastrarRelato(this);
        } catch (SQLException e) {
            System.err.println("Erro ao criar RelatoDao: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
} 