package com.sanchez.examen02.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="pagos")
@EntityListeners(AuditingEntityListener.class)
public class Pagos {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="socio_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Socio socio;
	
	@Column(name="monto", nullable = false,precision=6,scale=2)
	private Double monto;
	
	@Column(name="fecha", nullable = false)
	private Date fecha;
	
	@Column(name="created_at",nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createdAt;
	
	@Column(name="updated_at",nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date updatedAt;

}
