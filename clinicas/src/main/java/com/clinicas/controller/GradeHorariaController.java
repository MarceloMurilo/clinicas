package com.clinicas.controller;

import com.clinicas.model.GradeHoraria;
import com.clinicas.repository.GradeHorariaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;

@RestController
@RequestMapping("/grade-horaria")
public class GradeHorariaController {

    @Autowired
    private GradeHorariaRepository repository;

    // Lista todos os registros
    @GetMapping
    public List<GradeHoraria> listarTodos() {
        return repository.findAll();
    }

    // Lista horários por dia da semana (ex: MONDAY)
    @GetMapping("/dia/{diaSemana}")
    public List<GradeHoraria> buscarPorDia(@PathVariable String diaSemana) {
        try {
            DayOfWeek dia = DayOfWeek.valueOf(diaSemana.toUpperCase());
            return repository.findByDiaSemana(dia);
        } catch (IllegalArgumentException e) {
            return List.of(); // retorna lista vazia se o nome estiver incorreto
        }
    }
}
