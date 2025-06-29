package com.danilo.volles.astronomer.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Schema(description = "Request object for registering a new astronomer in the system.")
public record AstronomerRequestDTO(

        @Schema(description = "Full name of the astronomer", example = "Galileu Galilei")
        @NotBlank(message = "fullName field is required")
        @Pattern(regexp = "^[\\p{L} '\\-]+$", message = "fullName must contain only letters and spaces")
        String fullName,

        @Schema(description = "Birthdate of the astronomer", example = "1564-02-15")
        @NotNull(message = "birthDate field is required")
        @Past(message = "birthDate must be a past date")
        LocalDate birthDate,

        @Schema(description = "Email of the astronomer", example = "galileu.galilei@pisa.universita.it")
        @Email(message = "must be a valid email")
        @NotBlank(message = "email field is required")
        String email,

        @Schema(description = "Phone of the astronomer", example = "11912345678")
        @NotBlank(message = "phone field is required")
        @Pattern(
                regexp = "^\\(?\\d{2}\\)?[\\s-]?\\d{4,5}-?\\d{4}$",
                message = "Invalid phone format. Expected formats: (11) 91234-5678 or 11912345678")
        String phone,

        @Schema(description = "Degree of the astronomer", example = "doctor")
        @NotBlank(message = "degree field is required")
        String degree,

        @Schema(description = "Research Area of the astronomer", example = "Planets")
        @NotBlank(message = "researchArea field is required")
        String researchArea,

        @Schema(description = "Present Institution of the astronomer", example = "Università di Pisa")
        @NotBlank(message = "institution field is required")
        @Size(min = 3, max = 75, message = "institution must be between 3 and 75 characters")
        String institution,

        @Schema(description = "Cep code from where the astronomer resides", example = "69980000")
        @NotBlank(message = "cep field is required")
        @Pattern(
                regexp = "^\\d{5}-?\\d{3}$",
                message = "Invalid CEP format. Expected 8 digits, optionally formatted as '12345-678'")
        String cep,

        @Schema(description = "Indicates whether the astronomer is active", example = "true")
        @NotNull(message = "active field is required")
        Boolean active
) {
}
