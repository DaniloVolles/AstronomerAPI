package com.danilo.volles.astronomer.api.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Document(collection = "professors")
public class Professor {

    @Id
    private UUID id;

    private String fullName;
    private LocalDate birthDate;
    private String email;
    private String phone;
    private Degree degree;
    private String researchArea;
    private String institution;
    private Address address;
}
