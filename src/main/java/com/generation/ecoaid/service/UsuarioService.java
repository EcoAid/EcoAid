package com.generation.ecoaid.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.generation.ecoaid.model.Usuario;
import com.generation.ecoaid.model.UsuarioLogin;
import com.generation.ecoaid.repository.UsuarioRepository;
import com.generation.ecoaid.security.JwtService;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
    // Injeção do serviço JWT para geração de tokens
	@Autowired
	private JwtService jwtService;

    // Injeção do gerenciador de autenticação
	@Autowired
	private AuthenticationManager authenticationManager;

	// Metodo para verificar se já não existe um usuário com os mesmos dados no banco de dados.
	public Optional<Usuario> cadastrarUsuario(Usuario usuario) {

        // Verifica se o usuário já existe no banco de dados pelo email
		if (usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent())
			return Optional.empty();

        // Criptografa a senha do usuário antes de salvar
		usuario.setSenha(criptografarSenha(usuario.getSenha()));

        // Salva o usuário no banco de dados e retorna o usuário salvo
		return Optional.of(usuarioRepository.save(usuario));

	}

	// Metodo para verificar se já não existe um usuário com os mesmos dados no banco de dados pelo ID.
	public Optional<Usuario> atualizarUsuario(Usuario usuario) {

        // Verifica se o usuário existe no banco de dados pelo ID
		if (usuarioRepository.findById(usuario.getId()).isPresent()) {

			Optional<Usuario> buscaUsuario = usuarioRepository.findByUsuario(usuario.getUsuario());

            // Se o usuário com o email já existe e não é o mesmo usuário que está sendo atualizado, lança uma exceção
			if ((buscaUsuario.isPresent()) && (buscaUsuario.get().getId() != usuario.getId()))
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já existe!", null);

            // Criptografa a nova senha antes de salvar
			usuario.setSenha(criptografarSenha(usuario.getSenha()));

            // Salva o usuário atualizado no banco de dados e retorna o usuário salvo
			return Optional.ofNullable(usuarioRepository.save(usuario));

		}
        // Retorna vazio se o usuário não foi encontrado pelo ID
		return Optional.empty();

	}
    // Método para autenticar um usuário
	public Optional<UsuarioLogin> autenticarUsuario(Optional<UsuarioLogin> usuarioLogin) {

		// Gera o Objeto de autenticação
		var credenciais = new UsernamePasswordAuthenticationToken(usuarioLogin.get().getUsuario(),
				usuarioLogin.get().getSenha());

		// Autentica o Usuario
		Authentication authentication = authenticationManager.authenticate(credenciais);

		// Se a autenticação foi efetuada com sucesso
		if (authentication.isAuthenticated()) {

			// Busca os dados do usuário
			Optional<Usuario> usuario = usuarioRepository.findByUsuario(usuarioLogin.get().getUsuario());

			// Se o usuário foi encontrado
			if (usuario.isPresent()) {

				// Preenche o Objeto usuarioLogin com os dados encontrados
				usuarioLogin.get().setId(usuario.get().getId());
				usuarioLogin.get().setNome(usuario.get().getNome());
				usuarioLogin.get().setFoto(usuario.get().getFoto());
				usuarioLogin.get().setToken(gerarToken(usuarioLogin.get().getUsuario()));
				usuarioLogin.get().setSenha("");

				// Retorna o Objeto preenchido
				return usuarioLogin;

			}

		}
        // Retorna vazio se a autenticação falhou ou o usuário não foi encontrado
		return Optional.empty();

	}
    // Método para criptografar a senha do usuário
	private String criptografarSenha(String senha) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		return encoder.encode(senha);

	}
    // Método para gerar o token JWT para o usuário autenticado
	private String gerarToken(String usuario) {
		return "Bearer " + jwtService.generateToken(usuario);
	}

}
