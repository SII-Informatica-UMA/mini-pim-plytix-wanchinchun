package com.uma.wanchinchun.controllers;

import com.uma.wanchinchun.dtos.ProductDTO;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.time.OffsetDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private ProductDTO createSampleProduct() {
        return new ProductDTO(
            1L, "1234567890123", "SKU123", "Producto de prueba",
            "Descripción corta", OffsetDateTime.now(), OffsetDateTime.now(),
            "thumbnail.jpg", Collections.emptyList(), Collections.emptySet(),
            Collections.emptyList()
        );
    }

    private HttpHeaders createHeadersWithToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer valid-token");
        return headers;
    }

    @Test
    @DisplayName("GET /producto - Sin token -> 401 UNAUTHORIZED")
    void getAll_NoAuthorizationHeader_ReturnsUnauthorized() {
        ResponseEntity<String> response = restTemplate.getForEntity(
            "/producto?idProducto=1&idCuenta=1&idCategoria=1&gtin=123", 
            String.class
        );
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    @DisplayName("GET /producto - Token inválido -> 401 UNAUTHORIZED")
    void getAll_InvalidAuthorizationHeader_ReturnsUnauthorized() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "InvalidToken");
        
        HttpEntity<?> entity = new HttpEntity<>(headers);
        
        ResponseEntity<String> response = restTemplate.exchange(
            "/producto?idProducto=1&idCuenta=1&idCategoria=1&gtin=123",
            HttpMethod.GET,
            entity,
            String.class
        );
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    @DisplayName("POST /producto - Límite excedido -> 403 FORBIDDEN")
    void create_ExceedsLimit_ReturnsForbidden() {
        ProductDTO product = createSampleProduct();
        HttpEntity<ProductDTO> request = new HttpEntity<>(product, createHeadersWithToken());
        
        ResponseEntity<String> response = restTemplate.postForEntity(
            "/producto?idCuenta=1",
            request,
            String.class
        );
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    @DisplayName("DELETE /producto/{id} - Error PIM -> 401 UNAUTHORIZED")
    void delete_PIMException_ReturnsUnauthorized() {
        HttpEntity<?> request = new HttpEntity<>(createHeadersWithToken());
        
        ResponseEntity<String> response = restTemplate.exchange(
            "/producto/1",
            HttpMethod.DELETE,
            request,
            String.class
        );
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    @DisplayName("GET /producto - Solicitud válida -> 200 OK")
    void getAll_ValidRequest_ReturnsOk() {
        HttpEntity<?> request = new HttpEntity<>(createHeadersWithToken());
        
        ResponseEntity<String> response = restTemplate.exchange(
            "/producto?idProducto=1&idCuenta=1&idCategoria=1&gtin=123",
            HttpMethod.GET,
            request,
            String.class
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("PUT /producto/{id} - Actualización exitosa -> 200 OK")
    void update_ValidProduct_ReturnsOk() {
        ProductDTO product = createSampleProduct();
        HttpEntity<ProductDTO> request = new HttpEntity<>(product, createHeadersWithToken());
        
        ResponseEntity<String> response = restTemplate.exchange(
            "/producto/1",
            HttpMethod.PUT,
            request,
            String.class
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}