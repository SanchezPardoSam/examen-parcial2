package com.sanchez.examen02.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sanchez.examen02.entity.Pagos;

@Repository
public interface PagoRepository extends JpaRepository<Pagos, Integer>{
	List<Pagos> findByFechaContaining(int id, Pageable page);
	Page<Pagos> findAllBySocio_Id(int id, Pageable page);
}
