package com.valle.ms_usuario.controller;

import com.valle.ms_usuario.dto.UsuarioDTO;
import com.valle.ms_usuario.model.Usuario;
import com.valle.ms_usuario.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin(origins = "*")
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
        // Imprimimos en la consola de Java para ver que sí llegó la petición
        System.out.println("Intento de login con correo: " + credenciales.getEmail());

        // Aquí deberías llamar a tu UsuarioService para validar si el correo y
        // contraseña son correctos.
        // Ejemplo ficticio: return service.login(credenciales.getEmail(),
        // credenciales.getPassword());

        // Por ahora, devolvemos un OK genérico para que veas que la conexión ya
        // funciona y React avance.
        return ResponseEntity.ok().body("{\"mensaje\": \"Conexión exitosa al endpoint de login\"}");
    }
}
