package com.generation.ecoaid.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.generation.ecoaid.model.Usuario;
import com.generation.ecoaid.repository.UsuarioRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

		// Procura pelo usuário na repository que conversa com o banco de dados pelo
		// E-mail
		Optional<Usuario> usuario = usuarioRepository.findByUsuario(userName);

		/*
		 * Se essa conta existir, ele retorna um objeto da user details com senha,
		 * email, autoridades e validações ativadas.
		 */
		if (usuario.isPresent())
			return new UserDetailsImpl(usuario.get());
		else
			/*
			 * Se não da um erro na cara do usuário falando que não existe, status
			 * FORBIDDEN.
			 */
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);

	}
}
