package sanea.model;

import sanea.dao.*;
import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int idUsuario;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(nullable = false, unique = true)
	private String email;
	
	@Column(nullable = false, unique = true)
	private String telefone;
	
	@Column(nullable = true, unique = true)
	private String cpf;
	
	@Column(name = "senha_hash")
	private String senha;
	
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

	public String Username() {
		return this.email;
	}

	public int logar() {
		// Implementação do método de login
		// Por enquanto, retorna o ID do usuário
		return this.idUsuario;
	}

	public boolean alterar() {
		// Implementação do método de alteração
		return true;
	}

	public boolean alterarSenha(String novaSenha) {
		this.senha = novaSenha;
		return true;
	}
}
