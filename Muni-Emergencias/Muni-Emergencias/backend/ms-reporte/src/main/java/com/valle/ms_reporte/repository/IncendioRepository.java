package com.valle.ms_reporte.repository;

import com.valle.ms_reporte.model.Incendio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncendioRepository extends JpaRepository<Incendio, Long> {
}