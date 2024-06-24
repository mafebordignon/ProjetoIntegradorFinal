package com.grupo6.keepInventory.Controller;

import com.grupo6.keepInventory.Model.Usuario;
import com.grupo6.keepInventory.Repository.UsuarioRepository;
import com.grupo6.keepInventory.Service.PasswordHashingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HtmlController {
    @GetMapping("campos-ativos")
    public String campoAtivos() {
        return "campoativo";
    }
    @GetMapping("/access-denied")
    public String acessDenied() {
        return "acessonegado";
    }
    @GetMapping("/admin/usuarios")
    public String usuarios() {
        return "usuarios";
    }
    @GetMapping("/cadastro-item")
    public String register() {
        return "register";
    }

    @GetMapping("/inventario")
    public String infoGeral() {
        return "infogeral";
    }
    @GetMapping("/especificacao-item/{id}")
    public String especificacaoItem(@PathVariable Long id, Model model) {
        return "especificacaoItem";
    }

    @GetMapping("/devolucao/{id}")
    public String devolucao(@PathVariable long id, Model model) {
        return "devolucao";
    }

    @GetMapping("/emprestimo/{id}")
    public String emprestimo(@PathVariable long id, Model model) {
        return "emprestimo";
    }
    @GetMapping("/manutencao/{id}")
    public String manutencao(@PathVariable long id, Model model) {
        return "manutencao";
    }

}

