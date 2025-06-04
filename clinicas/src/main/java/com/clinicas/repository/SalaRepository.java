package com.clinicas.repository;

import com.clinicas.model.Sala;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaRepository extends JpaRepository<Sala, Long> {
	List<Sala> findByAreaClinica(String areaClinica);

}
