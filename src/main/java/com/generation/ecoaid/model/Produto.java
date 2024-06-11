package com.generation.ecoaid.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tb_produto")
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "O atributo de nome é Obrigatório!")
	@Size(min = 3, max = 100, message = "O atributo nome deve conter no minimo 3 e no máximo 100 caracteres")
	private String nome;
	
	@NotBlank(message = "O atributo de foto é Obrigatório!")
	@Size(min = 3, max = 5000, message = "O atributo foto deve conter no minimo 3 e no máximo 5000 caracteres")
	private String foto;
	
	@NotBlank(message = "O atributo de descrição é Obrigatório!")
	@Size(min = 3, max = 1000, message = "O atributo descrição deve conter no minimo 3 e no máximo 1000 caracteres")
	private String descricao;
	
	@NotBlank(message = "O atributo de condição é Obrigatório!")
	@Size(min = 3, max = 100, message = "O atributo condição deve conter no minimo 3 e no máximo 100 caracteres")
	private String condicao;
	
	@NotNull(message = "O atributo valor é Obrigatório!")
	@PositiveOrZero(message = "O atributo valor deve ser no minimo igual ou maior que zero!")
	private float valor;
	
	@UpdateTimestamp
	private LocalDateTime dataCadastro;
	
	@ManyToOne
    @JsonIgnoreProperties("produto")
	private Categoria categoria;
	
	@ManyToOne
    @JsonIgnoreProperties("produto")
	private Usuario usuario;

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

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getCondicao() {
		return condicao;
	}

	public void setCondicao(String condicao) {
		this.condicao = condicao;
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}
	
	public LocalDateTime getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDateTime dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}
