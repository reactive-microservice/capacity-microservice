package com.pragma.api;

import com.pragma.api.dto.CapacityDTO;
import com.pragma.api.mapper.CapacityMapper;
import com.pragma.model.capacities.Capacity;
import com.pragma.usecase.capacities.FindAllCapacitiesUseCase;
import com.pragma.usecase.capacities.SaveCapacityUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {

    private final FindAllCapacitiesUseCase findAllCapacitiesUseCase;
    private final SaveCapacityUseCase saveCapacityUseCase;
    private final CapacityMapper capacityMapper;

    public Mono<ServerResponse> listenGETFindAllCapacitiesUseCase(ServerRequest request) {
        Integer page = Integer.valueOf(request.queryParam("page").orElse("0"));
        Integer size = Integer.valueOf(request.queryParam("size").orElse("10"));
        Boolean asc = Boolean.valueOf(request.queryParam("asc").orElse("true"));
        Boolean orderByTechSize = Boolean.valueOf(request.queryParam("orderByTechSize").orElse("false"));

        return ServerResponse.ok().body(findAllCapacitiesUseCase.action(page, size, asc, orderByTechSize), Capacity.class);
    }

    public Mono<ServerResponse> listenPOSTSaveCapacityUseCase(ServerRequest request) {
        return request.bodyToMono(CapacityDTO.class)
                .map(capacityMapper::toDomain)
                .flatMap(saveCapacityUseCase::action)
                .flatMap(capacity -> ServerResponse.ok().bodyValue(capacity))
                .onErrorResume(error -> ServerResponse.badRequest().bodyValue(error.getMessage()));
    }

}
