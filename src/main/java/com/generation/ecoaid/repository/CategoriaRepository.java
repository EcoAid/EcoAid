package com.generation.ecoaid.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import com.generation.ecoaid.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

	public List<Categoria> findAllByTipoContainingIgnoreCase(@Param("tipo") String tipo);
	public List<Categoria> findAllByDescricaoContainingIgnoreCase(@Param("descricao") String descricao);
	
}
