package br.com.vistoriaOnline.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.vistoriaOnline.model.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Long>{
	@Query("SELECT u FROM Usuario u WHERE u.email = :email")
    public Usuario getUserByEmail(@Param("email") String email);
	
	List<Usuario> findByEmail(String email);
}
