package namgor.com.namgorstore.routes;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import namgor.com.namgorstore.models.entities.Contractor;
import namgor.com.namgorstore.models.entities.PayCheck;
import namgor.com.namgorstore.models.dto.PayCheckDto;
import namgor.com.namgorstore.services.PayCheckService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class PayCheckRoute {

    @Bean
    @RouterOperation(
            path = "/api/v1/paychecks/",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            beanClass = PayCheckService.class,
            method = RequestMethod.GET,
            beanMethod = "getAllPayChecks",
            operation = @Operation(
                    operationId = "getAllPayChecks",
                    responses = {
                            @ApiResponse(responseCode = "200",
                                    description = "Successful operation",
                                    content = @Content(schema =
                                    @Schema(implementation = Contractor.class)))
                    }
            )
    )
    public RouterFunction<ServerResponse> getAllPayChecks(PayCheckService service) {
        return route(
                GET("/api/v1/paychecks/"),
                request -> ServerResponse
                        .status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters
                                .fromProducer(service.getAllPayChecks(),
                                        PayCheckDto.class))
        );
    }

    @Bean
    @RouterOperation(
            path = "/api/v1/paychecks/",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            beanClass = PayCheckService.class,
            method = RequestMethod.POST,
            beanMethod = "createPayCheck",
            operation = @Operation(
                    operationId = "createPayCheck",
                    responses = {
                            @ApiResponse(responseCode = "201",
                                    description = "Successful operation",
                                    content = @Content(schema =
                                    @Schema(implementation = PayCheck.class))),
                            @ApiResponse(responseCode = "400",
                                    description = "Invalid recipe details supplied")},
                    requestBody = @RequestBody(content =
                    @Content(schema =
                    @Schema(implementation = PayCheck.class)))
            ))

    public RouterFunction<ServerResponse> createPayCheck(PayCheckService service) {
        return route(
                POST("/api/v1/paychecks/")
                        .and(accept(MediaType.APPLICATION_JSON)),
                request -> request
                        .bodyToMono(PayCheckDto.class)
                        .flatMap(service::postPayCheck)
                        .flatMap(dto -> ServerResponse
                                .status(HttpStatus.CREATED)
                                .bodyValue(dto)
                        )
                        .onErrorResume(e -> ServerResponse
                                .status(HttpStatus.BAD_REQUEST)
                                .build())
        );
    }
}
