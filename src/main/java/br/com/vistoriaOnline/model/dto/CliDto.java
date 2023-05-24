package br.com.vistoriaOnline.model.dto;

import br.com.vistoriaOnline.model.Cliente;

public class CliDto {

	private Long id;
	private String nome;
	private String telefone;
	private String link;
	
	
	public CliDto(){}
	
	public CliDto(Long id, String nome, String telefone, String link) {
		this.id = id;
		this.nome = nome;
		this.telefone = telefone;
		this.link = link;
	}
	
	public CliDto(Cliente cliente) {
		this.id = cliente.getId();
		this.nome = cliente.getNome();
		this.telefone = cliente.getTelefone();
		this.link = cliente.getLink();
		
	}
	
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
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	
}
