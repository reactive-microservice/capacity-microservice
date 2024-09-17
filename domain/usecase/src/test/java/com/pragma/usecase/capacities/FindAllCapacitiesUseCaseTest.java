package com.pragma.usecase.capacities;

import com.pragma.model.capacities.Capacity;
import com.pragma.model.capacities.Technology;
import com.pragma.model.capacities.gateways.CapacityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class FindAllCapacitiesUseCaseTest {

    @InjectMocks
    private FindAllCapacitiesUseCase findAllCapacitiesUseCase;

    @Mock
    private CapacityRepository capacityRepository;
    @Mock
    private FindTechnologyByUseCase findTechnologyByUseCase;

    @Test
    void findAllCapacitiesOrderByTechSizeShouldBeSuccess(){

        Capacity capacity1 = new Capacity("1", "name1", "description1", List.of(new Technology("1", null)));
        Capacity capacity2 = new Capacity("2", "name2", "description2", List.of(new Technology("1", null), new Technology("2", null)));
        Capacity capacity3 = new Capacity("3", "name3", "description3", List.of(new Technology("1", null), new Technology("2", null), new Technology("3", null)));
        given(capacityRepository.findAll(Sort.by(Sort.Direction.ASC, "name"))).willReturn(Flux.just(capacity2, capacity3, capacity1));
        given(findTechnologyByUseCase.action(anyString())).willReturn(Mono.just(new Technology("1", "TechName")));

        Flux<Capacity> capacities = findAllCapacitiesUseCase.action(0,10,true,true);

        StepVerifier.create(capacities)
                .expectNext(capacity3)
                .expectNext(capacity2)
                .expectNext(capacity1)
                .verifyComplete();
    }
}