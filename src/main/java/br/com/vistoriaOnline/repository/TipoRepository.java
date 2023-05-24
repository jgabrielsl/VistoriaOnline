package br.com.vistoriaOnline.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.vistoriaOnline.model.Tipo;

public interface TipoRepository extends CrudRepository<Tipo, Long>{
	@Query(value = "SELECT f from Tipo f LEFT JOIN f.imagens s ON s.tipo = f and s.id = :id and s.nextId IS NULL ORDER BY f.ordem")
	List<Tipo> listaTiposEnviosCliente(@Param("id") Long id);
	
	@Query(value = "SELECT f from Tipo f ORDER BY f.ordem")
	List<Tipo> listaTipos(@Param("id") Long id);
}
