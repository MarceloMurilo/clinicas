package com.clinicas.model;

import jakarta.persistence.*;

@Entity
public class SalaUnidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome; // ex: "Sala 1", "Sala A"

    private String areaClinica; // Psicologia, Fisioterapia...

    private boolean ocupada = false;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getAreaClinica() { return areaClinica; }
    public void setAreaClinica(String areaClinica) { this.areaClinica = areaClinica; }

    public boolean isOcupada() { return ocupada; }
    public void setOcupada(boolean ocupada) { this.ocupada = ocupada; }
}
