package sanea.model;

import sanea.dao.*;

public class Usuario {
	
	private int idUsuario;
	private String nome;
	private String email;
	private String telefone;
	private String senha;
	private String cpf;
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public boolean cadastrar() {
		try {
			new CadastroDao().cadastrarUsuario(this);
			return true;
		} catch (Exception e) {
			System.err.println("Erro ao cadastrar usuário: " + e.getMessage());
			return false;
		}
	}
	
	public String logar() {
		String UserID = (new LogarDao()).logarUsuario(this);
		return UserID;
	}
	
	public Usuario Username() {
		Usuario usuario = (new GetUserNameDao()).GetUserName(this);
		return usuario;
	}
	
	public void alterar() {
		new AlterarDao().alterarUsuario(this);
	}
	
	public void alterarSenha(String SenhaAtual) {
		new AlterarSenhaDao().alterarSenhaUsuario(this, SenhaAtual);
	}
}
