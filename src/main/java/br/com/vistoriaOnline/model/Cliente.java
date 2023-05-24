package br.com.vistoriaOnline.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Cliente{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String email;
	private String telefone;
    @Column(columnDefinition="BLOB")
	private byte[] privateKey;
	@Column(length = 65535, columnDefinition="text")
	private String link;
	private boolean ativo;
	private String endereco;
	private String municipio;
	private String uf;
	private String cpf;
	private String cep;
	private String marca;
	private String modelo;
	private String anoModelo;
	private String codigoTabelaFipe;
	private String valorTabelaFipe;
	
	@OneToMany()
	@JoinColumn(name = "client_id", referencedColumnName = "id")
    private Set<Imagem> imagens;
	
	public Cliente() {}
	
	

	public Cliente(Long id, String nome, String email, String telefone, boolean ativo,
			String endereco, String municipio, String uf, String cpf, String cep, String marca,String modelo, String anoModelo,
			String codigoTabelaFipe, String valorTabelaFipe) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
		this.ativo = ativo;
		this.endereco = endereco;
		this.municipio = municipio;
		this.uf = uf;
		this.cpf = cpf;
		this.cep = cep;
		this.marca = marca;
		this.modelo = modelo;
		this.anoModelo = anoModelo;
		this.codigoTabelaFipe = codigoTabelaFipe;
		this.valorTabelaFipe = valorTabelaFipe;
	}



	public Set<Imagem> getImagens() {
		return imagens;
	}



	public void setImagens(Set<Imagem> imagens) {
		this.imagens = imagens;
	}



	public String getMarca() {
		return marca;
	}



	public void setMarca(String marca) {
		this.marca = marca;
	}



	public String getModelo() {
		return modelo;
	}



	public void setModelo(String modelo) {
		this.modelo = modelo;
	}



	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getAnoModelo() {
		return anoModelo;
	}

	public void setAnoModelo(String anoModelo) {
		this.anoModelo = anoModelo;
	}

	public String getCodigoTabelaFipe() {
		return codigoTabelaFipe;
	}

	public void setCodigoTabelaFipe(String codigoTabelaFipe) {
		this.codigoTabelaFipe = codigoTabelaFipe;
	}

	public String getValorTabelaFipe() {
		return valorTabelaFipe;
	}

	public void setValorTabelaFipe(String valorTabelaFipe) {
		this.valorTabelaFipe = valorTabelaFipe;
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isAtivo() {
		return this.ativo;
	}
	
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public byte[] getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(byte[] privateKey) {
		this.privateKey = privateKey;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
}
