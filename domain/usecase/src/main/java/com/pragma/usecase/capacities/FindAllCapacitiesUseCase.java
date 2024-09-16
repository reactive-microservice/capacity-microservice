package com.pragma.usecase.capacities;

import com.pragma.model.capacities.Capacity;
import com.pragma.model.capacities.gateways.CapacityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;



@RequiredArgsConstructor
public class FindAllCapacitiesUseCase {

    private final CapacityRepository repository;

    private final FindTechnologyByUseCase findTechnologyByUseCase;

    public Flux<Capacity> action(Integer page, Integer size, Boolean asc, Boolean orderByTechSize) {
        return this.repository.findAll(orderByAscOrDesc(asc)) // sort by name asc or desc
            .skip((long) page * size) // skip the first page * size elements
            .take(size)// take the next size elements
            .flatMap(capacity -> Flux.fromIterable(capacity.getTechnologies())
                .flatMap(technology -> findTechnologyByUseCase.action(technology.getId())
                        .doOnNext(fetchedTechnology -> technology.setName(fetchedTechnology.getName())))
                .then(Mono.just(capacity)))
                .collectList()
                .flatMapMany(capacities -> orderByTechSize
                        ? Flux.fromIterable(capacities)
                        .sort((capacity1, capacity2) -> capacity2.getTechnologies().size() - capacity1.getTechnologies().size())
                        : Flux.fromIterable(capacities));
    }

    public Sort orderByAscOrDesc(Boolean asc) {
        return Sort.by(asc.equals(true)
                        ? Sort.Order.asc("name")
                        : Sort.Order.desc("name"));
    }


}
