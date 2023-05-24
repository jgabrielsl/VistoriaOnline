package br.com.vistoriaOnline.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import br.com.vistoriaOnline.exceptions.GetImagemStatusException;

@Entity
public class ImagemStatus {
	
	public static ImagemStatus AGUARDANDO_ENVIO = new ImagemStatus((long)1, "AGUARDANDO_ENVIO", "Aguardando Envio");
	public static ImagemStatus ENVIADO = new ImagemStatus((long)2, "ENVIADO", "Enviado");
	public static ImagemStatus REPROVADO = new ImagemStatus((long)3, "REPROVADO", "Aguardando Reenvio");
	public static ImagemStatus APROVADO = new ImagemStatus((long)4, "APROVADO", "Aprovado");
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String status;
	private String descricao;
	
	public ImagemStatus () {}
	
	public ImagemStatus (Long id) {
		this.id = id;
	}
	
	public ImagemStatus(Long id, String status, String descricaco) {
		this.id = id;
		this.status = status;
		this.descricao = descricaco;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public static ImagemStatus getStatus(Integer id) {
		switch (id) {
		case 1:
			return AGUARDANDO_ENVIO;
		case 2:
			return ENVIADO;
		case 3:
			return REPROVADO;
		case 4:
			return APROVADO;
		default:
			throw new GetImagemStatusException("Status não encontrado");
		}
	}
}
