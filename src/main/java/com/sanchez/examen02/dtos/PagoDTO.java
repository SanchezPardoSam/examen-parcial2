package com.sanchez.examen02.dtos;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PagoDTO {
	private int id;
	private int idSocio;	
	private Date fecha;
	private Double monto;
}
