package com.valle.ms_usuario.controller;

import com.valle.ms_usuario.dto.UsuarioDTO;
import com.valle.ms_usuario.model.Usuario;
import com.valle.ms_usuario.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @PostMapping("/registrar")
    public Usuario crear(@RequestBody UsuarioDTO dto) {
        return service.registrar(dto);
    }

    @GetMapping("/listar")
    public List<Usuario> listar() {
        return service.obtenerTodos();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsuarioDTO credenciales) {
        try {
            Usuario usuario = service.login(credenciales.getEmail(), credenciales.getPassword());
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body("{\"mensaje\": \"Credenciales incorrectas\"}");
        }
    }
}