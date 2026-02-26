package com.example.seguridad.service;

import com.example.seguridad.entity.Moto;
import com.example.seguridad.repository.MotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MotoService {

    @Autowired
    private MotoRepository motoRepo;

    public String guardar(Moto moto) {
        if (motoRepo.existsByPlacaMoto(moto.getPlacaMoto())) {
            return "Error: Ya existe una moto con esa placa";
        }
        motoRepo.save(moto);
        return "Moto guardada correctamente";
    }

    public boolean existePorPlaca(String placa) {
        return motoRepo.existsByPlacaMoto(placa);
    }
}