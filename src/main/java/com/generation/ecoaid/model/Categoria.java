package com.generation.ecoaid.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity 
@Table(name = "tb_categorias") 
public class Categoria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "O atributo tipo é Obrigatório!")
	@Size(min = 5, max = 100, message = "O atributo tipo deve conter no mínimo 05 e no máximo 100 caracteres")
	private String tipo;
	
	@NotBlank(message = "O atributo descrição é Obrigatório!")
	@Size(min = 5, max = 1000, message = "O atributo descrição deve conter no mínimo 05 e no máximo 100 caracteres")
	private String descricao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}
