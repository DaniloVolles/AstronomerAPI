package com.danilo.volles.astronomer.api.service;

import com.danilo.volles.astronomer.api.dto.request.AstronomerRequestDTO;
import com.danilo.volles.astronomer.api.dto.response.AstronomerResponseDTO;
import com.danilo.volles.astronomer.api.dto.response.CelestialObjectResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface AstronomerService {
    AstronomerResponseDTO saveAstronomer(AstronomerRequestDTO requestDTO);
    List<AstronomerResponseDTO> getAstronomers();
    AstronomerResponseDTO getAstronomerById(UUID id);
    List<AstronomerResponseDTO> getAstronomerByCity(String city);
    CelestialObjectResponseDTO getDiscoveriesByAstronomerName(String AstronomerName);
    AstronomerResponseDTO updateAstronomerById(UUID id, AstronomerRequestDTO requestDTO);
    AstronomerResponseDTO deactivateAstronomerById(UUID id);
}
