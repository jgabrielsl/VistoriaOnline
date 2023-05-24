package br.com.vistoriaOnline.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.vistoriaOnline.model.Cliente;
import br.com.vistoriaOnline.model.Imagem;
import br.com.vistoriaOnline.model.Tipo;

public interface ImagemRepository extends CrudRepository<Imagem, Long>{

	@Query(value = "SELECT f from Imagem f where f.tipo.id = :id and f.cliente.id = :idCli and f.nextId IS NULL "
			+ "ORDER BY f.id")
	List<Imagem> encontraUltimoEnviado(@Param("id") Long id, @Param("idCli") Long idCli);

	Imagem findByTipoAndClienteAndNextIdIsNull(Tipo tipo, Cliente cliente);

	List<Imagem> findAllByClienteAndNextIdIsNull(Cliente id);
	
	@Query(value = "SELECT f from Imagem f left join fetch f.tipo where f.cliente.id = :idCli and f.nextId IS NULL "
			+ "ORDER BY f.id")
	List<Imagem> encontraPorClienteAndNextIdIsNull(@Param("idCli") Long idCli);
}
