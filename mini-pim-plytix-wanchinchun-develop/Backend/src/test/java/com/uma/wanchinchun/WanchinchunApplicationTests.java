package com.uma.wanchinchun;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WanchinchunApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    private HttpHeaders createHeadersWithToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer valid-token");
        return headers;
	}

    @Test
    @DisplayName("GET /categoria-producto - Obtener categorías -> 200 OK")
    void getAllCategories_ReturnsOk() {
        HttpHeaders headers = createHeadersWithToken();
        HttpEntity<?> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                "/categoria-producto",
                HttpMethod.GET,
                request,
                String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("GET /categoria-producto - Sin token -> 401 UNAUTHORIZED")
    void getAllCategories_NoAuthorizationHeader_ReturnsUnauthorized() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                "/categoria-producto",
                HttpMethod.GET,
                request,
                String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    @DisplayName("GET /categoria-producto - Sin permisos -> 403 FORBIDDEN")
    void getAllCategories_InsufficientPermissions_ReturnsForbidden() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer insufficient-permission-token");
        HttpEntity<?> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                "/categoria-producto",
                HttpMethod.GET,
                request,
                String.class);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    @DisplayName("POST /categoria-producto - Crear categoría -> 201 CREATED")
    void createCategory_ValidCategory_ReturnsCreated() {
        HttpHeaders headers = createHeadersWithToken();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>("{\"nombre\": \"Nueva Categoria\"}", headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                "/categoria-producto",
                request,
                String.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    @DisplayName("POST /categoria-producto - Token inválido -> 401 UNAUTHORIZED")
    void createCategory_InvalidAuthorizationHeader_ReturnsUnauthorized() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "InvalidToken");
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>("{\"nombre\": \"Nueva Categoria\"}", headers);
        ResponseEntity<String> response = restTemplate.postForEntity(
                "/categoria-producto",
                request,
                String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    @DisplayName("POST /categoria-producto - Permisos insuficientes -> 403 FORBIDDEN")
    void createCategory_InsufficientPermissions_ReturnsForbidden() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer insufficient-permission-token");
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>("{\"nombre\": \"Nueva Categoria\"}", headers);
        ResponseEntity<String> response = restTemplate.postForEntity(
                "/categoria-producto",
                request,
                String.class);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    @DisplayName("PUT /categoria-producto/{idCategoria} - Actualizar categoría -> 200 OK")
    void updateCategory_ValidCategory_ReturnsOk() {
        HttpHeaders headers = createHeadersWithToken();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>("{\"nombre\": \"Categoria Actualizada\"}", headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "/categoria-producto/1",
                HttpMethod.PUT,
                request,
                String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("PUT /categoria-producto/{idCategoria} - Token inválido -> 401 UNAUTHORIZED")
    void updateCategory_InvalidAuthorizationHeader_ReturnsUnauthorized() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "InvalidToken");
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>("{\"nombre\": \"Categoria Actualizada\"}", headers);
        ResponseEntity<String> response = restTemplate.exchange(
                "/categoria-producto/1",
                HttpMethod.PUT,
                request,
                String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    @DisplayName("PUT /categoria-producto/{idCategoria} - Permisos insuficientes -> 403 FORBIDDEN")
    void updateCategory_InsufficientPermissions_ReturnsForbidden() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer insufficient-permission-token");
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>("{\"nombre\": \"Categoria Actualizada\"}", headers);
        ResponseEntity<String> response = restTemplate.exchange(
                "/categoria-producto/1",
                HttpMethod.PUT,
                request,
                String.class);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    @DisplayName("DELETE /categoria-producto/{idCategoria} - Eliminar categoría -> 200 OK")
    void deleteCategory_ValidCategory_ReturnsOk() {
        HttpHeaders headers = createHeadersWithToken();
        HttpEntity<?> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                "/categoria-producto/1",
                HttpMethod.DELETE,
                request,
                String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("DELETE /categoria-producto/{idCategoria} - Token inválido -> 401 UNAUTHORIZED")
    void deleteCategory_InvalidAuthorizationHeader_ReturnsUnauthorized() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "InvalidToken");
        HttpEntity<?> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                "/categoria-producto/1",
                HttpMethod.DELETE,
                request,
                String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    @DisplayName("DELETE /categoria-producto/{idCategoria} - Permisos insuficientes -> 403 FORBIDDEN")
    void deleteCategory_InsufficientPermissions_ReturnsForbidden() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer insufficient-permission-token");
        HttpEntity<?> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                "/categoria-producto/1",
                HttpMethod.DELETE,
                request,
                String.class);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }
}
