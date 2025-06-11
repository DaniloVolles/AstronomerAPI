package com.danilo.volles.astronomer.api.model;

import com.danilo.volles.celestial.objects.api.wsdl.GetCelestialObjectByNameResponse;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document(collection = "celestial_objects")
public class CelestialObject {

    private Long id;

    private String name;
    private CelestialObjectType type;
    private Double diameterKm;

    private UUID astronomerId;

    public CelestialObject(GetCelestialObjectByNameResponse clientResponse, UUID astronomerId) {
        this.id = clientResponse.getCelestialObject().getId();
        this.name = clientResponse.getCelestialObject().getName();
        this.type = CelestialObjectType.fromClientValue(clientResponse.getCelestialObject().getCelestialObjectType());
        this.diameterKm = clientResponse.getCelestialObject().getDiameter();
        this.astronomerId = astronomerId;
    }
}
