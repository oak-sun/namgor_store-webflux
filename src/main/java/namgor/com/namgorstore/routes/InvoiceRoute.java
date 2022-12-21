package namgor.com.namgorstore.routes;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import namgor.com.namgorstore.models.entities.Invoice;
import namgor.com.namgorstore.models.dto.InvoiceDto;
import namgor.com.namgorstore.services.InvoiceService;
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
public class InvoiceRoute {

    @Bean
    @RouterOperation(
            path = "/api/v1/invoices/",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            beanClass = InvoiceService.class,
            method = RequestMethod.GET,
            beanMethod = "getAllInvoices",
            operation = @Operation(
                    operationId = "getAllInvoices",
                    responses = {
                            @ApiResponse(
                                    responseCode = "200",
                                    description = "Successful operation",
                                    content = @Content(schema =
                                    @Schema(implementation = Invoice.class)))
                    }
            )
    )
    public RouterFunction<ServerResponse> getAllInvoices(InvoiceService service) {
        return route(
                GET("/api/v1/invoices/"),
                request -> ServerResponse.status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters
                                .fromProducer(service.getAllInvoices(),
                                        InvoiceDto.class))
        );
    }


    @Bean
    @RouterOperation(
            path = "/api/v1/invoices/",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            beanClass =  InvoiceService.class,
            method = RequestMethod.POST,
            beanMethod = "postInvoice",
            operation = @Operation(
                    operationId = "postInvoice",
                    responses = {
                            @ApiResponse(
                                    responseCode = "201",
                                    description = "Successful operation",
                                    content = @Content(schema =
                                    @Schema(implementation = Invoice.class))),
                            @ApiResponse(
                                    responseCode = "400",
                                    description = "Invalid recipe details supplied")},
                    requestBody = @RequestBody(content = @Content(schema =
                    @Schema(implementation = Invoice.class)))
            )
    )

    public RouterFunction<ServerResponse> createInvoice(InvoiceService service) {
        return route(
                POST("/api/v1/invoices/")
                        .and(accept(MediaType.APPLICATION_JSON)),
                request -> request
                        .bodyToMono(InvoiceDto.class)
                        .flatMap(service::postInvoice)
                        .flatMap(dto -> ServerResponse
                                .status(HttpStatus.CREATED)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(dto)
                        )
                        .onErrorResume(e -> ServerResponse
                                .status(HttpStatus.BAD_REQUEST)
                                .build())
        );
    }
}

