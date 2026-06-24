package com.valle.ms_reporte.model;

import com.valle.ms_reporte.dto.IncendioDTO;
import org.junit.jupiter.api.Test;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class ModelCoverageTest {

    @Test
    public void testIncendioModel() {
        Incendio incendio = new Incendio();
        incendio.setId(1L);
        incendio.setEstado("ACTIVO");
        incendio.setDescripcion("Fuego grande");
        incendio.setSector("Norte");
        incendio.setNivelGravedad("ALTA");

        assertEquals(1L, incendio.getId());
        assertEquals("ACTIVO", incendio.getEstado());
        assertEquals("Fuego grande", incendio.getDescripcion());
        assertEquals("Norte", incendio.getSector());
        assertEquals("ALTA", incendio.getNivelGravedad());

        Incendio incendio2 = new Incendio("Humo", "Sur", "MEDIA", "CONTROLADO");
        assertEquals("CONTROLADO", incendio2.getEstado());
        assertEquals("Humo", incendio2.getDescripcion());
    }

    @Test
    public void testIncendioDTO() {
        IncendioDTO dto = new IncendioDTO();
        dto.setDescripcion("Fuego");
        dto.setSector("Este");
        dto.setNivelGravedad("BAJA");

        assertEquals("Fuego", dto.getDescripcion());
        assertEquals("Este", dto.getSector());
        assertEquals("BAJA", dto.getNivelGravedad());

        IncendioDTO dto2 = new IncendioDTO("Incendio", "Oeste", "ALTA");
        assertEquals("Incendio", dto2.getDescripcion());
        assertEquals("Oeste", dto2.getSector());
    }
}
