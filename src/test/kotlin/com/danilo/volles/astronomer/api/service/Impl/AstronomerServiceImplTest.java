package com.danilo.volles.astronomer.api.service.Impl;

import com.danilo.volles.astronomer.api.TestUtils;
import com.danilo.volles.astronomer.api.client.celestialObjects.CelestialObjectsClient;
import com.danilo.volles.astronomer.api.client.cep.responseDto.ViaCepResponse;
import com.danilo.volles.astronomer.api.dto.request.AstronomerRequestDTO;
import com.danilo.volles.astronomer.api.dto.request.DiscoveryAssignmentRequestDTO;
import com.danilo.volles.astronomer.api.dto.response.AstronomerResponseDTO;
import com.danilo.volles.astronomer.api.dto.response.CelestialObjectResponseDTO;
import com.danilo.volles.astronomer.api.exception.ObjectAlreadyExistsException;
import com.danilo.volles.astronomer.api.exception.ObjectNotFoundException;
import com.danilo.volles.astronomer.api.model.*;
import com.danilo.volles.astronomer.api.repository.AstronomerRepository;
import com.danilo.volles.astronomer.api.repository.CelestialObjectsRepository;
import com.danilo.volles.astronomer.api.util.Constants;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@SpringBootTest
class AstronomerServiceImplTest {

    @Mock
    private AstronomerRepository astronomerRepository;
    @Mock
    private AddressServiceImpl addressService;
    @Mock
    private CelestialObjectsRepository celestialObjectsRepository;
    @Mock
    private CelestialObjectsClient celestialObjectsClient;

    @InjectMocks
    private AstronomerServiceImpl astronomerService;

    @Test
    void saveAstronomer_shouldSuccess() {

        // Arrange
        AstronomerRequestDTO requestDTO = TestUtils.mockAstronomerRequestDTO();
        ViaCepResponse cepResponse = TestUtils.mockViaCepResponse();

        var address = new Address(cepResponse);

        var degree = Degree.fromString(requestDTO.degree());

        var astronomer = new Astronomer(requestDTO, degree, address);
        astronomer.setId(UUID.randomUUID());

        when(addressService.getAddress("04552050")).thenReturn(cepResponse);
        when(astronomerRepository.save(any(Astronomer.class))).thenReturn(astronomer);

        // Act
        AstronomerResponseDTO response = astronomerService.saveAstronomer(requestDTO);

        // Assert
        assertNotNull(response);
        assertEquals("Galileu Galilei", response.fullName());
        assertEquals("galileu.galilei@pisa.universita.it", response.email());
        assertEquals(false, response.isActive());
    }

    @Test
    void saveAstronomer_shouldFail_whenAstronomerAlreadyExists() {

        // Arrange
        AstronomerRequestDTO requestDTO = TestUtils.mockAstronomerRequestDTO();

        var existingAstronomer = new Astronomer();
        existingAstronomer.setEmail(requestDTO.email());

        when(astronomerRepository.findByEmail(requestDTO.email()))
                .thenReturn(existingAstronomer);

        // Act
        var exception = assertThrows(
                ObjectAlreadyExistsException.class,
                () -> astronomerService.saveAstronomer(requestDTO)
        );

        // Assert
        assertEquals(Constants.Error.OBJECT_ALREADY_EXISTS, exception.getMessage());
    }

    @Test
    void getAstronomers_shouldSuccess() {

        // Arrange
        var astronomers = List.of(
                mock(Astronomer.class),
                mock(Astronomer.class)
        );

        when(astronomerRepository.findAll()).thenReturn(astronomers);

        // Act
        List<AstronomerResponseDTO> result = astronomerService.getAstronomers();

        // Assert
        assertNotNull(result);
        assertEquals(2, astronomers.size());

    }

    @Test
    void getAstronomers_shouldFail_whenEmptyAstronomerList() {

        // Arrange
        List<Astronomer> astronomers = new ArrayList<>();

        when(astronomerRepository.findAll()).thenReturn(astronomers);

        // Act
        var exception = assertThrows(
                ObjectNotFoundException.class,
                () -> astronomerService.getAstronomers()
        );

        // Assert
        assertEquals(Constants.Error.OBJECT_NOT_FOUND, exception.getMessage());

    }

    @Test
    void getAstronomerById_shouldSucess() {

        // Arrange
        var uuid = UUID.randomUUID();

        Astronomer astronomer = TestUtils.mockAstronomer(uuid);

        when(astronomerRepository.findById(uuid)).thenReturn(Optional.of(astronomer));

        // Act
        AstronomerResponseDTO response = astronomerService.getAstronomerById(uuid);

        // Assert
        assertNotNull(response);
        assertEquals(uuid, response.id());
        assertEquals(astronomer.getEmail(), response.email());
        assertEquals(astronomer.isActive(), response.isActive());
    }

