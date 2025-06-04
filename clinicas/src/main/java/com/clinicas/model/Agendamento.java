package com.clinicas.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String paciente;
    private String aluno; 
    private String especialidade;

    private LocalDateTime dataHora;

    private boolean gratuito;
    private boolean confirmado;
    private Integer avaliacao; // 1 a 5 estrelas

    @Enumerated(EnumType.STRING)
    private StatusAgendamento status = StatusAgendamento.AGENDADO; // valor padrão
}
