package com.valle.ms_usuario.service;

import com.valle.ms_usuario.dto.UsuarioDTO;
import com.valle.ms_usuario.model.Usuario;
import com.valle.ms_usuario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    public Usuario registrar(UsuarioDTO dto) {
        Usuario nuevo = Usuario.builder()
                .nombre(dto.getNombre())
                .rut(dto.getRut())
                .rol(dto.getRol())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();
        return repository.save(nuevo);
    }

    public List<Usuario> obtenerTodos() {
        return repository.findAll();
    }

    public Usuario login(String email, String password) {
        return repository.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new RuntimeException("Credenciales incorrectas"));
    }
}