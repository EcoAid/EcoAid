package com.generation.ecoaid.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.ecoaid.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	/*Igual as outras repositories, ela conversa com o banco de dados e
	 * procura pelo usuario pelo E-mail.
	*/
    public Optional<Usuario> findByUsuario(String usuario);

}