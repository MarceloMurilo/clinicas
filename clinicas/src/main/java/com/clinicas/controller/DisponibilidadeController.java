package com.clinicas.controller;

import com.clinicas.model.Disponibilidade;
import org.springframework.http.ResponseEntity;
import com.clinicas.repository.DisponibilidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/disponibilidades")
@CrossOrigin(origins = "*")
public class DisponibilidadeController {

    @Autowired
    private DisponibilidadeRepository repository;

    // ✅ Cadastrar uma nova disponibilidade
    @PostMapping
    public Disponibilidade cadastrar(@RequestBody Disponibilidade disponibilidade) {
        return repository.save(disponibilidade);
    }

    // ✅ Listar todas as disponibilidades
    @GetMapping
    public List<Disponibilidade> listar() {
        return repository.findAll();
    }

    // ✅ Filtrar por especialidade e aluno (disponível = true)
    @GetMapping("/filtrar")
    public List<Disponibilidade> filtrar(
        @RequestParam String especialidade,
        @RequestParam String aluno
    ) {
        LocalDateTime agora = LocalDateTime.now();
        return repository.findByEspecialidadeAndAlunoAndDisponivelAndDataHoraAfterOrderByDataHoraAsc(
            especialidade, aluno, true, agora
        );
    }



    // ✅ Listar apenas as futuras e disponíveis
    @GetMapping("/livres")
    public List<Disponibilidade> listarDisponiveisFuturos() {
        LocalDateTime agora = LocalDateTime.now();
        return repository.findByDisponivelTrueAndDataHoraAfterOrderByDataHoraAsc(agora);
    }

    // ✅ Marcar uma disponibilidade como ocupada
    @PutMapping("/{id}/ocupar")
    public Disponibilidade ocupar(@PathVariable Long id) {
        Disponibilidade d = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Disponibilidade não encontrada"));
        d.setDisponivel(false);
        return repository.save(d);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        return repository.findById(id).map(disponibilidade -> {
            repository.delete(disponibilidade);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }

}
