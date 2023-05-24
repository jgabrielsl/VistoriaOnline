package br.com.vistoriaOnline.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.vistoriaOnline.model.Cliente;

public interface ClienteRepository extends CrudRepository<Cliente, Long>{
	
	@Query("SELECT u FROM Cliente u WHERE u.email = :email")
    public Cliente getClienteByEmail(@Param("email") String email);
	
	List<Cliente> findByEmail(String email);

	public List<Cliente> findAllByOrderByIdDesc();

	
}
