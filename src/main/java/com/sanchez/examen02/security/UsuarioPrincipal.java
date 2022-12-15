package com.sanchez.examen02.security;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.sanchez.examen02.entity.Usuario;

import lombok.AllArgsConstructor;
import lombok.Getter;




@Getter
@AllArgsConstructor
public class UsuarioPrincipal implements UserDetails {

	private Usuario usuario;
	private Collection<? extends GrantedAuthority> authorities;
	
	public static UsuarioPrincipal create(Usuario usuario) {
		List<GrantedAuthority> authorities = Collections
				.singletonList(new SimpleGrantedAuthority("ADMIN"));
		return new UsuarioPrincipal(usuario, authorities);
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return usuario.getPassword();
	}

	@Override
	public String getUsername() {
		return usuario.getEmail();
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
		return true;
	}

}
