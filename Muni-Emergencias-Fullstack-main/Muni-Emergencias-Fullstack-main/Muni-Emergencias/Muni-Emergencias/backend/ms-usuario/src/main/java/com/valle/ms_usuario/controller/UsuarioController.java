package com.valle.ms_usuario.controller;

import com.valle.ms_usuario.dto.UsuarioDTO;
import com.valle.ms_usuario.model.Usuario;
import com.valle.ms_usuario.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @PostMapping("/registrar")
    public ResponseEntity<?> crear(@RequestBody UsuarioDTO dto) {
        try {
            Usuario nuevoUsuario = service.registrar(dto);
            return ResponseEntity.status(201).body(nuevoUsuario);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("mensaje", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
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
            Map<String, String> respuestaError = new HashMap<>();
            respuestaError.put("mensaje", "Credenciales incorrectas o agente no registrado");
            return ResponseEntity.status(401).body(respuestaError);
        }
    }
}