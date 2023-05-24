package br.com.vistoriaOnline;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.vistoriaOnline.model.Role;
import br.com.vistoriaOnline.model.Usuario;

public class UserDetailImp implements UserDetails{

	private static final long serialVersionUID = 1L;
	
	private Usuario user;
	
	public UserDetailImp(Usuario user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<Role> roles = user.getRoles();
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		for (Role role : roles) {
			authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getName()));
		}
		return authorities;
	}

	@Override
	public String getPassword() {
		return user.getSenha();
	}

	public Usuario getUser() {
		return new Usuario(user.getId(), user.getEmail(), user.getNome(), user.isAtivo());
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return user.isAtivo();
	}
	
	public void updateUser(Usuario user) {
		this.user = user;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}


}

