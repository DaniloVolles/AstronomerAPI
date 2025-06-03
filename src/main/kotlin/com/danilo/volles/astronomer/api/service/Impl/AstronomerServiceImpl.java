package com.danilo.volles.astronomer.api.service.Impl;

import com.danilo.volles.astronomer.api.dto.request.AstronomerRequestDTO;
import com.danilo.volles.astronomer.api.dto.response.AstronomerResponseDTO;
import com.danilo.volles.astronomer.api.dto.response.CelestialObjectResponseDTO;
import com.danilo.volles.astronomer.api.service.AstronomerService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AstronomerServiceImpl implements AstronomerService {

    @Override
    public AstronomerResponseDTO saveAstronomer(AstronomerRequestDTO requestDTO) {
        return null;
    }

    @Override
    public List<AstronomerResponseDTO> getAstronomers() {
        return List.of();
    }

    @Override
    public AstronomerResponseDTO getAstronomerById(String id) {
        return null;
    }

    @Override
    public AstronomerResponseDTO getAstronomerByCity(String City) {
        return null;
    }

    @Override
    public CelestialObjectResponseDTO getDiscoveriesByAstronomerName(String AstronomerName) {
        return null;
    }

    @Override
    public AstronomerResponseDTO updateAstronomerById(String name) {
        return null;
    }

    @Override
    public AstronomerResponseDTO inactivateAstronomerById(String id) {
        return null;
    }
}
