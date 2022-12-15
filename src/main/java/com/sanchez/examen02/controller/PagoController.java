package com.sanchez.examen02.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sanchez.examen02.converters.PagoConverter;
import com.sanchez.examen02.dtos.PagoDTO;
import com.sanchez.examen02.entity.Pagos;
import com.sanchez.examen02.service.PagoService;
import com.sanchez.examen02.utils.WrapperResponse;

@RestController
@RequestMapping("/v1/pagos")
public class PagoController {
	@Autowired
	private PagoService service;
	
	private PagoConverter converter = new PagoConverter();
	
	@GetMapping
	public ResponseEntity<List<PagoDTO>> findAll(
			@RequestParam(name = "socioId") int id,
			@RequestParam(value = "offset", required = false, defaultValue = "0") int pageNumber,
			@RequestParam(value = "limit", required = false, defaultValue = "5") int pageSize
			){
		Pageable page = PageRequest.of(pageNumber, pageSize);
		
		List<Pagos> articulos = service.finById(id, page);
		List<PagoDTO> articulosDTO=converter.fromEntity(articulos);
		
		return new WrapperResponse(true, "success", articulosDTO).createResponse(HttpStatus.OK);		
	}
	
	@PostMapping(value = "/{id}")
	public ResponseEntity<PagoDTO> create(@PathVariable("id") int id, @RequestBody PagoDTO PagoDTO) {	
		
		Pagos createArticulo = new Pagos();
		
		createArticulo = converter.fromDTO(PagoDTO);
		createArticulo.getSocio().setId(id);
		createArticulo = service.save(createArticulo);
		
		PagoDTO articuloReturn = converter.fromEntity(createArticulo);
		
		return new WrapperResponse(true, "success", articuloReturn).createResponse(HttpStatus.CREATED);
	}
}
