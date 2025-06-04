package com.clinicas.repository;

import com.clinicas.model.Disponibilidade;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DisponibilidadeRepository extends JpaRepository<Disponibilidade, Long> {

    // 🔍 Buscar por especialidade, aluno e se está disponível
    List<Disponibilidade> findByEspecialidadeAndAlunoAndDisponivel(String especialidade, String aluno, boolean disponivel);

    // 🔍 Buscar todas as datas futuras disponíveis
    List<Disponibilidade> findByDisponivelTrueAndDataHoraAfterOrderByDataHoraAsc(LocalDateTime agora);

    // 🔍 Buscar uma disponibilidade específica para tornar indisponível após agendamento
    Optional<Disponibilidade> findByEspecialidadeAndAlunoAndDataHoraAndDisponivel(
            String especialidade, String aluno, LocalDateTime dataHora, boolean disponivel
    );

    // ✅ NOVO: Buscar somente as disponibilidades futuras filtradas por especialidade e aluno
   
    
    List<Disponibilidade> findByEspecialidadeAndAlunoAndDisponivelAndDataHoraAfterOrderByDataHoraAsc(
    	    String especialidade, String aluno, boolean disponivel, LocalDateTime dataHora
    	);

    
    Optional<Disponibilidade> findByEspecialidadeAndAlunoAndDataHora(
    	    String especialidade,
    	    String aluno,
    	    LocalDateTime dataHora
    	);

 // ✅ Conta quantas disponibilidades existem para uma especialidade em um horário
    long countByDataHoraAndEspecialidade(LocalDateTime dataHora, String especialidade);
}
