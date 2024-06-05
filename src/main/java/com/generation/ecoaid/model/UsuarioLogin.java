package com.generation.ecoaid.model;

public class UsuarioLogin {
	
	/*
	 *UsuarioLogin é a classe de login so que para o usuário que efetuou um login 
	 *e quer receber um token. Também serve para revelar os dados ao usuário sem expor 
	 *dados da model original.
*/
	
	private Long id;
    private String nome;
    private String usuario;
    private String senha;
    private String foto;
    private String token;
    
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
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
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
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
    
    
}
