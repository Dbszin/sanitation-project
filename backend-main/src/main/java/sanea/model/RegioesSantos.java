package sanea.model;

import java.math.BigDecimal;

import sanea.dao.getLatiLongDAO;
import sanea.dao.getRegioesIdDAO;

public class RegioesSantos {
	
	private int id;
	private String nome;
	private BigDecimal latitude;
	private BigDecimal longitude;
	private int populacao;
	private float area;
	private String zona;
	private float taxa;
	private float densidade_populacional;
	
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
	public int getPopulacao() {
		return populacao;
	}
	public void setPopulacao(int populacao) {
		this.populacao = populacao;
	}
	public float getArea() {
		return area;
	}
	public void setArea(float area) {
		this.area = area;
	}
	public String getZona() {
		return zona;
	}
	public void setZona(String zona) {
		this.zona = zona;
	}
	public float getTaxa() {
		return taxa;
	}
	public void setTaxa(float taxa) {
		this.taxa = taxa;
	}
	public float getDensidade_populacional() {
		return densidade_populacional;
	}
	public void setDensidade_populacional(float densidade_populacional) {
		this.densidade_populacional = densidade_populacional;
	}
	
	public int getRegiaoID() {
		int RegiaoID = (new getRegioesIdDAO()).getRegioesID(this);
		return RegiaoID;
	}
	
	public BigDecimal[] getLatitudeLongitude() {
		BigDecimal[] LatitudeLongitude = (new getLatiLongDAO()).getLatitudeLongitude(this);
		return LatitudeLongitude;
	}

}
