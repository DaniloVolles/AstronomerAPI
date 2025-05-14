package com.danilo.volles.astronomer.api.repository;

import com.danilo.volles.astronomer.api.model.Astronomer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface AstronomerRepository extends MongoRepository<Astronomer, UUID> {
    Optional<Astronomer> findByFullName(String name);
    Optional<Astronomer> findByAddress_City(String city);
}
