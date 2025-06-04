package com.clinicas.repository;

import com.clinicas.model.GradeHoraria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;

@Repository
public interface GradeHorariaRepository extends JpaRepository<GradeHoraria, Long> {
    List<GradeHoraria> findByDiaSemana(DayOfWeek diaSemana);

}
