package com.sanchez.examen02.converters;

import com.sanchez.examen02.dtos.PagoDTO;
import com.sanchez.examen02.entity.Pagos;
import com.sanchez.examen02.entity.Socio;

public class PagoConverter extends AbstractConverter<Pagos, PagoDTO>{

	@Override
	public PagoDTO fromEntity(Pagos entity) {
		if (entity == null) return null;
		return PagoDTO.builder()
				.id(entity.getId())
				.idSocio(entity.getSocio().getId())
				.fecha(entity.getFecha())				
				.monto(entity.getMonto())
				.build();
	}

	@Override
	public Pagos fromDTO(PagoDTO dto) {
		if (dto==null) return null;
		Pagos pago = new Pagos();
		Socio socio = new Socio();
		
		socio.setId(dto.getId());
		
		pago.setId(dto.getId());
		pago.setFecha(dto.getFecha());
		pago.setMonto(dto.getMonto());
		pago.setSocio(socio);
		
		return pago;
	}

}
