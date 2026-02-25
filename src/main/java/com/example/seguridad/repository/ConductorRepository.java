package com.example.seguridad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.seguridad.entity.Conductor;

@Repository
public interface ConductorRepository extends JpaRepository<Conductor, String> {
    boolean existsByMatricula(String matricula);
}
