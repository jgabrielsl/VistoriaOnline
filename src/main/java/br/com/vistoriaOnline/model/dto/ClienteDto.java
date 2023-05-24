package br.com.vistoriaOnline.model.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import br.com.vistoriaOnline.model.Cliente;

public class ClienteDto {
	
	private Long id;
	private String email;
	private String nome;
	private String telefone;
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
	private String link;
	private boolean ativo;
	
	public ClienteDto(Long id, String email, String nome, String telefone) {
		this.id = id;
		this.email = email;
		this.nome = nome;
		this.telefone = telefone;
	}

	public ClienteDto(Cliente cliente) {
		this.id = cliente.getId();
		this.email = cliente.getEmail();
		this.nome = cliente.getNome();
		this.telefone = cliente.getTelefone();
		this.ativo = cliente.isAtivo();
		this.link = cliente.getLink();
		this.cep = cliente.getCep();
		this.cpf = cliente.getCpf();
		this.uf = cliente.getUf();
		this.municipio = cliente.getMunicipio();
		this.anoModelo= cliente.getAnoModelo();
		this.endereco = cliente.getEndereco();
		this.marca = cliente.getMarca();
		this.modelo = cliente.getModelo();
		this.valorTabelaFipe = cliente.getValorTabelaFipe();
		this.codigoTabelaFipe = cliente.getCodigoTabelaFipe();
	}

	public ClienteDto() {}
	
	public ClienteDto(Cliente cliente, String base) {
		this.id = cliente.getId();
		this.email = cliente.getEmail();
		this.nome = cliente.getNome();
		this.telefone = cliente.getTelefone();
		this.ativo = cliente.isAtivo();
		this.link = base+cliente.getLink();
		this.cep = cliente.getCep();
		this.cpf = cliente.getCpf();
		this.uf = cliente.getUf();
		this.municipio = cliente.getMunicipio();
		this.anoModelo= cliente.getAnoModelo();
		this.endereco = cliente.getEndereco();
		this.marca = cliente.getMarca();
		this.modelo = cliente.getModelo();
		this.valorTabelaFipe = cliente.getValorTabelaFipe();
		this.codigoTabelaFipe = cliente.getCodigoTabelaFipe();
	}

	public String getEmail() {
		return email;
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

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
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

	public static List<ClienteDto> converter(List<Cliente> clientes) {
		return clientes.stream().map(ClienteDto::new).collect(Collectors.toList());
	}

	public Cliente buildUsuario() {
		return new Cliente(this.id, this.nome, this.email,this.telefone, this.ativo, this.endereco, this.municipio, this.uf, this.cpf, this.cep, this.marca, this.modelo, this.anoModelo, this.codigoTabelaFipe, this.valorTabelaFipe);
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public void editUsuario(Cliente cliente) {
		cliente.setAnoModelo(this.anoModelo);
		cliente.setAtivo(this.ativo);
		cliente.setCep(this.cep);
		cliente.setCodigoTabelaFipe(this.codigoTabelaFipe);
		cliente.setCpf(this.cpf);
		cliente.setEmail(this.email);
		cliente.setEndereco(this.endereco);
		cliente.setMunicipio(this.municipio);
		cliente.setNome(this.nome);
		cliente.setTelefone(this.telefone);
		cliente.setUf(this.uf);
		cliente.setValorTabelaFipe(this.valorTabelaFipe);
		cliente.setMarca(this.marca);
		cliente.setModelo(this.modelo);
	}

	public List<String> validaCampos() {
		List<String> valida = new ArrayList<String>();
		if(nome==null || nome== "") valida.add("Campo nome obrigatório");
		if(email==null || email== "") valida.add("Campo email obrigatório");
		if(telefone==null || telefone== "") valida.add("Campo telefone obrigatório");
		if(endereco==null || endereco== "") valida.add("Campo endereco obrigatório");
		if(municipio==null || municipio== "") valida.add("Campo municipio obrigatório");
		if(uf==null || uf== "") valida.add("Campo uf obrigatório");
		if(cpf==null || cpf== "") valida.add("Campo cpf obrigatório");
		if(cep==null || cep== "") valida.add("Campo cep obrigatório");
		if(marca==null || marca== "") valida.add("Campo marca obrigatório");
		if(modelo==null || modelo== "") valida.add("Campo modelo obrigatório");
		if(anoModelo==null || anoModelo== "") valida.add("Campo Ano Modelo obrigatório");
		if(codigoTabelaFipe==null || codigoTabelaFipe== "") valida.add("Campo Codigo Tabela Fipe obrigatório");
		if(valorTabelaFipe==null || valorTabelaFipe== "") valida.add("Campo Valor Tabela Fipe obrigatório");
		return valida;
	}
}

