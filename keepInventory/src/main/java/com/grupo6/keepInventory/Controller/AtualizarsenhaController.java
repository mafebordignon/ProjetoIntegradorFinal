package com.grupo6.keepInventory.Controller;

import com.grupo6.keepInventory.Model.Usuario;
import com.grupo6.keepInventory.Repository.UsuarioRepository;
import com.grupo6.keepInventory.Service.PasswordHashingService;
import com.grupo6.keepInventory.Service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/senha")
public class AtualizarsenhaController {
    private UsuarioRepository usuarioRepository;
    private UsuarioService usuarioService;

    @Autowired
    public AtualizarsenhaController(UsuarioService usuarioService, UsuarioRepository usuarioRepository) {
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
    }


    @PostMapping
    public ResponseEntity<?> atualizarSenha(HttpSession session, @RequestBody SenhaDTO senhaDTO) {
        String newPassword = senhaDTO.senha();
        System.out.println(newPassword);

        System.out.println(session);
        if (newPassword.equals("BioparkEdu")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Senha é padrão");
        }

        String email = (String) session.getAttribute("username");

        if (email != null && !email.isEmpty()) {
            ResponseEntity<?> response = usuarioService.updatePassword(email, newPassword);
            if (response.getStatusCode() == HttpStatus.OK) {
                session.invalidate();
            }
            return response;
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request");
    }



}