package com.clinicas.repository;

import com.clinicas.model.SalaUnidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalaUnidadeRepository extends JpaRepository<SalaUnidade, Long> {
    List<SalaUnidade> findByAreaClinica(String areaClinica);
}
