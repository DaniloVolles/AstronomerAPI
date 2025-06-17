package com.danilo.volles.astronomer.api.dto.request;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record AstronomerRequestDTO(

        @NotBlank(message = "fullName field is required")
        @Pattern(regexp = "^[\\p{L} '\\-]+$", message = "fullName must contain only letters and spaces")
        String fullName,

        @NotNull(message = "birthDate field is required")
        @Past(message = "birthDate must be a past date")
        LocalDate birthDate,

        @Email(message = "must be a valid email")
        @NotBlank(message = "email field is required")
        String email,

        @NotBlank(message = "phone field is required")
        @Pattern(
                regexp = "^\\(?\\d{2}\\)?[\\s-]?\\d{4,5}-?\\d{4}$",
                message = "Invalid phone format. Expected formats: (11) 91234-5678 or 11912345678")
        String phone,

        @NotBlank(message = "degree field is required")
        String degree,

        @NotBlank(message = "researchArea field is required")
        String researchArea,

        @NotBlank(message = "institution field is required")
        @Size(min = 3, max = 75, message = "institution must be between 3 and 75 characters")
        String institution,

        @NotBlank(message = "cep field is required")
        @Pattern(
                regexp = "^\\d{5}-?\\d{3}$",
                message = "Invalid CEP format. Expected 8 digits, optionally formatted as '12345-678'")
        String cep,

        @NotNull(message = "active field is required")
        Boolean active
) {
}
