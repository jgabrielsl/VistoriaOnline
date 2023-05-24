package br.com.vistoriaOnline.model.dto;

import br.com.vistoriaOnline.model.Imagem;

public class ImagemTipoDto {

	private String tipo;
	private String imagem_padrao;
	private Long id;
	private String descricao;
	private Long idTipo;
	private Long statusId;
	private Long idImg;
	private String status;
	private String imagemEnviada;
	private String extensao;
	
	public ImagemTipoDto() {}
	
	public ImagemTipoDto(String tipo, String imagem_padrao, String descricao, Imagem img, Long idTipo) {
		this.tipo = tipo;
		this.imagem_padrao = imagem_padrao;
		this.id = img==null?null:img.getId();
		this.descricao = descricao;
		this.idTipo = idTipo;
	}
	
	public ImagemTipoDto(String tipo, String imagem_padrao, String descricao, Imagem img, Long idTipo, Long idStatus) {
		this.tipo = tipo;
		this.imagem_padrao = imagem_padrao;
		this.id = img==null?null:img.getId();
		this.descricao = descricao;
		this.idTipo = idTipo;
		this.statusId = idStatus;
	}
	
	public ImagemTipoDto(String tipo, String imagem_padrao, String descricao, Long id, Long idTipo, String status, String imagemEnviada, String extensao) {
		this.tipo = tipo;
		this.imagem_padrao = imagem_padrao;
		this.id = id;
		this.descricao = descricao;
		this.idTipo = idTipo;
		this.status = status;
		this.imagemEnviada = imagemEnviada;
		this.setExtensao(extensao);
	}
	
	public ImagemTipoDto(String descricao, Long id, Long idTipo, Long statusId,String status, String imagem, String extensao, Long idImg) {
		this.descricao = descricao;
		this.id = id;
		this.idTipo = idTipo;
		this.statusId = statusId;
		this.status = status;
		this.imagemEnviada = imagem;
		this.extensao = extensao;
		this.setIdImg(idImg);
	}

	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getImagem_padrao() {
		return imagem_padrao;
	}
	public void setImagem_padrao(String imagem_padrao) {
		this.imagem_padrao = imagem_padrao;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Long getIdTipo() {
		return idTipo;
	}

	public void setIdTipo(Long idTipo) {
		this.idTipo = idTipo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getImagemEnviada() {
		return imagemEnviada;
	}

	public void setImagemEnviada(String imagemEnviada) {
		this.imagemEnviada = imagemEnviada;
	}

	public String getExtensao() {
		return extensao;
	}

	public void setExtensao(String extensao) {
		this.extensao = extensao;
	}

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	public Long getIdImg() {
		return idImg;
	}

	public void setIdImg(Long idImg) {
		this.idImg = idImg;
	}
	
	
	
}
