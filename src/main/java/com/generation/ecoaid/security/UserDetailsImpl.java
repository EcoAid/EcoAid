package com.generation.ecoaid.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.generation.ecoaid.model.Usuario;

public class UserDetailsImpl implements UserDetails {

	// Informa para o Spring Security que versão iremos utilizar, no caso a versão
	// 1.
	private static final long serialVersionUID = 1L;

	// Váriavel do e-mail do usuário
	private String userName;
	// Váriavel da senha do usuário
	private String password;
	// Váriavel das autoridades do usuário, se ele é admin ou um usuário comum.
	private List<GrantedAuthority> authorities;

	// Construtores.
	public UserDetailsImpl(Usuario user) {
		this.userName = user.getUsuario();
		this.password = user.getSenha();
	}

	public UserDetailsImpl() {
	}

	// Getters and setters para o Spring conseguir acessar esses dados.
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
    // Retorna a senha do usuário
	@Override
	public String getPassword() {
		return password;
	}
    // Retorna o nome de usuário (email)
	@Override
	public String getUsername() {
		return userName;
	}

	// Ativando as validações se a conta não estiver expirada, trancada, token expirada ou conta desativada.
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
    // Indica se a conta do usuário não está bloqueada
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
    // Indica se as credenciais do usuário (senha) não estão expiradas
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
    // Indica se a conta do usuário está habilitada
	@Override
	public boolean isEnabled() {
		return true;
	}

}
