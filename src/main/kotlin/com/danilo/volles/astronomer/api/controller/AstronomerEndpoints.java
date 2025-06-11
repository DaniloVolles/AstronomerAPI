package com.danilo.volles.astronomer.api.controller;

import com.danilo.volles.astronomer.api.dto.request.AstronomerNameDTO;
import com.danilo.volles.astronomer.api.dto.request.AstronomerRequestDTO;
import com.danilo.volles.astronomer.api.dto.request.DiscoveryAssignmentRequestDTO;
import com.danilo.volles.astronomer.api.dto.response.ApiResponse;
import com.danilo.volles.astronomer.api.dto.response.AstronomerResponseDTO;
import com.danilo.volles.astronomer.api.dto.response.CelestialObjectResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface AstronomerEndpoints {
    ResponseEntity<ApiResponse<AstronomerResponseDTO>> saveAstronomer(AstronomerRequestDTO astronomerDTO);
    ResponseEntity<ApiResponse<List<AstronomerResponseDTO>>> getAllAstronomers();
    ResponseEntity<ApiResponse<AstronomerResponseDTO>> getAstronomerById(UUID id);
    ResponseEntity<ApiResponse<List<AstronomerResponseDTO>>> getAstronomersByCity(String city);
    ResponseEntity<ApiResponse<List<CelestialObjectResponseDTO>>> assignDiscoveriesToAstronomer(UUID id, DiscoveryAssignmentRequestDTO attibuteRequestDTO);
    ResponseEntity<ApiResponse<List<CelestialObjectResponseDTO>>> getDiscoveriesByAstronomerName(AstronomerNameDTO astronomer);
    ResponseEntity<ApiResponse<AstronomerResponseDTO>> updateAstronomerById(UUID id, AstronomerRequestDTO requestDTO);
    ResponseEntity<ApiResponse<AstronomerResponseDTO>> deactivateAstronomerById(UUID id);
}
