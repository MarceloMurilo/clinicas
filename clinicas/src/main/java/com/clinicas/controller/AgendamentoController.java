package com.clinicas.controller;
import com.clinicas.model.Agendamento;
import com.clinicas.model.Disponibilidade;
import com.clinicas.model.StatusAgendamento;
import com.clinicas.repository.AgendamentoRepository;
import com.clinicas.repository.DisponibilidadeRepository;
import com.clinicas.service.AgendamentoService;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/agendamentos")
@CrossOrigin(origins = "*")
public class AgendamentoController {

    private final AgendamentoService service;
    private final AgendamentoRepository agendamentoRepository;
    private final DisponibilidadeRepository disponibilidadeRepository;

    public AgendamentoController(AgendamentoService service,
                                  AgendamentoRepository agendamentoRepository,
                                  DisponibilidadeRepository disponibilidadeRepository) {
        this.service = service;
        this.agendamentoRepository = agendamentoRepository;
        this.disponibilidadeRepository = disponibilidadeRepository;
    }

    @PostMapping
    public Agendamento agendar(@RequestBody Agendamento agendamento) {
    	 agendamento.setStatus(StatusAgendamento.AGENDADO);
        // Salvar o agendamento
        Agendamento novo = service.salvar(agendamento);

        // Atualizar a disponibilidade como ocupada
        Disponibilidade disponibilidade = disponibilidadeRepository
        	    .findByEspecialidadeAndAlunoAndDataHora(
        	        agendamento.getEspecialidade(),
        	        agendamento.getAluno(),
        	        agendamento.getDataHora()
        	    ).orElseThrow(() -> new RuntimeException("Disponibilidade não encontrada"));


        disponibilidade.setDisponivel(false);
        disponibilidadeRepository.save(disponibilidade);

        return novo;
    }

    @GetMapping
    public List<Agendamento> listar() {
        return service.listarTodos();
    }

    @PutMapping("/{id}/gratuito")
    public Agendamento marcarComoGratuito(@PathVariable Long id) {
        return service.marcarComoGratuito(id);
    }

    @GetMapping("/paciente/{nome}")
    public List<Agendamento> listarPorPaciente(@PathVariable String nome) {
        return agendamentoRepository.findByPaciente(nome);
    }
    
    @DeleteMapping("/{id}/cancelar")
    public ResponseEntity<?> cancelarAgendamento(@PathVariable Long id) {
        Optional<Agendamento> agendamentoOpt = agendamentoRepository.findById(id);

        if (agendamentoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Agendamento agendamento = agendamentoOpt.get();

        // Reabrir a disponibilidade da consulta
        Optional<Disponibilidade> disponibilidadeOpt = disponibilidadeRepository
                .findByEspecialidadeAndAlunoAndDataHora(
                    agendamento.getEspecialidade(),
                    agendamento.getAluno(),
                    agendamento.getDataHora());

        if (disponibilidadeOpt.isPresent()) {
            Disponibilidade disponibilidade = disponibilidadeOpt.get();
            disponibilidade.setDisponivel(true);
            disponibilidadeRepository.save(disponibilidade);
        }

        // Excluir o agendamento
        agendamentoRepository.deleteById(id);

        return ResponseEntity.ok("Agendamento cancelado com sucesso!");
    }

    
    @PutMapping("/{id}/confirmar")
    public ResponseEntity<Agendamento> confirmar(@PathVariable Long id) {
        Optional<Agendamento> agendamentoOptional = agendamentoRepository.findById(id);
        if (agendamentoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Agendamento agendamento = agendamentoOptional.get();
        agendamento.setStatus(StatusAgendamento.CONFIRMADO);
        agendamentoRepository.save(agendamento);

        return ResponseEntity.ok(agendamento);
    }

    @PutMapping("/{id}/avaliar")
    public ResponseEntity<?> avaliarConsulta(@PathVariable Long id, @RequestBody Map<String, Integer> payload) {
        Optional<Agendamento> agendamentoOpt = agendamentoRepository.findById(id);
        if (agendamentoOpt.isEmpty()) return ResponseEntity.notFound().build();

        Agendamento agendamento = agendamentoOpt.get();
        agendamento.setAvaliacao(payload.get("avaliacao"));
        agendamentoRepository.save(agendamento);

        return ResponseEntity.ok("Avaliação registrada com sucesso.");
    }


}
