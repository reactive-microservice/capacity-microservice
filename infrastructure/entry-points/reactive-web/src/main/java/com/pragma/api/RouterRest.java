package com.pragma.api;

import com.pragma.api.dto.CapacityDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {

    @RouterOperations({
            @RouterOperation(
                    path = "/api/capacity/{page}/{size}/{asc}/{orderByTechSize}",
                    beanClass = Handler.class,
                    beanMethod = "listenGETFindAllCapacitiesUseCase",
                    operation = @Operation(
                            operationId = "FindAllCapacities",
                            summary = "Find All Capacities",
                            parameters = {
                                    @Parameter(name = "page", description = "Page number", required = true, in = ParameterIn.PATH),
                                    @Parameter(name = "size", description = "Page size", required = true, in = ParameterIn.PATH),
                                    @Parameter(name = "asc", description = "Sort ascending", required = true, in = ParameterIn.PATH),
                                    @Parameter(name = "orderByCapSize", description = "Order by capacity size", required = true, in = ParameterIn.PATH)
                            }
                    )
            ),
            @RouterOperation(
                    path = "/api/capacity",
                    beanClass = Handler.class,
                    beanMethod = "listenPOSTSaveCapacityUseCase",
                    operation = @Operation(
                            operationId = "saveCapacity",
                            summary = "Save a new capacity",
                            requestBody = @RequestBody(
                                    description = "Capacity to be saved",
                                    required = true,
                                    content = @Content(
                                            schema = @Schema(implementation = CapacityDTO.class)
                                    )
                            )
                    )
            ),
            @RouterOperation(
                    path = "/api/capacity/{id}",
                    beanClass = Handler.class,
                    beanMethod = "listenGETFindCapacityByIdUseCase",
                    operation = @Operation(
                            operationId = "findCapacityById",
                            summary = "Find capacity by ID",
                            parameters = {
                                    @Parameter(name = "id", description = "Capacity ID", required = true, in = ParameterIn.PATH)
                            }
                    )
            )
    })
    @Bean
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {

        return route(GET("/api/capacity/{page}/{size}/{asc}/{orderByTechSize}"), handler::listenGETFindAllCapacitiesUseCase)
                .andRoute(POST("/api/capacity"), handler::listenPOSTSaveCapacityUseCase)
                .andRoute(GET("/api/capacity/{id}"), handler::listenGETFindCapacityByIdUseCase);
    }
}
