package com.sanchez.examen02.repository;

import org.springframework.data.domain.Pageable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sanchez.examen02.entity.Socio;



@Repository
public interface SocioRepository extends JpaRepository<Socio, Integer>{
	List<Socio> findByNombreContaining(String nombre,Pageable page);
}
