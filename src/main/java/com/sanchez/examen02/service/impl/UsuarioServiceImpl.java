package com.sanchez.examen02.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sanchez.examen02.converters.UsuarioConverter;
import com.sanchez.examen02.dtos.LoginRequestDTO;
import com.sanchez.examen02.dtos.LoginResponseDTO;
import com.sanchez.examen02.entity.Usuario;
import com.sanchez.examen02.exceptions.GeneralServiceException;
import com.sanchez.examen02.exceptions.NoDataFoundException;
import com.sanchez.examen02.exceptions.ValidateServiceException;
import com.sanchez.examen02.repository.UsuarioRepository;
import com.sanchez.examen02.service.UsuarioService;
import com.sanchez.examen02.validators.UsuarioValidator;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UsuarioServiceImpl implements UsuarioService {
	@Autowired
	private UsuarioRepository repository;
	
	private UsuarioConverter converter=new UsuarioConverter();
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Override
	public Usuario create(Usuario usuario) {
		try {
			UsuarioValidator.save(usuario);
			Usuario existUsuario=repository.findByEmail(usuario.getEmail()).orElse(null);
			if(existUsuario!=null) throw new ValidateServiceException("El usuario ya existe.");
			
			String passEncoder= encoder.encode(usuario.getPassword());
			usuario.setPassword(passEncoder);
			usuario.setActivo(true);
			repository.save(usuario);
			return usuario;
			
		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}
	@Override
	public LoginResponseDTO login(LoginRequestDTO request) {
		try {
			Usuario usuario=repository.findByEmail(request.getEmail())
					.orElseThrow(() -> new ValidateServiceException("Usuarios o password incorrectos."));
			if(!encoder.matches(request.getPassword(), usuario.getPassword())) throw new ValidateServiceException("Usuario o Password Incorrectos");
			
			//if(!usuario.getPassword().equals(request.getPassword())) throw new ValidateServiceException("Usuario o Password Incorrectos");
			if(!usuario.getActivo()) throw new ValidateServiceException("Usuario o Password Incorrectos");
			String token=createToken(usuario);
			return new LoginResponseDTO(converter.fromEntity(usuario),token);	
		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}
	@Value("${jwt.password}")
	private String jwtSecret; 
	public String createToken(Usuario usuario) {
		Date now=new Date();
		Date expiryDate=new Date(now.getTime() + 1000*60*60);
		return Jwts.builder()
				.setSubject(Long.toString(usuario.getId()))
				.setIssuedAt(now)
				.setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
		
	}
	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
			return true;
		}catch (UnsupportedJwtException e) {
			log.error("JWT in a particular format/configuration that does not match the format expected");
		}catch (MalformedJwtException e) {
			log.error(" JWT was not correctly constructed and should be rejected");
		}catch (SignatureException e) {
			log.error("Signature or verifying an existing signature of a JWT failed");
		}catch (ExpiredJwtException e) {
			log.error("JWT was accepted after it expired and must be rejected");
		}
		return false;
	}
	@Override
	public String getUserFromToken(String jwt) {
		try {
			return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwt).getBody().getSubject();	
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ValidateServiceException("Invalid Token");
		}
		
	}
}
