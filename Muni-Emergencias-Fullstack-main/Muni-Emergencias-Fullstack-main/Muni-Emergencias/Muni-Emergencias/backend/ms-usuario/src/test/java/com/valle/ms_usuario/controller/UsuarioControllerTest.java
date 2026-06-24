package com.valle.ms_usuario.controller;

import com.valle.ms_usuario.dto.UsuarioDTO;
import com.valle.ms_usuario.model.Usuario;
import com.valle.ms_usuario.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias del UsuarioController (ms-usuario).
 *
 * Reglas de negocio cubiertas:
 *  - POST /api/usuarios/registrar: devuelve 201 con el nuevo usuario, o 400 si el email ya existe / datos inválidos.
 *  - GET  /api/usuarios/listar:    devuelve lista completa de usuarios del municipio.
 *  - POST /api/usuarios/login:     devuelve 200 con el usuario autenticado, o 401 si las credenciales son incorrectas.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("UsuarioController - Pruebas de capa web")
public class UsuarioControllerTest {

    @Mock
    private UsuarioService service;

    @InjectMocks
    private UsuarioController controller;

    private UsuarioDTO requestDTO;
    private Usuario usuarioResponse;

    @BeforeEach
    void setUp() {
        requestDTO = new UsuarioDTO();
        requestDTO.setNombre("Agente Rodríguez");
        requestDTO.setRut("12345678-9");
        requestDTO.setEmail("agente@valledelsol.cl");
        requestDTO.setPassword("secreta123");

        usuarioResponse = new Usuario();
        usuarioResponse.setId(1L);
        usuarioResponse.setNombre("Agente Rodríguez");
        usuarioResponse.setRut("12345678-9");
        usuarioResponse.setRol("USER");
        usuarioResponse.setEmail("agente@valledelsol.cl");
        usuarioResponse.setPassword("$2a$hash_bcrypt");
    }

    // ==================== POST /api/usuarios/registrar ====================

    @Test
    @DisplayName("registrar: Retorna 201 cuando el agente es creado exitosamente")
    void registrar_DevuelveStatus201_CuandoExitoso() {
        when(service.registrar(any(UsuarioDTO.class))).thenReturn(usuarioResponse);

        ResponseEntity<?> respuesta = controller.crear(requestDTO);

        assertEquals(HttpStatus.CREATED, respuesta.getStatusCode());
        assertNotNull(respuesta.getBody());
        assertTrue(respuesta.getBody() instanceof Usuario);
        Usuario cuerpo = (Usuario) respuesta.getBody();
        assertEquals("agente@valledelsol.cl", cuerpo.getEmail());
        assertEquals("Agente Rodríguez", cuerpo.getNombre());
        verify(service, times(1)).registrar(any(UsuarioDTO.class));
    }

    @Test
    @DisplayName("registrar: Retorna 400 cuando el email ya está registrado en el municipio")
    void registrar_DevuelveStatus400_CuandoEmailDuplicado() {
        when(service.registrar(any(UsuarioDTO.class)))
                .thenThrow(new RuntimeException("El correo ya está registrado"));

        ResponseEntity<?> respuesta = controller.crear(requestDTO);

        assertEquals(HttpStatus.BAD_REQUEST, respuesta.getStatusCode());
        assertNotNull(respuesta.getBody());
    }

    @Test
    @DisplayName("registrar: Retorna 400 cuando faltan datos obligatorios (email o contraseña)")
    void registrar_DevuelveStatus400_CuandoDatosObligatoriosFaltantes() {
        when(service.registrar(any(UsuarioDTO.class)))
                .thenThrow(new RuntimeException("Email y contraseña son obligatorios"));

        ResponseEntity<?> respuesta = controller.crear(requestDTO);

        assertEquals(HttpStatus.BAD_REQUEST, respuesta.getStatusCode());
    }

    // ==================== GET /api/usuarios/listar ====================

    @Test
    @DisplayName("listar: Retorna todos los agentes registrados del municipio")
    void listar_DevuelveTodosLosAgentes() {
        Usuario u2 = new Usuario("Supervisor González", "11-1", "ADMIN", "sup@valledelsol.cl", "hash2");
        when(service.obtenerTodos()).thenReturn(Arrays.asList(usuarioResponse, u2));

        List<Usuario> resultado = controller.listar();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("agente@valledelsol.cl", resultado.get(0).getEmail());
        assertEquals("ADMIN", resultado.get(1).getRol());
        verify(service, times(1)).obtenerTodos();
    }

    // ==================== POST /api/usuarios/login ====================

    @Test
    @DisplayName("login: Retorna 200 con el usuario cuando las credenciales son correctas")
    void login_DevuelveStatus200_CuandoCredencialesCorrectas() {
        when(service.login("agente@valledelsol.cl", "secreta123")).thenReturn(usuarioResponse);

        ResponseEntity<?> respuesta = controller.login(requestDTO);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertNotNull(respuesta.getBody());
        assertTrue(respuesta.getBody() instanceof Usuario);
        assertEquals("agente@valledelsol.cl", ((Usuario) respuesta.getBody()).getEmail());
    }

    @Test
    @DisplayName("login: Retorna 401 cuando la contraseña es incorrecta")
    void login_DevuelveStatus401_CuandoContrasenaIncorrecta() {
        when(service.login(anyString(), anyString()))
                .thenThrow(new RuntimeException("Credenciales incorrectas"));

        ResponseEntity<?> respuesta = controller.login(requestDTO);

        assertEquals(HttpStatus.UNAUTHORIZED, respuesta.getStatusCode());
    }

    @Test
    @DisplayName("login: Retorna 401 cuando el agente no está registrado en el sistema")
    void login_DevuelveStatus401_CuandoAgenteNoExiste() {
        requestDTO.setEmail("fantasma@valledelsol.cl");
        when(service.login("fantasma@valledelsol.cl", "secreta123"))
                .thenThrow(new RuntimeException("Credenciales incorrectas"));

        ResponseEntity<?> respuesta = controller.login(requestDTO);

        assertEquals(HttpStatus.UNAUTHORIZED, respuesta.getStatusCode());
    }
}
