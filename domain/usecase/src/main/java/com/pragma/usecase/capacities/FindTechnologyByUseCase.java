package com.pragma.usecase.capacities;

import com.pragma.model.capacities.Technology;
import reactor.core.publisher.Mono;
import org.springframework.web.reactive.function.client.WebClient;

public class FindTechnologyByUseCase {

    private final WebClient webClient;

    public FindTechnologyByUseCase(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8081").build();
    }

    public Mono<Technology> action(String id) {
        return webClient.get()
                .uri("/api/technology/{id}", id)
                .retrieve()
                .bodyToMono(Technology.class);
    }

}
