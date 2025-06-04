package com.clinicas.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String areaClinica;

    private int quantidade;

    @ElementCollection
    private List<String> salas;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public String getAreaClinica() {
        return areaClinica;
    }

    public void setAreaClinica(String areaClinica) {
        this.areaClinica = areaClinica;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public List<String> getSalas() {
        return salas;
    }

    public void setSalas(List<String> salas) {
        this.salas = salas;
    }
}
