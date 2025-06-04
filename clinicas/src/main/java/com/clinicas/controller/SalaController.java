package com.clinicas.controller;

import com.clinicas.model.Sala;
import com.clinicas.model.SalaUnidade;
import com.clinicas.repository.SalaRepository;
import com.clinicas.repository.SalaUnidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salas")
@CrossOrigin(origins = "*")
public class SalaController {

    @Autowired
    private SalaRepository salaRepository;

    @Autowired
    private SalaUnidadeRepository salaUnidadeRepository;

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody Sala sala) {
        List<Sala> existentes = salaRepository.findByAreaClinica(sala.getAreaClinica());

        if (!existentes.isEmpty()) {
            Sala existente = existentes.get(0);
            existente.setSalas(sala.getSalas());
            existente.setQuantidade(sala.getSalas().size());
            salaRepository.save(existente);

            // Remove as unidades anteriores
            salaUnidadeRepository.deleteAll(salaUnidadeRepository.findByAreaClinica(sala.getAreaClinica()));
        } else {
            sala.setQuantidade(sala.getSalas().size());
            salaRepository.save(sala);
        }

        // Cria novas unidades com base nas salas selecionadas
        for (String nomeSala : sala.getSalas()) {
            SalaUnidade s = new SalaUnidade();
            s.setAreaClinica(sala.getAreaClinica());
            s.setNome(nomeSala);
            s.setOcupada(false);
            salaUnidadeRepository.save(s);
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public List<Sala> listar() {
        return salaRepository.findAll();
    }

    @GetMapping("/unidades/{areaClinica}")
    public List<SalaUnidade> listarUnidadesPorArea(@PathVariable String areaClinica) {
        return salaUnidadeRepository.findByAreaClinica(areaClinica);
    }
    
    @GetMapping("/especialidade/{areaClinica}")
    public List<SalaUnidade> listarPorEspecialidade(@PathVariable String areaClinica) {
        return salaUnidadeRepository.findByAreaClinica(areaClinica);
    }

}
