package br.com.vistoriaOnline.model.dto;

public class UploadDto {

	private String image64;
	private String token;
	private Long idTipo;
	private String extensao;
	
	public String getExtensao() {
		return extensao;
	}
	public void setExtensao(String extensao) {
		this.extensao = extensao;
	}
	public String getImage64() {
		return image64;
	}
	public void setImage64(String image64) {
		this.image64 = image64;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Long getIdTipo() {
		return idTipo;
	}
	public void setIdTipo(Long idTipo) {
		this.idTipo = idTipo;
	}
	
	
}
