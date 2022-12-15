package com.sanchez.examen02.converters;

import java.sql.Date;

import com.sanchez.examen02.dtos.SocioDTO;
import com.sanchez.examen02.entity.Socio;

public class SocioConverter extends AbstractConverter<Socio, SocioDTO>{

	@Override
	public SocioDTO fromEntity(Socio entity) {
		if (entity == null) return null;
		
		return SocioDTO.builder()
				.id(entity.getId())
				.nombre(entity.getNombre())
				.apellidos(entity.getApellidos())
				.documento(entity.getDocumento())
				.telefono(entity.getTelefono())
				.email(entity.getEmail())
				.habilitado(entity.getHabilitado())
				.build();
	}

	@Override
	public Socio fromDTO(SocioDTO dto) {
		if (dto==null) return null;
		
		Socio socio = new Socio();
		
		socio.setId(dto.getId());
		socio.setNombre(dto.getNombre());
		socio.setApellidos(dto.getApellidos());
		socio.setDocumento(dto.getDocumento());
		socio.setTelefono(dto.getTelefono());
		socio.setEmail(dto.getEmail());		
		socio.setHabilitado(dto.isHabilitado());
		return socio;
	}

}
