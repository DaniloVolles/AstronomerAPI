package com.danilo.volles.astronomer.api.model;

import com.danilo.volles.astronomer.api.dto.request.AstronomerRequestDTO;
import com.danilo.volles.astronomer.api.util.DateValidator;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@Document(collection = "astronomers")
public class Astronomer {

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
    private boolean active;

    public Astronomer(AstronomerRequestDTO dto, Degree degree, Address address) {
        this.id = UUID.randomUUID();
        this.fullName = dto.fullName();
        this.birthDate = DateValidator.getLocalDateFromString(dto.birthDate());
        this.email = dto.email();
        this.phone = dto.phone();
        this.degree = degree;
        this.researchArea = dto.researchArea();
        this.institution = dto.institution();
        this.address = address;
        this.active = dto.active();
    }

    public Astronomer applyUpdates(AstronomerRequestDTO dto, Degree degree, Address address) {
        this.fullName = dto.fullName();
        this.birthDate = DateValidator.getLocalDateFromString(dto.birthDate());
        this.email = dto.email();
        this.phone = dto.phone();
        this.degree = degree;
        this.researchArea = dto.researchArea();
        this.institution = dto.institution();
        this.address = address;
        this.active = dto.active();
        return this;
    }

    public void inactivate(){
        this.active = false;
    }
}
