package sanea.model;

import sanea.dao.getProblemaIdDAO;

public class TiposProblema {
	private int id;
	private String nome;
	private String descricao;
	private String icon;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public int getProblemaID() {
		int RegiaoID = (new getProblemaIdDAO()).getProblemaID(this);
		return RegiaoID;
	}
	
}
