package br.com.vistoriaOnline.controllers.dto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import br.com.vistoriaOnline.model.Usuario;

public class UsuarioDto {
	
	private Long id;
	private String email;
	private String nome;


	public UsuarioDto(Usuario usuario) {
		this.id = usuario.getId();
		this.email = usuario.getEmail();
		this.nome = usuario.getNome();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}


	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public static List<UsuarioDto> converter(List<Usuario> usuarios) {
		return usuarios.stream().map(UsuarioDto::new).collect(Collectors.toList());
	}

}

