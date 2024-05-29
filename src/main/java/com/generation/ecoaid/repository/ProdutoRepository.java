package com.generation.ecoaid.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.generation.ecoaid.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	
	public List<Produto> findAllByNomeContainingIgnoreCase(@Param("nome") String nome);
	
	public List<Produto> findAllByCondicaoContainingIgnoreCase(@Param("condicao") String condicao);
	
	@Query(value = "select * from tb_produto where valor between :inicio and :fim", nativeQuery = true)
    public List <Produto> buscarProdutosEntre(@Param("inicio") float inicio, @Param("fim") float fim);
	
}
