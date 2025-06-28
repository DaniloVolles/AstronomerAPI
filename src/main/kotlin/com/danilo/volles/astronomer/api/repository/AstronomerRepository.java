package com.danilo.volles.astronomer.api.repository;

import com.danilo.volles.astronomer.api.model.Astronomer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface AstronomerRepository extends MongoRepository<Astronomer, UUID> {
    Astronomer findByEmail(String name);
    List<Astronomer> findAllByAddress_CityIgnoreCase(String city);
}