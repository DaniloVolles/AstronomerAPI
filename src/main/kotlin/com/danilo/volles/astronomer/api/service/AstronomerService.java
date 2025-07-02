package com.danilo.volles.astronomer.api.service;

import com.danilo.volles.astronomer.api.dto.request.AstronomerRequestDTO;
import com.danilo.volles.astronomer.api.dto.request.DiscoveryAssignmentRequestDTO;
import com.danilo.volles.astronomer.api.dto.response.AstronomerResponseDTO;
import com.danilo.volles.astronomer.api.dto.response.CelestialObjectResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Service interface responsible for managing astronomers and their celestial discoveries.
 * <p>
 * This service handles all operations related to the astronomer domain, including:
 * creation, retrieval, update, deactivation, and the assignment of celestial object discoveries.
 * </p>
 *
 * <p>
 * It interacts with data repositories and external clients to ensure consistent
 * persistence and integration with celestial object data providers.
 * </p>
 *
 * <p>
 * Typical use cases include:
 * <ul>
 *     <li>Registering new astronomers</li>
 *     <li>Fetching astronomers by ID or city</li>
 *     <li>Assigning celestial discoveries</li>
 *     <li>Managing astronomer status (e.g., deactivation)</li>
 * </ul>
 * </p>
 */
@Service
public interface AstronomerService {

    /**
     * Saves a new astronomer based on the provided data.
     * @param requestDTO the data for the astronomer to be saved
     * @return the saved astronomer information
     */
    AstronomerResponseDTO saveAstronomer(AstronomerRequestDTO requestDTO);

    /**
     * Retrieves a list of all astronomers.
     * @return a list of all registered astronomers
     */
    List<AstronomerResponseDTO> getAstronomers();

    /**
     * Retrieves an astronomer by their unique identifier.
     * @param id the UUID of the astronomer
     * @return the corresponding astronomer details
     */
    AstronomerResponseDTO getAstronomerById(UUID id);

    /**
     * Retrieves a list of astronomers based on their city of residence.
     * @param city the name of the city
     * @return a list of astronomers living in the specified city
     */
    List<AstronomerResponseDTO> getAstronomerByCity(String city);

    /**
     * Assigns a list of celestial discoveries to an astronomer.
     * @param id the UUID of the astronomer
     * @param requestDTO the data containing the discoveries to be assigned
     * @return the list of celestial objects assigned to the astronomer
     */
    List<CelestialObjectResponseDTO> assignDiscoveriesToAstronomer(UUID id, DiscoveryAssignmentRequestDTO requestDTO);

    /**
     * Retrieves the list of celestial discoveries made by a specific astronomer.
     * @param id the UUID of the astronomer
     * @return the list of celestial objects discovered by the astronomer
     */
    List<CelestialObjectResponseDTO> getDiscoveriesByAstronomerId(UUID id);

    /**
     * Updates the information of an existing astronomer.
     * @param id the UUID of the astronomer to be updated
     * @param requestDTO the new data to update
     * @return the updated astronomer information
     */
    AstronomerResponseDTO updateAstronomerById(UUID id, AstronomerRequestDTO requestDTO);

    /**
     * Deactivates an astronomer by their unique identifier.
     * @param id the UUID of the astronomer to be deactivated
     * @return the updated astronomer information with deactivation applied
     */
    AstronomerResponseDTO deactivateAstronomerById(UUID id);

}
