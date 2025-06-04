package com.clinicas.controller;

import com.clinicas.model.Papel;
import com.clinicas.model.Usuario;
import com.clinicas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin(origins = "*") // Permitir requisições externas (celular)
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // 🔐 Login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario dadosLogin) {
        return usuarioRepository.findByEmailAndSenha(dadosLogin.getEmail(), dadosLogin.getSenha())
                .<ResponseEntity<?>>map(usuario -> ResponseEntity.ok(usuario))
                .orElseGet(() -> ResponseEntity.status(401).body("Usuário ou senha inválidos"));
    }

    @PostMapping("/login-interno")
    public ResponseEntity<LoginResponse> loginInterno(@RequestBody LoginRequest login) {
        Optional<Usuario> optUsuario = usuarioRepository.findByEmailAndSenha(login.email, login.senha);

        if (optUsuario.isPresent()) {
            Usuario usuario = optUsuario.get();
            if (!usuario.isAtivo()) {
                return ResponseEntity.status(403).build();
            }
            return ResponseEntity.ok(new LoginResponse(null, usuario.getPapel().name()));
        }

        return ResponseEntity.status(401).build();
    }

    // ➕ Cadastro (assumindo que todos são pacientes)
    @PostMapping("/usuarios")
    public Usuario cadastrarUsuario(@RequestBody Usuario usuario) {
        usuario.setAtivo(true); // Define o paciente como ativo
        return usuarioRepository.save(usuario);
    }
    
 // Classes auxiliares internas
    public static class LoginRequest {
        public String email;
        public String senha;
    }

    public static class LoginResponse {
        public String token; // null no MVP
        public String papel;
        public LoginResponse(String token, String papel) {
            this.token = token;
            this.papel = papel;
        }
    }

 // 📋 Lista todos os usuários (pacientes)
    @GetMapping("/usuarios")
    public List<Usuario> listarTodosUsuarios() {
        return usuarioRepository.findAll(); // Retorna todos os usuários, se todos forem pacientes
    }
    
    @GetMapping("/usuarios/existe-email")
    public boolean emailExiste(@RequestParam String email) {
        return usuarioRepository.findByEmail(email).isPresent();
    }
    
    @GetMapping("/usuarios/email/{email}")
    public ResponseEntity<?> buscarPorEmail(@PathVariable String email) {
        return usuarioRepository.findByEmail(email)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/usuarios/{id}")
    public ResponseEntity<Usuario> atualizar(@PathVariable Long id, @RequestBody Usuario dados) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    usuario.setNome(dados.getNome());
                    usuario.setCelular(dados.getCelular());
                    if (dados.getSenha() != null && !dados.getSenha().isEmpty()) {
                        usuario.setSenha(dados.getSenha());
                    }
                    Usuario atualizado = usuarioRepository.save(usuario);
                    return ResponseEntity.ok(atualizado);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    public static class NovoUsuarioRequest {
        public String nome;
        public String email;
        public String celular;
        public String senha;
        public Papel papel;
        public String areaClinica;
    }

    @PostMapping("/usuarios-internos")
    public ResponseEntity<?> cadastrarUsuarioInterno(@RequestBody NovoUsuarioRequest dto) {
        // Verifica se já existe um usuário com o mesmo e-mail
        if (usuarioRepository.findByEmail(dto.email).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                                 .body("E-mail já cadastrado. Por favor, use outro.");
        }

        // Cria novo usuário a partir do DTO
        Usuario novo = new Usuario();
        novo.setNome(dto.nome);
        novo.setEmail(dto.email);
        novo.setCelular(dto.celular);
        novo.setSenha(dto.senha);
        novo.setPapel(dto.papel);
        novo.setAtivo(true);

        if (dto.papel == Papel.PRECEPTOR && dto.areaClinica != null) {
            novo.setAreaClinica(dto.areaClinica);
        }

        Usuario salvo = usuarioRepository.save(novo);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

 // DTO para requisição de cadastro de aluno
    public static class AlunoRequest {
        public String nome;
        public String email;
        public String senha;
        public String celular;
        public String emailPreceptor; // usado para identificar a área clínica
        public String periodo;

    }

    // Método no UsuarioController.java
    @PostMapping("/alunos")
    public ResponseEntity<?> cadastrarAluno(@RequestBody AlunoRequest dto) {
        // Verifica se o preceptor existe e é válido
        Optional<Usuario> preceptor = usuarioRepository.findByEmail(dto.emailPreceptor);

        if (preceptor.isEmpty() || preceptor.get().getPapel() != Papel.PRECEPTOR) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Preceptor inválido ou não encontrado.");
        }

        // Verifica se o e-mail do aluno já está cadastrado
        if (usuarioRepository.findByEmail(dto.email).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("E-mail do aluno já cadastrado.");
        }

        Usuario aluno = new Usuario();
        aluno.setNome(dto.nome);
        aluno.setEmail(dto.email);
        aluno.setSenha(dto.senha);
        aluno.setCelular(dto.celular);
        aluno.setPapel(Papel.ALUNO);
        aluno.setAtivo(true);
        aluno.setPeriodo(dto.periodo);
        aluno.setAreaClinica(preceptor.get().getAreaClinica());
        aluno.setDataCadastro(LocalDate.now());
        

        Usuario salvo = usuarioRepository.save(aluno);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping("/alunos")
    public ResponseEntity<List<Usuario>> listarAlunos() {
        List<Usuario> alunos = usuarioRepository.findByPapel(Papel.ALUNO);
        return ResponseEntity.ok(alunos);
    }


}
