package com.example.seguridad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.seguridad.entity.Vehiculo;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, String> {
    boolean existsByPlaca(String placa);

    Vehiculo findByMatricula(String matricula);


}
