package com.clinicas.repository;

import com.clinicas.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    List<Agendamento> findByPaciente(String paciente); // <- este método permite buscar por nome do paciente
}