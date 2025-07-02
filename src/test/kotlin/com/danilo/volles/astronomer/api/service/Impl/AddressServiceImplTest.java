package com.danilo.volles.astronomer.api.service.Impl;

import com.danilo.volles.astronomer.api.TestUtils;
import com.danilo.volles.astronomer.api.client.cep.CepClient;
import com.danilo.volles.astronomer.api.client.cep.responseDto.ViaCepResponse;
import com.danilo.volles.astronomer.api.service.AddressService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class AddressServiceImplTest {

    @Mock
    private CepClient cepClient;

    @InjectMocks
    private AddressServiceImpl addressService;

    @Test
    void getAddress_shouldReturnViaCepResponse_whenCepExists() {

        // Arrange
        ViaCepResponse mockResponse = TestUtils.mockViaCepResponse();
        String cep = mockResponse.cep();

        when(cepClient.searchAddress(cep)).thenReturn(mockResponse);

        // Act
        ViaCepResponse response = addressService.getAddress(cep);

        // Assert
        assertNotNull(response);
        assertEquals("Rua Helena", response.logradouro());
        assertEquals("São Paulo", response.localidade());
        verify(cepClient).searchAddress(cep);
    }

    @Test
    void getAddress_shouldThrowException_whenCepNotFound() {
        // Arrange
        String cep = "00000000";
        when(cepClient.searchAddress(cep)).thenReturn(null);

        // Act + Assert
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> addressService.getAddress(cep)
        );

        assertEquals("No address found for this CEP code: " + cep, exception.getMessage());
        verify(cepClient).searchAddress(cep);
    }
}