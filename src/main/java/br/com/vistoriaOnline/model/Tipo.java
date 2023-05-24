package br.com.vistoriaOnline.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Tipo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String tipo;
	@Column(columnDefinition="MEDIUMTEXT")
	private String imagemPadrao;
	private String descricao;
	@Column(name="ordem") 
	private Integer ordem;
	
	@OneToMany(mappedBy="tipo", fetch = FetchType.LAZY)
    private Set<Imagem> imagens;
	
	public Tipo() {}
	
	public Tipo(Long idTipo) {
		this.id = idTipo;
	}
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
	public String getImagemPadrao() {
		return imagemPadrao;
	}
	public void setImagemPadrao(String imagemPadrao) {
		this.imagemPadrao = imagemPadrao;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Set<Imagem> getImagens() {
		return imagens;
	}
	public void setImagens(Set<Imagem> imagens) {
		this.imagens = imagens;
	}

	public Integer getOrdem() {
		return ordem;
	}

	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}
	
	
}
