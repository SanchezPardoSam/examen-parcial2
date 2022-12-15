package com.sanchez.examen02.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="socios")
public class Socio {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false, length = 100)
	private String apellidos;
	
	@Column(unique = true,nullable = false, length = 100)
	private String nombre;
	
	@Column(nullable = false, length = 20)
	private String documento;
	
	@Column(nullable = false, length = 20)
	private String telefono;
	
	@Column(nullable = false, length = 50)
	private String email;
	
	@Column(name="habilitado",nullable=false)
	private Boolean habilitado;
	
	@Column(name="created_at",nullable = true, updatable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createdAt;
	
	@Column(name="updated_at",nullable = true, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")	
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date updatedAt;

}
