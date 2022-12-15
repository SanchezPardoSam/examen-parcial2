package com.sanchez.examen02.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sanchez.examen02.entity.Usuario;
import com.sanchez.examen02.exceptions.NoDataFoundException;
import com.sanchez.examen02.repository.UsuarioRepository;
import com.sanchez.examen02.service.UsuarioService;

import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {
	@Autowired
	private UsuarioService service;
	
	@Autowired
	private UsuarioRepository repository;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String jwt = getJwtFromRequest(request);
			if(StringUtils.hasText(jwt) && service.validateToken(jwt)) {
				int usuarioId=Integer.parseInt(service.getUserFromToken(jwt));
				Usuario usuario=repository.findById(usuarioId)
						.orElseThrow(()-> new NoDataFoundException("No existe el Usuario"));
				UsuarioPrincipal principal = UsuarioPrincipal.create(usuario);
				UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(principal, null,principal.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (Exception e) {
			log.error("Error al autenticar al usuario",e);
		}
		filterChain.doFilter(request, response);
		
		
	}
	public String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken=request.getHeader("Authorization");
		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7,bearerToken.length());
		}
		return null;
	}
}
