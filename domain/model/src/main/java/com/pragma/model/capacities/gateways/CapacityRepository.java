package com.pragma.model.capacities.gateways;

import com.pragma.model.capacities.Capacity;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface CapacityRepository extends ReactiveMongoRepository<Capacity,String> {

}
