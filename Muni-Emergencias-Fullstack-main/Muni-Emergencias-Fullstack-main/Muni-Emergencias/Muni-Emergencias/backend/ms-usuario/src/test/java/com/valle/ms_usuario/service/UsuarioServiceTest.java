package com.valle.ms_usuario.service;

import com.valle.ms_usuario.dto.UsuarioDTO;
import com.valle.ms_usuario.model.Usuario;
import com.valle.ms_usuario.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository repository;

    @InjectMocks
    private UsuarioService service;

    private UsuarioDTO dto;
    private Usuario usuarioRegistrado;

    @BeforeEach
    void setUp() {
        // Datos simulados para la petición de registro
        dto = new UsuarioDTO();
        dto.setNombre("Agente Prueba");
        dto.setEmail("agente@valledelsol.cl");
        dto.setPassword("secreta123");

        // Datos simulados devueltos por la base de datos
        usuarioRegistrado = new Usuario();
        usuarioRegistrado.setId(1L);
        usuarioRegistrado.setNombre("Agente Prueba");
        usuarioRegistrado.setEmail("agente@valledelsol.cl");
        
        // Simulamos que la clave en BD ya está encriptada
        String hashReal = BCrypt.hashpw("secreta123", BCrypt.gensalt());
        usuarioRegistrado.setPassword(hashReal);
    }

    @Test
    void registrar_CaminoFeliz_UsuarioGuardadoConExito() {
        when(repository.save(any(Usuario.class))).thenAnswer(i -> {
            Usuario u = i.getArgument(0);
            u.setId(100L); // Simulamos el ID auto-incremental generado
            return u;
        });

        Usuario resultado = service.registrar(dto);

        assertNotNull(resultado);
        assertEquals(100L, resultado.getId());
        assertEquals("agente@valledelsol.cl", resultado.getEmail());
        
        // Verificamos que se haya invocado al repositorio exactamente una vez
        verify(repository, times(1)).save(any(Usuario.class));
    }

    @Test
    void login_CaminoFeliz_CredencialesCorrectas() {
        // Asumiendo que adaptaste el método del repositorio a findByEmail(email) 
        // para poder desencriptar y comparar
        when(repository.findByEmail("agente@valledelsol.cl")).thenReturn(Optional.of(usuarioRegistrado));

        Usuario resultado = service.login("agente@valledelsol.cl", "secreta123");

        assertNotNull(resultado);
        assertEquals("agente@valledelsol.cl", resultado.getEmail());
    }

    @Test
    void login_Excepcion_UsuarioNoEncontrado() {
        when(repository.findByEmail("falso@valledelsol.cl")).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            service.login("falso@valledelsol.cl", "secreta123");
        });

        // Validamos el mensaje de la excepción lanzada
        assertTrue(exception.getMessage().contains("Credenciales incorrectas") || 
                   exception.getMessage().contains("no encontrado"));
    }

    @Test
    void login_Excepcion_ContrasenaIncorrecta() {
        when(repository.findByEmail("agente@valledelsol.cl")).thenReturn(Optional.of(usuarioRegistrado));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            service.login("agente@valledelsol.cl", "claveFalsa123");
        });

        assertTrue(exception.getMessage().contains("Credenciales incorrectas"));
    }
}
