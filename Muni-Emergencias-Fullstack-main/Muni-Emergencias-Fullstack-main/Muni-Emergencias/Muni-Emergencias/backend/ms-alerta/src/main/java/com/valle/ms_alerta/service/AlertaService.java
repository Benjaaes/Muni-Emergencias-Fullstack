package com.valle.ms_alerta.service;

import com.valle.ms_alerta.dto.AlertaDTO;
import com.valle.ms_alerta.model.Alerta;
import com.valle.ms_alerta.repository.AlertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AlertaService {

    @Autowired
    private AlertaRepository repository;

    public Alerta emitirAlerta(AlertaDTO dto) {
        Alerta nuevaAlerta = Alerta.builder()
                .tipo(dto.getTipo())
                .mensaje(dto.getMensaje())
                .destinatario(dto.getDestinatario())
                .enviada(true)
                .build();
        return repository.save(nuevaAlerta);
    }

    public List<Alerta> listarAlertas() {
        return repository.findAll();
    }
}