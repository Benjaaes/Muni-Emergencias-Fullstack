package com.valle.ms_usuario.model;

import com.valle.ms_usuario.dto.UsuarioDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ModelCoverageTest {

    @Test
    public void testUsuarioModel() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Juan");
        usuario.setRut("12345678-9");
        usuario.setRol("ADMIN");
        usuario.setEmail("juan@mail.com");
        usuario.setPassword("12345");

        assertEquals(1L, usuario.getId());
        assertEquals("Juan", usuario.getNombre());
        assertEquals("12345678-9", usuario.getRut());
        assertEquals("ADMIN", usuario.getRol());
        assertEquals("juan@mail.com", usuario.getEmail());
        assertEquals("12345", usuario.getPassword());

        Usuario u2 = new Usuario("Pedro", "111-1", "USER", "p@mail.com", "pass");
        assertEquals("Pedro", u2.getNombre());
        assertEquals("111-1", u2.getRut());
        assertEquals("USER", u2.getRol());
        assertEquals("p@mail.com", u2.getEmail());
        assertEquals("pass", u2.getPassword());
    }

    @Test
    public void testUsuarioDTO() {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setNombre("Ana");
        dto.setRut("222-2");
        dto.setRol("USER");
        dto.setEmail("ana@mail.com");
        dto.setPassword("pwd");

        assertEquals("Ana", dto.getNombre());
        assertEquals("222-2", dto.getRut());
        assertEquals("USER", dto.getRol());
        assertEquals("ana@mail.com", dto.getEmail());
        assertEquals("pwd", dto.getPassword());

        UsuarioDTO dto2 = new UsuarioDTO("Luz", "333", "ADMIN", "l@l", "1");
        assertEquals("Luz", dto2.getNombre());
        assertEquals("333", dto2.getRut());
    }
}
