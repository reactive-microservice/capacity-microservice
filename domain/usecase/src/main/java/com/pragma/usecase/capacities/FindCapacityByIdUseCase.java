package com.pragma.usecase.capacities;

import com.pragma.model.capacities.Capacity;
import com.pragma.model.capacities.Technology;
import com.pragma.model.capacities.gateways.CapacityRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class FindCapacityByIdUseCase {

    private final CapacityRepository capacityRepository;
    private final FindTechnologyByUseCase findTechnologyByUseCase;

    public Mono<Capacity> action(String id) {
        return capacityRepository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Capacity not found")))
                .flatMap(capacity -> Flux.fromIterable(capacity.getTechnologies())
                        .flatMap(this::fetchTechnology)
                        .collectList()
                        .flatMap(technologies -> {
                            capacity.setTechnologies(technologies);
                            return Mono.just(capacity);
                        }));
    }

    private Mono<Technology> fetchTechnology(Technology technology) {
        return findTechnologyByUseCase.action(technology.getId())
                .map(fetchedTechnology -> {
                    technology.setName(fetchedTechnology.getName());
                    return technology;
                })
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Technology not found")));
    }
}
