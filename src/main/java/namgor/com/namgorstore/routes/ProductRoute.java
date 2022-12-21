package namgor.com.namgorstore.routes;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import namgor.com.namgorstore.models.entities.Product;
import namgor.com.namgorstore.models.dto.ProductDto;
import namgor.com.namgorstore.services.ProductService;
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

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ProductRoute {

    @Bean
    @RouterOperation(
            path = "/api/v1/products/",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            beanClass = ProductService.class,
            method = RequestMethod.GET,
            beanMethod = "getAllProducts",
            operation = @Operation(
                    operationId = "getAllProducts",
                    responses = {
                            @ApiResponse(responseCode = "200",
                                    description = "Successful operation",
                            content = @Content(schema =
                            @Schema(implementation = Product.class)))
                    }
            )
    )
    public RouterFunction<ServerResponse> getAllProducts(ProductService service) {
        return route(
                GET("/api/v1/products/"),
                request -> ServerResponse
                        .status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters
                                .fromProducer(service.getAllProducts(),
                                        ProductDto.class))
        );
    }

    @Bean
    @RouterOperation(
            path = "/api/v1/products/",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            beanClass = ProductService.class,
            method = RequestMethod.POST,
            beanMethod = "postProduct",
            operation = @Operation(
                    operationId = "postProduct",
                    responses = {
                            @ApiResponse(
                                    responseCode = "201",
                                    description = "Successful operation",
                                    content = @Content(schema =
                                    @Schema(implementation = Product.class))),
                            @ApiResponse(
                                    responseCode = "400",
                                    description = "Invalid recipe details supplied")},
                    requestBody = @RequestBody(content = @Content(schema =
                    @Schema(implementation = Product.class)))
            )
    )

    public RouterFunction<ServerResponse> createProduct(ProductService service) {
        return route(
                POST("/api/v1/products/")
                        .and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(ProductDto.class)
                        .flatMap(service::postProduct)
                        .flatMap(dto -> ServerResponse
                                .status(HttpStatus.CREATED)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(dto)
                        )
                        .onErrorResume(e -> ServerResponse
                                .status(HttpStatus.BAD_REQUEST).build())
        );
    }

    @Bean
    @RouterOperation(
            path = "/api/v1/products/",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            beanClass = ProductService.class,
            method = RequestMethod.PUT,
            beanMethod = "putProduct",
            operation = @Operation(
                    operationId = "putProduct",
                    responses = {
                            @ApiResponse(responseCode = "202",
                                    description = "Successful operation",
                                    content = @Content(schema =
                                    @Schema(implementation = Product.class))),
                            @ApiResponse(responseCode = "400",
                                    description = "Invalid recipe details supplied")},
                    requestBody = @RequestBody(content = @Content(schema =
                    @Schema(implementation = Product.class)))
            )
    )
    public RouterFunction<ServerResponse> updateProduct(ProductService service) {
        return route(
                PUT("/api/v1/products/")
                        .and(accept(MediaType.APPLICATION_JSON)),
                request -> request
                        .bodyToMono(ProductDto.class)
                        .flatMap(service::putProduct)
                        .flatMap(dto -> ServerResponse
                                .status(HttpStatus.ACCEPTED)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(dto)
                        )
                        .onErrorResume(e -> ServerResponse
                                .status(HttpStatus.BAD_REQUEST).build())
        );
    }

    @Bean
    @RouterOperation(
            path = "/api/v1/products/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            beanClass = ProductService.class,
            method = RequestMethod.DELETE,
            beanMethod = "deleteProductById",
            operation = @Operation(
                    operationId = "deleteProductById",
                    responses = {
                            @ApiResponse(
                                    responseCode = "202",
                                    description = "Successful operation"),
                            @ApiResponse(
                                    responseCode = "404",
                                    description = "Product not found")},
                    parameters = {@Parameter(in = ParameterIn.PATH,
                                             name = "id")}
            )
    )
    public RouterFunction<ServerResponse> deleteProductById(ProductService service) {
        return route(
                DELETE("/api/v1/products/{id}")
                        .and(accept(MediaType.APPLICATION_JSON)),
                request -> service
                        .deleteProductById(
                                request.pathVariable("id"))
                        .flatMap(_e -> ServerResponse
                                .status(HttpStatus.ACCEPTED)
                                .build())
                        .onErrorResume(e -> ServerResponse
                                .status(HttpStatus.NOT_FOUND)
                                .build())
        );
    }
}
