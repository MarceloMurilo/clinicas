package com.clinicas.model;

import jakarta.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
public class GradeHoraria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DayOfWeek diaSemana;

    private LocalTime horaInicio;
    private LocalTime horaFim;

    private int intervaloMinutos;

    private LocalTime intervaloAlmocoInicio;
    private LocalTime intervaloAlmocoFim;

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DayOfWeek getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(DayOfWeek diaSemana) {
        this.diaSemana = diaSemana;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(LocalTime horaFim) {
        this.horaFim = horaFim;
    }

    public int getIntervaloMinutos() {
        return intervaloMinutos;
    }

    public void setIntervaloMinutos(int intervaloMinutos) {
        this.intervaloMinutos = intervaloMinutos;
    }

    public LocalTime getIntervaloAlmocoInicio() {
        return intervaloAlmocoInicio;
    }

    public void setIntervaloAlmocoInicio(LocalTime intervaloAlmocoInicio) {
        this.intervaloAlmocoInicio = intervaloAlmocoInicio;
    }

    public LocalTime getIntervaloAlmocoFim() {
        return intervaloAlmocoFim;
    }

    public void setIntervaloAlmocoFim(LocalTime intervaloAlmocoFim) {
        this.intervaloAlmocoFim = intervaloAlmocoFim;
    }
}
