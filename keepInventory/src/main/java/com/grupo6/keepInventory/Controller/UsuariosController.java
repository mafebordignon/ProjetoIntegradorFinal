package com.grupo6.keepInventory.Controller;

import com.grupo6.keepInventory.Model.Usuario;
import com.grupo6.keepInventory.Service.PasswordHashingService;
import com.grupo6.keepInventory.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
@RestController
@RequestMapping("admin/api/usuarios")
public class UsuariosController {
    private UsuarioService usuarioService;
    private PasswordHashingService passwordHashingService;
    @Autowired
    public UsuariosController(UsuarioService usuarioService, PasswordHashingService passwordHashingService) {
        this.usuarioService = usuarioService;
        this.passwordHashingService = passwordHashingService;
    }

    @GetMapping
    public Page<Usuario> getUsuariosPaginados(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nome") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        return usuarioService.getUsuariosPaginados(page, size, sortBy, sortDir);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioPorId(@PathVariable int id){
        return usuarioService.getUsuarioPorId(id);
    }

    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody Usuario usuario) {
        return usuarioService.adicionarUsuario(usuario);
    }

    @DeleteMapping("/{id}")
    public void deletarUsuario(@PathVariable Long id) {
    }

    @PutMapping("/{id}/toggleAtivo")
    public ResponseEntity<?> toggleAtivo(@PathVariable long id) {
        return usuarioService.toggleAtivo(id);
    }
    @PutMapping("/{id}/senhaReset")
    public ResponseEntity<?> senhaReset(@PathVariable long id) {
        return usuarioService.senhaReset(id);
    }

    @PutMapping("/{id}/alterarUsuario")
    public ResponseEntity<?> alterarUsuario(@PathVariable long id, @RequestBody Usuario usuario) {
        return usuarioService.alterarUsuario(id, usuario);
    }
}
