package com.sanchez.examen02.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sanchez.examen02.converters.SocioConverter;
import com.sanchez.examen02.dtos.SocioDTO;
import com.sanchez.examen02.entity.Socio;
import com.sanchez.examen02.exceptions.NoDataFoundException;
import com.sanchez.examen02.repository.SocioRepository;
import com.sanchez.examen02.service.SocioService;
import com.sanchez.examen02.utils.WrapperResponse;

@RestController
@RequestMapping("/v1/socios")
public class SocioController {
	@Autowired
	private SocioService service;
	
	@Autowired
	private SocioRepository repositorySocio;
	
	private SocioConverter converter = new SocioConverter();
	
	@GetMapping
	public ResponseEntity<List<SocioDTO>> findAll(
			@RequestParam(value = "offset", required = false, defaultValue = "0") int pageNumber,
			@RequestParam(value = "limit", required = false, defaultValue = "5") int pageSize
			){
		Pageable page = PageRequest.of(pageNumber, pageSize);
		List<Socio> socios = service.findAll(page);
		List<SocioDTO> sociosDTO=converter.fromEntity(socios);
		return new WrapperResponse(true, "success", sociosDTO).createResponse(HttpStatus.OK);		
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<WrapperResponse<SocioDTO>> findById(@PathVariable("id") int id) {
		Socio socio = service.findById(id);
		SocioDTO socioDTO=converter.fromEntity(socio);
		return new WrapperResponse<SocioDTO>(true,"succes",socioDTO).createResponse(HttpStatus.OK);
		
	}
	
	@PostMapping()
	public ResponseEntity<SocioDTO> create(@RequestBody SocioDTO articuloDTO) {		
		Socio createSocio = service.save(converter.fromDTO(articuloDTO));
		SocioDTO socioReturn = converter.fromEntity(createSocio);
		return new WrapperResponse(true, "success", socioReturn).createResponse(HttpStatus.CREATED);
	}
	@PutMapping(value = "/{id}")
	public ResponseEntity<SocioDTO> update(@PathVariable("id") int id,@RequestBody SocioDTO articuloDTO) {
		Socio updateSocio = service.save(converter.fromDTO(articuloDTO));
		SocioDTO socioReturn = converter.fromEntity(updateSocio);
		return new WrapperResponse(true, "success", socioReturn).createResponse(HttpStatus.OK);
	}
	
	@PutMapping(value = "/habilitar/{id}")
	public ResponseEntity<SocioDTO> habilitar(@PathVariable("id") int id) {
		
		Socio updateSocio = repositorySocio.findById(id)
				.orElseThrow(()->new NoDataFoundException("No existe el registro"));
		
		updateSocio.setHabilitado(true);
		updateSocio = service.save(updateSocio);
		SocioDTO socioReturn = converter.fromEntity(updateSocio);
		return new WrapperResponse(true, "success", socioReturn).createResponse(HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") int id) {
		service.delete(id);
		return new WrapperResponse(true, "success", null).createResponse(HttpStatus.OK);
	}
}
