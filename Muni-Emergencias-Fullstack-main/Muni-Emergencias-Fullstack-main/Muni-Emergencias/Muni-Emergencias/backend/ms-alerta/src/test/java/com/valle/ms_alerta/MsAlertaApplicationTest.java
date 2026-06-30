package com.valle.ms_alerta;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class MsAlertaApplicationTest {

    @Test
    @DisplayName("MsAlertaApplication: La clase principal puede ser instanciada")
    void applicationClass_PuedeSerInstanciada() {
        assertDoesNotThrow(() -> new MsAlertaApplication());
    }

    @Test
    @DisplayName("MsAlertaApplication: El método main se ejecuta correctamente")
    void application_MainRuns() {
        try (MockedStatic<SpringApplication> mocked = Mockito.mockStatic(SpringApplication.class)) {
            mocked.when(() -> SpringApplication.run(MsAlertaApplication.class, new String[]{}))
                  .thenReturn(null);
            
            assertDoesNotThrow(() -> MsAlertaApplication.main(new String[]{}));
            
            mocked.verify(() -> SpringApplication.run(MsAlertaApplication.class, new String[]{}));
        }
    }
}
