package com.sanchez.examen02.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sanchez.examen02.entity.Socio;
import com.sanchez.examen02.exceptions.GeneralServiceException;
import com.sanchez.examen02.exceptions.NoDataFoundException;
import com.sanchez.examen02.exceptions.ValidateServiceException;
import com.sanchez.examen02.repository.SocioRepository;
import com.sanchez.examen02.service.SocioService;
import com.sanchez.examen02.validators.SocioValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SocioServiceImpl implements SocioService{
	@Autowired
	private SocioRepository repository;
	
	@Override
	public List<Socio> findAll(Pageable page) {
		try {
			List<Socio> socios=repository.findAll(page).toList();
			return socios;
		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<Socio> finByNombre(String nombre, Pageable page) {
		try {
			List<Socio> socios = repository.findByNombreContaining(nombre, page);
			return socios;
		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}

	@Override
	public Socio findById(int id) {
		try {
			Socio existeRegistro= repository.findById(id)
					.orElseThrow(()->new NoDataFoundException("No Existe el registro"));
			return existeRegistro;
		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}

	@Override
	public Socio save(Socio socio) {
		try {
			SocioValidator.save(socio);
			if(socio.getId()==0) {
				socio.setHabilitado(true);
				socio.setCreatedAt(new Date());
				socio.setUpdatedAt(new Date());
				Socio nuevoRegistro=repository.save(socio);
				return nuevoRegistro;
			}
			Socio existeRegistro=repository.findById(socio.getId())
					.orElseThrow(()->new NoDataFoundException("No existe el registro"));
			
			existeRegistro.setNombre(socio.getNombre());
			existeRegistro.setApellidos(socio.getApellidos());
			existeRegistro.setDocumento(socio.getDocumento());
			existeRegistro.setTelefono(socio.getTelefono());
			existeRegistro.setEmail(socio.getEmail());
			existeRegistro.setUpdatedAt(new Date());
			existeRegistro.setHabilitado(socio.getHabilitado());
			
			repository.save(existeRegistro);
			return existeRegistro;
		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(int id) {
		try {
			Socio existeRegistro=repository.findById(id)
					.orElseThrow(()->new NoDataFoundException("No existe el registro"));
			
			//existeRegistro.setActivo(false);;
			//repository.save(existeRegistro);
			repository.delete(existeRegistro);
		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new GeneralServiceException(e.getMessage(), e);
		}	
	}
}
