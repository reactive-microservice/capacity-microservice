package com.pragma.usecase.capacities;

import com.pragma.model.capacities.Capacity;
import com.pragma.model.capacities.Technology;
import com.pragma.model.capacities.gateways.CapacityRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Validated
public class SaveCapacityUseCase {

    private final CapacityRepository capacityRepository;
    private final FindTechnologyByUseCase findTechnologyByUseCase;

    public Mono<Capacity> action(@Valid Capacity capacity) {
        if (isTechnologyDuplicate(capacity)) {
            return Mono.error(new IllegalArgumentException("Duplicate technologies found"));
        }
        return Mono.just(capacity)
                .flatMapMany(cap -> Flux.fromIterable(cap.getTechnologies()))
                .flatMap(this::fetchTechnology)
                .then(Mono.just(capacity))
                .flatMap(capacityRepository::save);
    }

    private Mono<Technology> fetchTechnology(Technology technology) {
        return findTechnologyByUseCase.action(technology.getId())
                .map(fetchedTechnology -> {
                    technology.setName(fetchedTechnology.getName());
                    return technology;
                })
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Technology not found")));
    }

   private boolean isTechnologyDuplicate(Capacity capacity) {
       return capacity.getTechnologies().stream()
               .map(Technology::getId)
               .distinct()
               .count() != capacity.getTechnologies().size();
   }
}
