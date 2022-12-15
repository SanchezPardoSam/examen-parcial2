package com.sanchez.examen02.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sanchez.examen02.entity.Pagos;
import com.sanchez.examen02.entity.Socio;
import com.sanchez.examen02.exceptions.GeneralServiceException;
import com.sanchez.examen02.exceptions.NoDataFoundException;
import com.sanchez.examen02.exceptions.ValidateServiceException;
import com.sanchez.examen02.repository.PagoRepository;
import com.sanchez.examen02.repository.SocioRepository;
import com.sanchez.examen02.service.PagoService;
import com.sanchez.examen02.validators.PagoValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PagoServiceImpl implements PagoService{
	@Autowired
	private SocioRepository repositorySocio;
	
	@Autowired
	private PagoRepository repositoryPago;
	
	@Override
	public List<Pagos> finById(int id, Pageable page) {
		try {
			Socio existeRegistro = repositorySocio.findById(id)
					.orElseThrow(()->new NoDataFoundException("No existe este sociooo"));	
			
			List<Pagos> pagos = repositoryPago.findAllBySocio_Id(id, page).toList();
			
			return pagos;
		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}

	@Override
	public Pagos save(Pagos pago) {
		try {
			PagoValidator.save(pago);

			Socio existeRegistro = repositorySocio.findById(pago.getSocio().getId())
					.orElseThrow(()->new NoDataFoundException("No existe este sociooo"));	
			
			pago.setSocio(existeRegistro);				
			pago = repositoryPago.save(pago);
			
			return pago;			

		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}

}
