package sanea.model;

public class Acompanhamento {
	private int id;
	private int reclamacao_id;
	private String responsavel;
	private String acao;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getReclamacao_id() {
		return reclamacao_id;
	}
	public void setReclamacao_id(int reclamacao_id) {
		this.reclamacao_id = reclamacao_id;
	}
	public String getResponsavel() {
		return responsavel;
	}
	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}
	public String getAcao() {
		return acao;
	}
	public void setAcao(String acao) {
		this.acao = acao;
	} 
	
	
}
