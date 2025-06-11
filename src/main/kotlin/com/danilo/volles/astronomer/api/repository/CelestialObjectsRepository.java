package com.danilo.volles.astronomer.api.repository;

import com.danilo.volles.astronomer.api.model.CelestialObject;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface CelestialObjectsRepository extends MongoRepository<CelestialObject, Integer> {
    List<CelestialObject> findCelestialObjectsByAstronomerId(UUID astronomerId);
}