    @Test
    void getAstronomerById_shouldFail_whenWrongId() {

        // Arrange
        var uuid = UUID.randomUUID();

        when(astronomerRepository.findById(uuid)).thenReturn(Optional.empty());

        // Act
        var exception = assertThrows(
                ObjectNotFoundException.class,
                () -> astronomerService.getAstronomerById(uuid)
        );

        // Assert
        assertNotNull(exception);
        assertEquals(Constants.Error.OBJECT_NOT_FOUND, exception.getMessage());
    }

    @Test
    void getAstronomerByCity_shouldSuccess() {

        // Arrange
        var city = "São Paulo";
        var astronomers = List.of(
                TestUtils.mockAstronomer(UUID.randomUUID()),
                TestUtils.mockAstronomer(UUID.randomUUID()),
                TestUtils.mockAstronomer(UUID.randomUUID())
        );

        when(astronomerRepository.findAllByAddress_CityIgnoreCase(city))
                .thenReturn(astronomers);

        // Act
        List<AstronomerResponseDTO> astronomersOfSaoPaulo = astronomerService.getAstronomerByCity(city);

        // Assert
        assertNotNull(astronomersOfSaoPaulo);
        assertEquals(3, astronomersOfSaoPaulo.size());
        assertEquals(astronomers.getFirst().getFullName(), astronomersOfSaoPaulo.getFirst().fullName());
        assertEquals(city, astronomers.getFirst().getAddress().getCity());
    }

    @Test
    void getAstronomerByCity_shouldFail_whenEmptyList() {

        // Arrange
        var city = "  São Paulo    ";

        when(astronomerRepository.findAllByAddress_CityIgnoreCase(city))
                .thenReturn(new ArrayList<>());

        // Act
        var exception = assertThrows(
                ObjectNotFoundException.class,
                () -> astronomerService.getAstronomerByCity(city)
        );

        // Assert
        assertNotNull(exception);
        assertEquals(Constants.Error.OBJECT_NOT_FOUND, exception.getMessage());
    }

    @Test
    void assignDiscoveriesToAstronomer_shouldSuccess() {
        // Arrange
        var astronomerId = UUID.randomUUID();

        String celestialObjectName = "Alpha Centauri";
        var celestialObjectNames = List.of(celestialObjectName);

        var dto = new DiscoveryAssignmentRequestDTO(celestialObjectNames);

        var celestialObjectFromClient = mock(com.danilo.volles.celestial.objects.api.wsdl.CelestialObject.class);
        when(celestialObjectFromClient.getName())
                .thenReturn("Alpha Centauri");
        when(celestialObjectFromClient.getCelestialObjectType())
                .thenReturn(com.danilo.volles.celestial.objects.api.wsdl.CelestialObjectType.STAR);

        var soapResponse = new com.danilo.volles.celestial.objects.api.wsdl.GetCelestialObjectByNameResponse();
        soapResponse.setCelestialObject(celestialObjectFromClient);

        when(astronomerRepository.findById(astronomerId))
                .thenReturn(Optional.of(TestUtils.mockAstronomer(astronomerId)));
        when(celestialObjectsClient.getCelestialObjectByName(celestialObjectName)).thenReturn(soapResponse);
        when(celestialObjectsRepository.saveAll(anyList())).thenReturn(List.of());

        // Act
        var response = astronomerService.assignDiscoveriesToAstronomer(astronomerId, dto);

        // Assert
        assertNotNull(response);
        assertEquals(1, response.size());
        var result = response.getFirst();
        assertEquals("Alpha Centauri", result.name());
        assertEquals(CelestialObjectType.STAR, CelestialObjectType.valueOf(result.type().toString()));
    }

    @Test
    void assignDiscoveriesToAstronomer_shouldFail_whenCelestialObjectNotFound() {

        // Arrange
        var astronomerId = UUID.randomUUID();
        var objectName = "Alpha Centauri";
        var requestDTO = new DiscoveryAssignmentRequestDTO(List.of(objectName));

        when(astronomerRepository.findById(astronomerId))
                .thenReturn(Optional.of(TestUtils.mockAstronomer(astronomerId)));

        when(celestialObjectsClient.getCelestialObjectByName(objectName))
                .thenThrow(new ObjectNotFoundException("No celestial object found with name " + objectName));

        // Act
        var exception = assertThrows(
                ObjectNotFoundException.class,
                () -> astronomerService.assignDiscoveriesToAstronomer(astronomerId, requestDTO)
        );

        // Assert
        assertNotNull(exception);
        assertEquals("No celestial object found with name Alpha Centauri", exception.getMessage());
    }

