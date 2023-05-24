package br.com.vistoriaOnline.model.dto;

import java.util.ArrayList;
import java.util.List;

public class RestResponse {

	private String codigo;
	private List<String> mensagem;
	private Object conteudo;
	
	public RestResponse() {}
	
	public RestResponse(String codigo, List<String> mensagem, Object conteudo) {
		this.codigo = codigo;
		this.mensagem = mensagem;
		this.conteudo = conteudo;
	}
	
	public RestResponse(String codigo, String mensagem, Object conteudo) {
		this.codigo = codigo;
		this.mensagem = new ArrayList<String>();
		this.mensagem.add(mensagem);
		this.conteudo = conteudo;
	}
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public List<String> getMensagem() {
		return mensagem;
	}
	public void setMensagem(List<String> mensagem) {
		this.mensagem = mensagem;
	}
	public Object getConteudo() {
		return conteudo;
	}
	public void setConteudo(Object conteudo) {
		this.conteudo = conteudo;
	}		
}
