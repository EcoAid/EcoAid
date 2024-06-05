package com.generation.ecoaid.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tb_usuario")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "O atributo de nome é Obrigatório!")
	@Size(min = 3, max = 100, message = "O atributo nome deve conter no minimo 3 e no máximo 100 caracteres")
	private String nome;
	
	@NotBlank(message = "O atributo de senha é Obrigatório!")
	@Size(min = 8, max = 500, message = "O atributo senha deve conter no minimo 8 e no máximo 500 caracteres")
	private String senha;
	
	@NotBlank(message = "O atributo de foto é Obrigatório!")
	@Size(min = 3, max = 5000, message = "O atributo foto deve conter no minimo 3 e no máximo 5000 caracteres")
	private String foto;
	
	@NotBlank(message = "O atributo de email é Obrigatório!")
	@Size(min = 8, max = 100, message = "O atributo email deve conter no minimo 8 e no máximo 100 caracteres")
	private String usuario;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties("usuario")
	private List<Produto> produto;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public List<Produto> getProduto() {
		return produto;
	}

	public void setProduto(List<Produto> produto) {
		this.produto = produto;
	}
	
}
