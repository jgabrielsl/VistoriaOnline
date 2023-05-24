package br.com.vistoriaOnline.model.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.vistoriaOnline.model.Usuario;

public class UsuarioDto {
	
	private Long id;
	private String email;
	private String nome;
	private String senha;
	private boolean ativo;
	
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public UsuarioDto() {}
	
	public UsuarioDto(Usuario usuario) {
		this.id = usuario.getId();
		this.email = usuario.getEmail();
		this.nome = usuario.getNome();
		this.ativo = usuario.isAtivo();
	}

	public UsuarioDto(Usuario usuario, String senhaGerada) {
		this.id = usuario.getId();
		this.email = usuario.getEmail();
		this.nome = usuario.getNome();
		this.ativo = usuario.isAtivo();
		this.senha = senhaGerada;
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

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public static List<UsuarioDto> converter(List<Usuario> usuarios) {
		return usuarios.stream().map(UsuarioDto::new).collect(Collectors.toList());
	}

	public Usuario buildUsuario() {
		return new Usuario(this.id, this.nome, this.email, this.ativo);
	}

}

