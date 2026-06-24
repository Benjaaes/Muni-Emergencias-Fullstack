package com.valle.ms_alerta.model;

import com.valle.ms_alerta.dto.AlertaDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ModelCoverageTest {

    @Test
    public void testAlertaModel() {
        Alerta alerta = new Alerta();
        alerta.setId(1L);
        alerta.setTipo("FUEGO");
        alerta.setMensaje("Fuego en el bosque");
        alerta.setDestinatario("Bomberos");
        alerta.setEnviada(true);

        assertEquals(1L, alerta.getId());
        assertEquals("FUEGO", alerta.getTipo());
        assertEquals("Fuego en el bosque", alerta.getMensaje());
        assertEquals("Bomberos", alerta.getDestinatario());
        assertTrue(alerta.isEnviada());

        Alerta alerta2 = new Alerta("SISMO", "Temblor leve", "Defensa Civil", false);
        assertEquals("SISMO", alerta2.getTipo());
        assertEquals("Temblor leve", alerta2.getMensaje());
        assertEquals("Defensa Civil", alerta2.getDestinatario());
        assertFalse(alerta2.isEnviada());
    }

    @Test
    public void testAlertaDTO() {
        AlertaDTO dto = new AlertaDTO();
        dto.setTipo("TSUNAMI");
        dto.setMensaje("Ola gigante");
        dto.setDestinatario("Marina");

        assertEquals("TSUNAMI", dto.getTipo());
        assertEquals("Ola gigante", dto.getMensaje());
        assertEquals("Marina", dto.getDestinatario());

        AlertaDTO dto2 = new AlertaDTO("INUNDACION", "Mucha agua", "Todos");
        assertEquals("INUNDACION", dto2.getTipo());
        assertEquals("Mucha agua", dto2.getMensaje());
        assertEquals("Todos", dto2.getDestinatario());
    }
}
