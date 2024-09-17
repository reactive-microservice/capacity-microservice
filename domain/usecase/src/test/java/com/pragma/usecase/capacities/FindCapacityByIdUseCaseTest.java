package com.pragma.usecase.capacities;

import com.pragma.model.capacities.Capacity;
import com.pragma.model.capacities.Technology;
import com.pragma.model.capacities.gateways.CapacityRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
class FindCapacityByIdUseCaseTest {
    @Mock
    private CapacityRepository capacityRepository;

    @Mock
    private FindTechnologyByUseCase findTechnologyByUseCase;

    @InjectMocks
    private FindCapacityByIdUseCase findCapacityByIdUseCase;


    @Test
    void findAllTechnologiesShouldReturnTwoItems() {
        Technology tech1 = new Technology();
        tech1.setId("tech1");
        Technology tech2 = new Technology();
        tech2.setId("tech2");
        List<Technology> technologies = Arrays.asList(tech1, tech2);

        Capacity capacity = new Capacity();
        capacity.setId("capacity1");
        capacity.setTechnologies(technologies);

        given(capacityRepository.findById(anyString())).willReturn(Mono.just(capacity));
        given(findTechnologyByUseCase.action(anyString())).willReturn(Mono.just(new Technology()));

        Mono<Capacity> result = findCapacityByIdUseCase.action("capacity1");

        StepVerifier.create(result).expectNext(capacity).verifyComplete();

    }
}