    @Test
    void getDiscoveriesByAstronomerId_shouldReturnCelestialObjects_whenFound() {
        // Arrange
        UUID astronomerId = UUID.randomUUID();
        Astronomer astronomer = TestUtils.mockAstronomer(astronomerId);

        CelestialObject object1 = new CelestialObject();
        object1.setName("Alpha Centauri");
        object1.setType(CelestialObjectType.STAR);

        CelestialObject object2 = new CelestialObject();
        object2.setName("Betelgeuse");
        object2.setType(CelestialObjectType.STAR);

        when(astronomerRepository.findById(astronomerId)).thenReturn(Optional.of(astronomer));
        when(celestialObjectsRepository.findCelestialObjectsByAstronomerId(astronomerId))
                .thenReturn(List.of(object1, object2));

        // Act
        List<CelestialObjectResponseDTO> result = astronomerService.getDiscoveriesByAstronomerId(astronomerId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Alpha Centauri", result.getFirst().name());
        assertEquals(CelestialObjectType.STAR, result.getFirst().type());
    }

    @Test
    void getDiscoveriesByAstronomerId_shouldFail_whenAstronomerNotFound() {
        // Arrange
        UUID astronomerId = UUID.randomUUID();
        when(astronomerRepository.findById(astronomerId)).thenReturn(Optional.empty());

        // Act + Assert
        ObjectNotFoundException exception = assertThrows(
                ObjectNotFoundException.class,
                () -> astronomerService.getDiscoveriesByAstronomerId(astronomerId)
        );

        assertNotNull(exception);
    }

    @Test
    void getDiscoveriesByAstronomerId_shouldFail_whenCelestialObjectsListIsEmpty() {
        // Arrange
        UUID astronomerId = UUID.randomUUID();
        Astronomer astronomer = TestUtils.mockAstronomer(astronomerId);

        when(astronomerRepository.findById(astronomerId)).thenReturn(Optional.of(astronomer));
        when(celestialObjectsRepository.findCelestialObjectsByAstronomerId(astronomerId))
                .thenReturn(Collections.emptyList());

        // Act + Assert
        ObjectNotFoundException exception = assertThrows(
                ObjectNotFoundException.class,
                () -> astronomerService.getDiscoveriesByAstronomerId(astronomerId)
        );

        assertNotNull(exception);
        assertEquals("No celestial objects found for this astronomer: " + astronomerId, exception.getMessage());
    }

    @Test
    void updateAstronomerById_shouldUpdateSuccessfully() {
        // Arrange
        UUID id = UUID.randomUUID();
        var requestDTO = TestUtils.mockAstronomerRequestDTO();
        var viaCepResponse = TestUtils.mockViaCepResponse();
        var existingAstronomer = TestUtils.mockAstronomer(id);

        when(addressService.getAddress(requestDTO.cep()))
                .thenReturn(viaCepResponse);
        when(astronomerRepository.findById(id))
                .thenReturn(Optional.of(existingAstronomer));
        when(astronomerRepository.save(any(Astronomer.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        AstronomerResponseDTO response = astronomerService.updateAstronomerById(id, requestDTO);

        // Assert
        assertNotNull(response);
        assertEquals(requestDTO.fullName(), response.fullName());
        assertEquals(requestDTO.email(), response.email());
        assertEquals(requestDTO.institution(), response.institution());
        assertEquals(requestDTO.active(), response.isActive());
    }

    @Test
    void deactivateAstronomerById_shouldDeactivateSuccessfully() {

        // Arrange
        var uuid = UUID.randomUUID();
        Astronomer astronomer = TestUtils.mockAstronomer(uuid); // This mocked Astronomer is already active

        when(astronomerRepository.findById(uuid))
                .thenReturn(Optional.of(astronomer));

        // Act
        AstronomerResponseDTO responseDTO = astronomerService.deactivateAstronomerById(uuid);

        // Assert
        assertNotNull(responseDTO);
        assertEquals(false, responseDTO.isActive());

        verify(astronomerRepository).save(astronomer);
    }

    @Test
    void deactivateAstronomerById_shouldNotDeactivate() { // Because it is already deactivated

        // Arrange
        var uuid = UUID.randomUUID();
        Astronomer astronomer = TestUtils.mockAstronomer(uuid); // This mocked Astronomer is already active
        astronomer.deactivate();

        when(astronomerRepository.findById(uuid))
                .thenReturn(Optional.of(astronomer));

        // Act
        AstronomerResponseDTO responseDTO = astronomerService.deactivateAstronomerById(uuid);

        // Assert
        assertNotNull(responseDTO);
        assertEquals(false, responseDTO.isActive());
    }

    @Test
    void deactivateAstronomerById_shouldFail_whenAstronomerNotFound() {
        // Arrange
        UUID id = UUID.randomUUID();
        when(astronomerRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        var exception = assertThrows(
                ObjectNotFoundException.class,
                () -> astronomerService.deactivateAstronomerById(id)
        );

        // Assert
        assertNotNull(exception);
    }
}
