package br.com.vistoriaOnline.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.vistoriaOnline.model.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {

	@Query("SELECT u FROM Role u WHERE u.name = :name")
    public Role getRoleByName(@Param("name") String name);
	
	@Query("select p from Role p join p.usuarios u where u.nome = :nome")
	List<Role> findAllByNome(@Param("nome") String nome);
	
}
