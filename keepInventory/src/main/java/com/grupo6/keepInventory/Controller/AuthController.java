package com.grupo6.keepInventory.Controller;

import com.grupo6.keepInventory.Model.Usuario;
import com.grupo6.keepInventory.Repository.UsuarioRepository;
import com.grupo6.keepInventory.Service.PasswordHashingService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class AuthController {
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    PasswordHashingService passwordHashingService;
    @GetMapping("/")
    public String redirectToInventario() {
        return "redirect:/inventario";
    }
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/atualizar-senha")
    public String atualizarsenhapage() {
        return "atualizarsenha";
    }
    @PostMapping("/login")
    public String doLogin(HttpSession session, String username, String password) {
        Optional<Usuario> temusuario = usuarioRepository.findByEmail(username);
        if(temusuario.isEmpty()){
            return "redirect:/login?error=true";
        }

        Usuario usuario = temusuario.orElse(null);
        if(usuario.isAtivo() && passwordHashingService.checkPassword(password, usuario.getSenha())) {
            session.setAttribute("username", username);
            session.setAttribute("role", usuario.getRole());
            session.setAttribute("isDefaultPassword", password.equals("BioparkEdu"));

            if (password.equals("BioparkEdu")) {
                return "redirect:/atualizar-senha";
            }

            return "redirect:/inventario";
        }

        return "redirect:/login?error=true";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
