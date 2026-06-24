package com.valle.ms_usuario.service;

import com.valle.ms_usuario.dto.UsuarioDTO;
import com.valle.ms_usuario.model.Usuario;
import com.valle.ms_usuario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.mindrot.jbcrypt.BCrypt;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    public Usuario registrar(UsuarioDTO dto) {
        if (dto.getEmail() == null || dto.getPassword() == null) {
            throw new RuntimeException("Email y contraseña son obligatorios");
        }

        String email = dto.getEmail().trim();
        if (repository.existsByEmail(email)) {
            throw new RuntimeException("El correo ya está registrado");
        }

        String hashPassword = BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt());

        Usuario nuevo = new Usuario(
                dto.getNombre() != null && !dto.getNombre().trim().isEmpty() ? dto.getNombre().trim() : "Usuario Nuevo",
                dto.getRut() != null && !dto.getRut().trim().isEmpty() ? dto.getRut().trim() : "Sin RUT",
                "USER", // Rol por defecto
                email,
                hashPassword
        );

        return repository.save(nuevo);
    }

    public List<Usuario> obtenerTodos() {
        return repository.findAll();
    }

    public Usuario login(String email, String password) {
        if (email == null || password == null) {
            throw new RuntimeException("Credenciales incorrectas");
        }

        Usuario usuario = repository.findByEmail(email.trim())
                .orElseThrow(() -> new RuntimeException("Credenciales incorrectas"));

        // Única validación: ¿La contraseña coincide usando BCrypt?
        if (!BCrypt.checkpw(password, usuario.getPassword())) {
            throw new RuntimeException("Credenciales incorrectas");
        }

        // Retornamos el usuario (sin restricciones estrictas de rol o dominio)
        return usuario;
    }
}