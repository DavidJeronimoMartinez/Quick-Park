package com.example.seguridad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.seguridad.entity.RegistroPersonal;

@Repository
public interface RegistroPersonalRepository extends JpaRepository<RegistroPersonal, String> {

    boolean existsByContrasena(String contrasena);
}