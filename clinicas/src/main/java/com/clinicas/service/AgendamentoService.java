package com.clinicas.service;

import com.clinicas.model.Agendamento;
import com.clinicas.model.StatusAgendamento;
import com.clinicas.repository.AgendamentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendamentoService {

    private final AgendamentoRepository repository;

    public AgendamentoService(AgendamentoRepository repository) {
        this.repository = repository;
    }

    public Agendamento salvar(Agendamento agendamento) {
    	if (agendamento.getStatus() == null) {
            agendamento.setStatus(StatusAgendamento.AGENDADO);
        }
        return repository.save(agendamento);
    }

    public List<Agendamento> listarTodos() {
        return repository.findAll();
    }

    public Agendamento marcarComoGratuito(Long id) {
        Agendamento agendamento = repository.findById(id).orElseThrow();
        agendamento.setGratuito(true);
        return repository.save(agendamento);
    }
    public List<Agendamento> listarPorPaciente(String nome) {
        return repository.findByPaciente(nome);
    }

}
