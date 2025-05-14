package com.danilo.volles.astronomer.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AstronomerResponseDTO {
    private String fullName;
    private String email;
    private String institution;
}
