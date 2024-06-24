package com.grupo6.keepInventory.Service;

import com.grupo6.keepInventory.Model.Usuario;
import com.grupo6.keepInventory.Repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.apache.catalina.valves.rewrite.ResolverImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.file.FileStore;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordHashingService passwordHashingService;

    public Page<Usuario> getUsuariosPaginados(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return usuarioRepository.findAll(pageable);
    }

    public Optional<Usuario> getUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public ResponseEntity<?> adicionarUsuario(Usuario usuario) {
        if (usuario.getNome() == null || usuario.getSobrenome() == null || usuario.getRole() == null || usuario.getEmail() == null || usuario.getNome().isEmpty() || usuario.getSobrenome().isEmpty() || usuario.getRole().isEmpty() || usuario.getEmail().isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Campo vazio");
        }
        try {
            for (Usuario u : usuarioRepository.findAll()) {
                if (usuario.getEmail().equals(u.getEmail())) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O email já está associado a outra conta");
                }
            }
            Usuario novoUsuario = new Usuario(usuario.getNome(), usuario.getSobrenome(), usuario.getEmail(), usuario.getRole());
            novoUsuario = usuarioRepository.save(novoUsuario);
            return ResponseEntity.ok(novoUsuario);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request");
        }
    }

    public ResponseEntity<?> updatePassword(String email, String newPassword) {
        Optional<Usuario> temusuario = usuarioRepository.findByEmail(email);
        if (temusuario.isPresent()) {
            Usuario usuario = temusuario.get();
            newPassword = (passwordHashingService.hashPassword(newPassword));
            usuarioRepository.updateSenha(usuario.getId(), newPassword);
            return ResponseEntity.ok("Senha atualizada com sucesso");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request");
    }

    public ResponseEntity<?> toggleAtivo(Long id) {
        Optional<Usuario> temusuario = usuarioRepository.findById(id);
        if (temusuario.isPresent()) {
            Usuario usuario = temusuario.get();
            usuario.setAtivo(!usuario.isAtivo());
            Usuario updatedUsuario = usuarioRepository.save(usuario);
            return ResponseEntity.ok(updatedUsuario);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    public ResponseEntity<?> senhaReset(long id) {
        Optional<Usuario> temusuario = usuarioRepository.findById(id);
        if (temusuario.isPresent()) {
            Usuario usuario = temusuario.get();
            usuario.setSenha(PasswordHashingService.hashPassword("BioparkEdu"));
            Usuario updatedUsuario = usuarioRepository.save(usuario);
            return ResponseEntity.ok(updatedUsuario);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    public ResponseEntity<?> alterarUsuario(long id, Usuario usuarioInfo) {
        if (usuarioInfo.getNome() == null || usuarioInfo.getSobrenome() == null || usuarioInfo.getRole() == null || usuarioInfo.getEmail() == null || usuarioInfo.getNome().isEmpty() || usuarioInfo.getSobrenome().isEmpty() || usuarioInfo.getRole().isEmpty() || usuarioInfo.getEmail().isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Campo vazio");
        }
        Optional<Usuario> temusuario = usuarioRepository.findById(id);
        if (temusuario.isPresent()) {
            for (Usuario u : usuarioRepository.findAll()) {
                if (usuarioInfo.getEmail().equals(u.getEmail())) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O email já está associado a outra conta");
                }
            }
            Usuario usuario = temusuario.get();

            usuario.setEmail(usuarioInfo.getEmail());
            usuario.setNome(usuarioInfo.getNome());
            usuario.setSobrenome(usuarioInfo.getSobrenome());
            usuario.setRole(usuarioInfo.getRole());
            Usuario updatedUsuario = usuarioRepository.save(usuario);
            return ResponseEntity.ok(updatedUsuario);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    public ResponseEntity<Usuario> getUsuarioPorId(int id) {
        Optional<Usuario> temusuario = usuarioRepository.findById((long) id);
        if (temusuario.isPresent()) {
            Usuario usuario = temusuario.get();
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    public String getNomeUsuarioFromSession(HttpSession session) {
        String email = (String) session.getAttribute("username");
        if (email != null) {
            Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);
            if (usuarioOptional.isPresent()) {
                return usuarioOptional.get().getNome();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public String getRoleUsuarioFromSession(HttpSession session) {
        return (String) session.getAttribute("role");
    }
}