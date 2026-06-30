package com.valle.ms_alerta.service;

import com.valle.ms_alerta.dto.AlertaDTO;
import com.valle.ms_alerta.dto.ReporteDTO;
import com.valle.ms_alerta.model.Alerta;
import com.valle.ms_alerta.repository.AlertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

@Service
public class AlertaService {

    @Autowired
    private AlertaRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    public Alerta emitirAlerta(AlertaDTO dto) {
        Alerta nuevaAlerta = new Alerta(dto.getTipo(), dto.getMensaje(), dto.getDestinatario(), true);
        return repository.save(nuevaAlerta);
    }

    public List<Alerta> listarAlertas() {
        List<Alerta> alertas = new ArrayList<>(repository.findAll());
        
        try {
            // Fetch reports from ms-reporte
            ReporteDTO[] reportes = restTemplate.getForObject("http://ms-reporte:8092/api/reportes", ReporteDTO[].class);
            if (reportes != null) {
                for (ReporteDTO rep : reportes) {
                    Alerta alertaFromReporte = new Alerta();
                    alertaFromReporte.setId(rep.getId() + 10000); // offset id to avoid conflicts in frontend map keys if needed
                    alertaFromReporte.setTipo(rep.getTipoEmergencia());
                    alertaFromReporte.setMensaje(rep.getDescripcion());
                    alertaFromReporte.setDestinatario(rep.getEstado());
                    alertaFromReporte.setEnviada(true);
                    alertas.add(alertaFromReporte);
                }
            }
        } catch (Exception e) {
            System.err.println("Error fetching reports from ms-reporte: " + e.getMessage());
        }
        
        return alertas;
    }
}