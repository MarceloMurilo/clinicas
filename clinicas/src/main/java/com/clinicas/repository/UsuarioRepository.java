package com.clinicas.repository;

import com.clinicas.model.Papel;
import com.clinicas.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmailAndSenha(String email, String senha);
    
    // ✅ método para verificar se o e-mail já existe
    Optional<Usuario> findByEmail(String email);
    
    List<Usuario> findByPapel(Papel papel);

    

}
