package com.example.seguridad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.seguridad.entity.Moto;

public interface MotoRepository extends JpaRepository<Moto, String> {
    boolean existsByPlacaMoto(String placaMoto);
}