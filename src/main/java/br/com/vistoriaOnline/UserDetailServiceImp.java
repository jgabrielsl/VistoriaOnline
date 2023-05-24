package br.com.vistoriaOnline;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.com.vistoriaOnline.model.Usuario;
import br.com.vistoriaOnline.repository.UsuarioRepository;

public class UserDetailServiceImp implements UserDetailsService{

	@Autowired
    private UsuarioRepository userRepository;
     
    @Override
    public UserDetails loadUserByUsername(String email)
    		throws UsernameNotFoundException {
        Usuario user = userRepository.getUserByEmail(email);
         
        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }
         
        return new UserDetailImp(user);
    }
	
}
