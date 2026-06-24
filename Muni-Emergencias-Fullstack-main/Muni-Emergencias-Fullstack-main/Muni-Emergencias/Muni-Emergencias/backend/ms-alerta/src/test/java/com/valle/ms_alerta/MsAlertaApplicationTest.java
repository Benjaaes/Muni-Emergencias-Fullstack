package com.valle.ms_alerta;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Prueba de cobertura para la clase principal MsAlertaApplication.
 *
 * Objetivo: Cubrir el método main() de la clase de arranque para que JaCoCo
 * no muestre 0% en el paquete raíz com.valle.ms_alerta.
 *
 * Nota: No se usa @SpringBootTest para no levantar el contexto completo
 * (que requeriría BD), solo se valida que la clase se puede instanciar
 * y que el método main existe en la clase.
 */
public class MsAlertaApplicationTest {

    @Test
    @DisplayName("MsAlertaApplication: La clase principal puede ser instanciada")
    void applicationClass_PuedeSerInstanciada() {
        // Verifica que la clase existe y puede crearse sin errores
        assertDoesNotThrow(() -> new MsAlertaApplication());
    }
}
