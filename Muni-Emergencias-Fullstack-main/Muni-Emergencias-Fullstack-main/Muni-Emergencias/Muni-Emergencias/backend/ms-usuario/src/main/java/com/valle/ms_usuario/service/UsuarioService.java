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
        if (repository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("El correo ya está registrado");
        }
        String hashPassword = BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt());

        Usuario nuevo = Usuario.builder()
                .nombre(dto.getNombre() != null && !dto.getNombre().isEmpty() ? dto.getNombre() : "Operador Nuevo")
                .rut(dto.getRut() != null && !dto.getRut().isEmpty() ? dto.getRut() : "Sin RUT")
                .rol(dto.getRol() != null && !dto.getRol().isEmpty() ? dto.getRol() : "OPERADOR")
                .email(dto.getEmail())
                .password(hashPassword)
                .build();
        return repository.save(nuevo);
    }

    public List<Usuario> obtenerTodos() {
        return repository.findAll();
    }

    public Usuario login(String email, String password) {
        Usuario usuario = repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Credenciales incorrectas"));
                
        if (!BCrypt.checkpw(password, usuario.getPassword())) {
            throw new RuntimeException("Credenciales incorrectas");
        }
        return usuario;
    }
}