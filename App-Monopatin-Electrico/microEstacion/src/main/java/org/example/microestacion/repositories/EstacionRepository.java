package org.example.microestacion.repositories;

import org.example.microestacion.entities.Estacion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface EstacionRepository extends MongoRepository<Estacion, String> {

}
