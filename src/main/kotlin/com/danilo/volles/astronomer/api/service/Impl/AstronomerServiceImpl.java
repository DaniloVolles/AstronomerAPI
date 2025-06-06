package com.danilo.volles.astronomer.api.service.Impl;

import com.danilo.volles.astronomer.api.client.cep.responseDto.ViaCepResponse;
import com.danilo.volles.astronomer.api.dto.request.AstronomerRequestDTO;
import com.danilo.volles.astronomer.api.dto.response.AstronomerResponseDTO;
import com.danilo.volles.astronomer.api.dto.response.CelestialObjectResponseDTO;
import com.danilo.volles.astronomer.api.exception.InvalidCepCodeException;
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

        verifyCepCode(requestDTO.cep());

        Degree degree = Degree.fromString(requestDTO.degree());

        ViaCepResponse cepResponse = addressService.getAddress(requestDTO.cep());
        Address address = new Address(cepResponse);

        Astronomer savedAstronomer = astronomerRepository.save(new Astronomer(requestDTO, degree, address));

        return astronomerToResponseDTO(savedAstronomer);
    }

    @Override
    public List<AstronomerResponseDTO> getAstronomers() {

        List<Astronomer> astronomers = astronomerRepository.findAll();

        return astronomers.stream()
                .map(this::astronomerToResponseDTO)
                .collect(Collectors.toList());
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

    private static void verifyCepCode(String cep) {
        if (!CepValidator.isCodeBrazilian(cep)) {
            log.error("Brazilian CEP code expected");
            throw new InvalidCepCodeException();
        }
    }

    private AstronomerResponseDTO astronomerToResponseDTO(Astronomer astronomer) {
        return new AstronomerResponseDTO(
                astronomer.getFullName(),
                astronomer.getEmail(),
                astronomer.getInstitution()
        );
    }
}
