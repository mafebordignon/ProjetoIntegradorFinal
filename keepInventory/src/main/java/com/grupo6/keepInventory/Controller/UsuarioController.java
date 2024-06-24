package com.grupo6.keepInventory.Controller;

import java.util.List;
import java.util.Optional;

import com.grupo6.keepInventory.Model.Usuario;
import com.grupo6.keepInventory.Repository.UsuarioRepository;
import com.grupo6.keepInventory.Service.PasswordHashingService;
import com.grupo6.keepInventory.Service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ola")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/api/usuario/nome")
    public ResponseEntity<String> getNomeUsuario(HttpSession session) {
        String nomeUsuario = usuarioService.getNomeUsuarioFromSession(session);
        if (nomeUsuario != null) {
            return ResponseEntity.ok(nomeUsuario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/api/usuario/role")
    public ResponseEntity<String> getRoleUsuario(HttpSession session) {
        String role = usuarioService.getRoleUsuarioFromSession(session);
        if (role != null) {
            return ResponseEntity.ok(role);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

