package com.clinicas.model;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String periodo;
    private String celular;
    private String senha;
    @Enumerated(EnumType.STRING)
    private Papel papel; // Enum: ADMIN, COORDENADOR, etc.
    @Column(name = "data_cadastro")
    private LocalDate dataCadastro;
    private String areaClinica; // Apenas para preceptores
    private boolean ativo;

   
}
