package com.sanchez.examen02.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sanchez.examen02.converters.UsuarioConverter;
import com.sanchez.examen02.dtos.LoginRequestDTO;
import com.sanchez.examen02.dtos.LoginResponseDTO;
import com.sanchez.examen02.dtos.SignupRequestDTO;
import com.sanchez.examen02.dtos.UsuarioDTO;
import com.sanchez.examen02.entity.Usuario;
import com.sanchez.examen02.service.UsuarioService;
import com.sanchez.examen02.utils.WrapperResponse;



@RestController
@RequestMapping("/v1/")
public class UsuarioController {
	@Autowired
	private UsuarioService service;
	 
	private UsuarioConverter converter=new UsuarioConverter();
	
	@PostMapping(value="signup")
	public ResponseEntity<WrapperResponse<UsuarioDTO>> signup(@RequestBody SignupRequestDTO request){
		Usuario usuario = service.create(converter.signup(request));
		return new WrapperResponse<>(true,"success",converter.fromEntity(usuario)).createResponse(HttpStatus.CREATED);		
	}
	
	@PostMapping(value="login")
	public ResponseEntity<WrapperResponse<LoginResponseDTO>> login(@RequestBody LoginRequestDTO request){
		LoginResponseDTO response=service.login(request);
		return new WrapperResponse<>(true,"success",response).createResponse(HttpStatus.OK);
	}
}
