package sanea.model;

import java.math.BigDecimal;

import sanea.dao.CadastroDao;
import sanea.dao.CadastroRelatorioDAO;

public class Reclamacoes {
 
	private int IdUsuario;
	private int IdTipoProblema;
	private int IdRegiao;
	private String endereco;
	private String descricao;
	private BigDecimal latitude;
	private BigDecimal longitude;
	private String status;
	private String anexo;
	
	public String getAnexo() {
		return anexo;
	}
	public void setAnexo(String anexo) {
		this.anexo = anexo;
	}
	public int getIdUsuario() {
		return IdUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		IdUsuario = idUsuario;
	}
	public int getIdTipoProblema() {
		return IdTipoProblema;
	}
	public void setIdTipoProblema(int idTipoProblema) {
		IdTipoProblema = idTipoProblema;
	}
	public int getIdRegiao() {
		return IdRegiao;
	}
	public void setIdRegiao(int idRegiao) {
		IdRegiao = idRegiao;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public BigDecimal getLatitude() {
		return latitude;
	}
	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}
	public BigDecimal getLongitude() {
		return longitude;
	}
	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public void cadastrar() {
		new CadastroRelatorioDAO().cadastrarRelatorio(this);
	}
	
	
}
