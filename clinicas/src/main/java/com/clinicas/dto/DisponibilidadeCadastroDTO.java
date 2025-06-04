package com.clinicas.dto; // Ou seu pacote de DTOs

import java.time.LocalDateTime;

public class DisponibilidadeCadastroDTO {
    private String alunoEmail; // Ou Long alunoId;
    private String especialidade;
    private LocalDateTime dataHora;
    private int sala;
    // Não precisa de 'disponivel' se o backend sempre define como true
    // Não precisa de 'ocupado' pois o backend define como false

    // Getters e Setters
    public String getAlunoEmail() {
        return alunoEmail;
    }

    public void setAlunoEmail(String alunoEmail) {
        this.alunoEmail = alunoEmail;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public int getSala() {
        return sala;
    }

    public void setSala(int sala) {
        this.sala = sala;
    }
}