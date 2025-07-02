package com.danilo.volles.astronomer.api

import com.danilo.volles.astronomer.api.client.cep.responseDto.ViaCepResponse
import com.danilo.volles.astronomer.api.dto.request.AstronomerRequestDTO
import com.danilo.volles.astronomer.api.model.Address
import com.danilo.volles.astronomer.api.model.Astronomer
import com.danilo.volles.astronomer.api.model.Degree
import java.time.LocalDate
import java.util.UUID

/**
 * Utility object for creating mock data used in unit tests.
 *
 * This object provides static factory methods to generate sample instances
 * of domain entities and DTOs commonly used during testing, ensuring consistency
 * and reducing boilerplate in test cases.
 *
 * Available mock data creators:
 * - [mockAstronomer]: Returns a sample [Astronomer] instance with predefined values.
 * - [mockAstronomerRequestDTO]: Returns a sample [AstronomerRequestDTO] instance with predefined values.
 * - [mockViaCepResponse]: Returns a sample [ViaCepResponse] instance representing address information.
 *
 * Usage:
 * ```
 * // Kotlin
 * val astronomer = TestUtils.mockAstronomer(UUID.randomUUID())
 * val requestDto = TestUtils.mockAstronomerRequestDTO()
 * val viaCepResponse = TestUtils.mockViaCepResponse()
 * ```
 * ```
 * // Java
 * Astronomer astronomer = TestUtils.mockAstronomer(UUID.randomUUID()
 * AstronomerRequestDTO requestDto = TestUtils.mockAstronomerRequestDTO()
 * ViaCepResponse viaCepResponse = TestUtils.mockViaCepResponse()
 * ```
 *
 * Note: This object is intended exclusively for test environments and should not be used in production code.
 */
object TestUtils {

    /**
     * @return mocked [Astronomer]
     */
    @JvmStatic
    fun mockAstronomer(id: UUID) = Astronomer(
        id,
        "Galileu Galilei",
        LocalDate.of(1564, 2, 15),
        "galileu.galilei@pisa.universita.it",
        "(11)91234-5678",
        Degree.DOCTOR,
        "",
        "",
        Address(mockViaCepResponse()),
        true
    )

    /**
     * @return mocked [AstronomerRequestDTO]
     */
    @JvmStatic
    fun mockAstronomerRequestDTO() = AstronomerRequestDTO(
        "Galileu Galilei",
        LocalDate.of(1564, 2, 15),
        "galileu.galilei@pisa.universita.it",
        "(11)91234-5678",
        "doctor",
        "Astronomy",
        "Pisa University",
        "04552050",
        false
    )

    /**
     * @return mocked [ViaCepResponse]
     */
    @JvmStatic
    fun mockViaCepResponse() = ViaCepResponse(
        "04552050",
        "Rua Helena",
        "",
        "Vila Olímpia",
        "São Paulo",
        "SP",
        "3550308",
        "1004",
        "11",
        "7107"
    )

}