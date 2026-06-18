package com.valle.ms_usuario.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.valle.ms_usuario.dto.UsuarioDTO;
import com.valle.ms_usuario.model.Usuario;
import com.valle.ms_usuario.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService service;

    @Autowired
    private ObjectMapper objectMapper;

    private UsuarioDTO requestDTO;
    private Usuario usuarioResponse;

    @BeforeEach
    void setUp() {
        requestDTO = new UsuarioDTO();
        requestDTO.setEmail("agente@valledelsol.cl");
        requestDTO.setPassword("secreta123");

        usuarioResponse = new Usuario();
        usuarioResponse.setId(1L);
        usuarioResponse.setEmail("agente@valledelsol.cl");
    }

    @Test
    void register_DevuelveStatus200_SiEsExitoso() throws Exception {
        when(service.registrar(any(UsuarioDTO.class))).thenReturn(usuarioResponse);

        // Ojo: asegúrate de que la ruta coincida con el RequestMapping de tu Controlador
        mockMvc.perform(post("/api/usuarios/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("agente@valledelsol.cl"));
    }

    @Test
    void login_DevuelveStatus200_SiCredencialesSonCorrectas() throws Exception {
        when(service.login("agente@valledelsol.cl", "secreta123")).thenReturn(usuarioResponse);

        mockMvc.perform(post("/api/usuarios/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("agente@valledelsol.cl"));
    }

    @Test
    void login_DevuelveError_SiCredencialesSonInvalidas() throws Exception {
        when(service.login(anyString(), anyString())).thenThrow(new RuntimeException("Credenciales incorrectas"));

        mockMvc.perform(post("/api/usuarios/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                // Acá usamos isUnauthorized (401) o isBadRequest (400) según lo que devuelva tu ExceptionHandler. 
                // Asumiendo que no tienes un handler global, Spring por defecto mapea RuntimeException a un error de servidor o al status que configuraste.
                // Ajusta isUnauthorized() o isBadRequest() dependiendo de la implementación exacta de tu ResponseEntity en el Controller.
                .andExpect(status().is4xxClientError()); 
    }
}
