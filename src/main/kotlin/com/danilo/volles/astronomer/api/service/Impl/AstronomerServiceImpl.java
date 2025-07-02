package com.danilo.volles.astronomer.api.service.Impl;

import com.danilo.volles.astronomer.api.client.celestialobjects.CelestialObjectsClient;
import com.danilo.volles.astronomer.api.client.cep.dto.ViaCepResponse;
import com.danilo.volles.astronomer.api.dto.request.AstronomerRequestDTO;
import com.danilo.volles.astronomer.api.dto.request.DiscoveryAssignmentRequestDTO;
import com.danilo.volles.astronomer.api.dto.response.AstronomerResponseDTO;
import com.danilo.volles.astronomer.api.dto.response.CelestialObjectResponseDTO;
import com.danilo.volles.astronomer.api.exception.ObjectAlreadyExistsException;
import com.danilo.volles.astronomer.api.exception.ObjectNotFoundException;
import com.danilo.volles.astronomer.api.model.Address;
import com.danilo.volles.astronomer.api.model.Astronomer;
import com.danilo.volles.astronomer.api.model.CelestialObject;
import com.danilo.volles.astronomer.api.model.Degree;
import com.danilo.volles.astronomer.api.repository.AstronomerRepository;
import com.danilo.volles.astronomer.api.repository.CelestialObjectsRepository;
import com.danilo.volles.astronomer.api.service.AstronomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementation of {@link AstronomerService}, responsible for handling the core business logic
 * related to astronomers and their celestial discoveries.
 *
 * <p>
 * This class integrates with:
 * <ul>
 *     <li>{@code AstronomerRepository} for persistence</li>
 *     <li>{@code CelestialObjectsClient} to fetch celestial object data</li>
 *     <li>{@code AddressService} for address resolution</li>
 * </ul>
 * </p>
 *
 * <p>
 * It ensures consistency in domain rules such as:
 * <ul>
 *     <li>Preventing duplicate astronomers</li>
 *     <li>Validating and assigning discoveries</li>
 *     <li>Managing deactivation and update flows</li>
 * </ul>
 * </p>
 */
@Slf4j
@Component
public class AstronomerServiceImpl implements AstronomerService {

    private final AstronomerRepository astronomerRepository;
    private final AddressServiceImpl addressService;
    private final CelestialObjectsRepository celestialObjectsRepository;
    private final CelestialObjectsClient celestialObjectsClient;

    public AstronomerServiceImpl(AstronomerRepository astronomerRepository,
                                 AddressServiceImpl addressService,
                                 CelestialObjectsRepository celestialObjectsRepository,
                                 CelestialObjectsClient celestialObjectsClient) {
        this.astronomerRepository = astronomerRepository;
        this.addressService = addressService;
        this.celestialObjectsRepository = celestialObjectsRepository;
        this.celestialObjectsClient = celestialObjectsClient;
    }

    @Override
    public AstronomerResponseDTO saveAstronomer(AstronomerRequestDTO requestDTO) {

        verifyAstronomerAlreadyExists(requestDTO.email());

        Degree degree = Degree.fromString(requestDTO.degree());

        ViaCepResponse cepResponse = addressService.getAddress(requestDTO.cep());
        Address address = new Address(cepResponse);

        Astronomer savedAstronomer = astronomerRepository.save(new Astronomer(requestDTO, degree, address));

        return this.entityToResponseDTO(savedAstronomer);
    }

    @Override
    public List<AstronomerResponseDTO> getAstronomers() {

        List<Astronomer> astronomers = astronomerRepository.findAll();

        verifyEmptyAstronomersList(astronomers);

        return astronomers.stream()
                .map(this::entityToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AstronomerResponseDTO getAstronomerById(UUID id) {
        Astronomer astronomer = this.findAstronomerById(id);
        return entityToResponseDTO(astronomer);
    }

    @Override
    public List<AstronomerResponseDTO> getAstronomerByCity(String city) {

        List<Astronomer> astronomers = astronomerRepository.findAllByAddress_CityIgnoreCase(city.trim());

        verifyEmptyAstronomersList(astronomers);

        return astronomers.stream()
                .map(this::entityToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CelestialObjectResponseDTO> assignDiscoveriesToAstronomer(UUID astronomerId, DiscoveryAssignmentRequestDTO requestDTO) {

        Astronomer astronomer = findAstronomerById(astronomerId);

        List<CelestialObjectResponseDTO> responseDTOList = new ArrayList<>();
        List<CelestialObject> celestialObjects = new ArrayList<>();

        requestDTO.celestialObjectNameList().forEach(name -> {
            var object = celestialObjectsClient.getCelestialObjectByName(name);
            celestialObjects.add(new CelestialObject(object, astronomer.getId()));
            responseDTOList.add(new CelestialObjectResponseDTO(object.getCelestialObject()));
        });

        celestialObjectsRepository.saveAll(celestialObjects);

        return responseDTOList;
    }

    @Override
    public List<CelestialObjectResponseDTO> getDiscoveriesByAstronomerId(UUID id) {

        Astronomer astronomer = findAstronomerById(id);

        List<CelestialObject> celestialObjects = celestialObjectsRepository.findCelestialObjectsByAstronomerId(astronomer.getId());

        if (celestialObjects.isEmpty()) {
            log.error("No celestial object found with id {}", id);
            throw new ObjectNotFoundException("No celestial objects found for this astronomer: " + id);
        }

        List<CelestialObjectResponseDTO> celestialObjectsResponse = new ArrayList<>();
        celestialObjects.forEach(c -> celestialObjectsResponse.add(new CelestialObjectResponseDTO(c.getName(), c.getType())));

        return celestialObjectsResponse;
    }

    @Override
    public AstronomerResponseDTO updateAstronomerById(UUID id, AstronomerRequestDTO requestDTO) {

        Degree degree = Degree.fromString(requestDTO.degree());
        Address address = new Address(addressService.getAddress(requestDTO.cep()));

        Astronomer astronomer = this.findAstronomerById(id);

        Astronomer updatedAstronomer = astronomer.applyUpdates(requestDTO, degree, address);

        astronomerRepository.save(updatedAstronomer);

        return entityToResponseDTO(updatedAstronomer);
    }


    @Override
    public AstronomerResponseDTO deactivateAstronomerById(UUID id) {

        Astronomer astronomer = this.findAstronomerById(id);

        if (astronomer.isActive()) {
            astronomer.deactivate();
            astronomerRepository.save(astronomer);
            return this.entityToResponseDTO(astronomer);
        }

        return this.entityToResponseDTO(astronomer);
    }

    private Astronomer findAstronomerById(UUID id) {
        return astronomerRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Astronomer with id {} not found", id);
                    return new ObjectNotFoundException();
                });
    }

    private static void verifyEmptyAstronomersList(List<Astronomer> astronomers) {
        if (astronomers.isEmpty()) {
            log.error("No astronomers found");
            throw new ObjectNotFoundException();
        }
    }

    private AstronomerResponseDTO entityToResponseDTO(Astronomer astronomer) {
        return new AstronomerResponseDTO(
                astronomer.getId(),
                astronomer.getFullName(),
                astronomer.getEmail(),
                astronomer.getInstitution(),
                astronomer.isActive()
        );
    }

    private void verifyAstronomerAlreadyExists(String email) {
        Astronomer astronomer = astronomerRepository.findByEmail(email);
        if (astronomer != null) {
            log.error("Astronomer with email {} already found", email);
            throw new ObjectAlreadyExistsException();
        }
    }
}
