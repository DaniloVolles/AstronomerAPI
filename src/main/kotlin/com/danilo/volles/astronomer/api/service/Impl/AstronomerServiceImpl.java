package com.danilo.volles.astronomer.api.service.Impl;

import com.danilo.volles.astronomer.api.client.cep.responseDto.ViaCepResponse;
import com.danilo.volles.astronomer.api.dto.request.AstronomerRequestDTO;
import com.danilo.volles.astronomer.api.dto.response.AstronomerResponseDTO;
import com.danilo.volles.astronomer.api.dto.response.CelestialObjectResponseDTO;
import com.danilo.volles.astronomer.api.exception.InvalidCepCodeException;
import com.danilo.volles.astronomer.api.exception.ObjectNotFoundException;
import com.danilo.volles.astronomer.api.model.Address;
import com.danilo.volles.astronomer.api.model.Astronomer;
import com.danilo.volles.astronomer.api.model.Degree;
import com.danilo.volles.astronomer.api.repository.AstronomerRepository;
import com.danilo.volles.astronomer.api.service.AddressService;
import com.danilo.volles.astronomer.api.service.AstronomerService;
import com.danilo.volles.astronomer.api.util.CepValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Slf4j
@Component
public class AstronomerServiceImpl implements AstronomerService {

    private final AstronomerRepository astronomerRepository;
    private final AddressService addressService;

    public AstronomerServiceImpl(AstronomerRepository astronomerRepository, AddressService addressService) {
        this.astronomerRepository = astronomerRepository;
        this.addressService = addressService;
    }

    @Override
    public AstronomerResponseDTO saveAstronomer(AstronomerRequestDTO requestDTO) {

        CepValidator.verifyCepCode(requestDTO.cep());

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
    public CelestialObjectResponseDTO getDiscoveriesByAstronomerName(String AstronomerName) {
        return null;
    }

    @Override
    public AstronomerResponseDTO updateAstronomerById(UUID id, AstronomerRequestDTO requestDTO) {

        CepValidator.verifyCepCode(requestDTO.cep());

        Degree degree = Degree.fromString(requestDTO.degree());
        Address address = new Address(addressService.getAddress(requestDTO.cep()));

        Astronomer astronomer = this.findAstronomerById(id);

        Astronomer updatedAstronomer = astronomer.applyUpdates(requestDTO, degree, address);

        astronomerRepository.save(updatedAstronomer);

        return entityToResponseDTO(updatedAstronomer);
    }


    @Override
    public AstronomerResponseDTO inactivateAstronomerById(UUID id) {

        Astronomer astronomer = this.findAstronomerById(id);

        if (astronomer.isActive()) {
            astronomer.inactivate();
            astronomerRepository.save(astronomer);
            return this.entityToResponseDTO(astronomer);
        }

        return this.entityToResponseDTO(astronomer);
    }

    private Astronomer findAstronomerById(UUID id) {
        return astronomerRepository.findById(id)
                .orElseThrow(ObjectNotFoundException::new);
    }

    private static void verifyEmptyAstronomersList(List<Astronomer> astronomers){
        if (astronomers.isEmpty()) {
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
}
