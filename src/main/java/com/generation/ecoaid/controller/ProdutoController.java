package com.generation.ecoaid.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import com.generation.ecoaid.model.Produto;
import com.generation.ecoaid.repository.ProdutoRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/produto")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutoController {

	@Autowired
    private ProdutoRepository produtoRepository;
	
	@GetMapping
	public ResponseEntity<List<Produto>> getAll(){
        return ResponseEntity.ok(produtoRepository.findAll());
    }
	
	@GetMapping("/{id}")
	public ResponseEntity<Produto> getById(@PathVariable Long id) {
		return produtoRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Produto>> getByNome(@PathVariable String nome) {
		return ResponseEntity.ok(produtoRepository
	            .findAllByNomeContainingIgnoreCase(nome));
	}
	
	@GetMapping("/condicao/{condicao}")
	public ResponseEntity<List<Produto>> getByCondicao(@PathVariable String condicao) {
		return ResponseEntity.ok(produtoRepository
	            .findAllByCondicaoContainingIgnoreCase(condicao));
	}
	
	@GetMapping("/preco_inicial/{inicio}/preco_final/{fim}")
    public ResponseEntity<List<Produto>> getByPrecoEntreNatve(@PathVariable float inicio, @PathVariable float fim){
        return ResponseEntity.ok(produtoRepository.buscarProdutosEntre(inicio, fim));
    }
	
	@PostMapping
	public ResponseEntity<Produto> post(@Valid @RequestBody Produto Produto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(produtoRepository.save(Produto));
    }
	
	@PutMapping
	public ResponseEntity<Produto> put(@Valid @RequestBody Produto Produto){
        return produtoRepository.findById(Produto.getId())
            .map(resposta -> ResponseEntity.status(HttpStatus.CREATED)
            .body(produtoRepository.save(Produto)))
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        Optional<Produto> Produto = produtoRepository.findById(id);
        
        if(Produto.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        
        produtoRepository.deleteById(id);              
    }
}
