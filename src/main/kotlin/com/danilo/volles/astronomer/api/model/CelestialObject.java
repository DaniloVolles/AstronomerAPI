package com.danilo.volles.astronomer.api.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Document(collection = "celestial_objects")
public class CelestialObject {

    private Integer id;

    private String name;
    private CelestialObjectType type;
    private BigDecimal diameterKm;

    private LocalDate discoveryDate;
    private String discoveryMethod;
    private String discoveryDescription;

    private UUID professorId;
}
