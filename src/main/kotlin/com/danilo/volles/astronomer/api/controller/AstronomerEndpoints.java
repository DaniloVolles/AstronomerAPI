package com.danilo.volles.astronomer.api.controller;

import com.danilo.volles.astronomer.api.dto.response.AstronomerResponseDTO;
import com.danilo.volles.astronomer.api.dto.request.AstronomerNameDTO;
import com.danilo.volles.astronomer.api.dto.request.AstronomerRequestDTO;
import com.danilo.volles.astronomer.api.dto.request.CityRequestDTO;
import com.danilo.volles.astronomer.api.dto.response.CelestialObjectResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public interface AstronomerEndpoints {
    ResponseEntity<AstronomerResponseDTO> saveAstronomer(AstronomerRequestDTO astronomerDTO);
    ResponseEntity<AstronomerResponseDTO> getAllAstronomers();
    ResponseEntity<AstronomerResponseDTO> getAstronomerById(UUID id);
    ResponseEntity<AstronomerResponseDTO> getAstronomerByCity(CityRequestDTO city);
    ResponseEntity<CelestialObjectResponseDTO> getDiscoveriesByAstronomerName(AstronomerNameDTO astronomer);
    ResponseEntity<AstronomerResponseDTO> updateAstronomeById(UUID id);
    ResponseEntity<AstronomerResponseDTO> inactivateAstronomerById(UUID id);
}
