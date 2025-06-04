package com.danilo.volles.astronomer.api.service.Impl;

import com.danilo.volles.astronomer.api.client.cep.responseDto.ViaCepResponse;
import com.danilo.volles.astronomer.api.dto.request.AstronomerRequestDTO;
import com.danilo.volles.astronomer.api.dto.response.AstronomerResponseDTO;
import com.danilo.volles.astronomer.api.dto.response.CelestialObjectResponseDTO;
import com.danilo.volles.astronomer.api.exception.InvalidCepCodeException;
import com.danilo.volles.astronomer.api.exception.InvalidDegreeValueException;
import com.danilo.volles.astronomer.api.model.Address;
import com.danilo.volles.astronomer.api.model.Astronomer;
import com.danilo.volles.astronomer.api.model.Degree;
import com.danilo.volles.astronomer.api.repository.AstronomerRepository;
import com.danilo.volles.astronomer.api.service.AddressService;
import com.danilo.volles.astronomer.api.service.AstronomerService;
import com.danilo.volles.astronomer.api.util.CepValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

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

        if (!CepValidator.isCodeBrazilian(requestDTO.cep())) {
            log.error("Brazilian CEP code expected");
            throw new InvalidCepCodeException();
        }

        Degree degree = getDegreeFromString(requestDTO.degree());

        ViaCepResponse cepResponse = addressService.getAddress(requestDTO.cep());

        Address address = new Address(cepResponse);

        Astronomer savedAstronomer = astronomerRepository.save(new Astronomer(requestDTO, degree, address));

        return new AstronomerResponseDTO(
                savedAstronomer.getFullName(),
                savedAstronomer.getEmail(),
                savedAstronomer.getInstitution()
        );
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

    private static Degree getDegreeFromString(final String value) {
        return Arrays.stream(Degree.values())
                .filter(degree -> degree.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(InvalidDegreeValueException::new);
    }

}
