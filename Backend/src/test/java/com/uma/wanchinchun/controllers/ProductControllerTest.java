package com.uma.wanchinchun.controllers;

import com.uma.wanchinchun.dtos.ProductDTO;
import com.uma.wanchinchun.exceptions.ExceedsLimitException;
import com.uma.wanchinchun.exceptions.PIMException;
import com.uma.wanchinchun.exceptions.UnauthorizedAccessException;
import com.uma.wanchinchun.services.IProductService;
import com.uma.wanchinchun.services.ProductServiceFactory;
import jdk.jfr.Description;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Product Controller - Test")
class ProductControllerTest {

    @Mock
    IProductService productService;
    @Mock
    ProductServiceFactory productServiceFactory;
    ProductController sut;

    static String JWT = "jwt-token";
    static String AUTH_HEADER = "Bearer " + JWT;
    static Long ACCOUNT_ID = 1L;

    @BeforeEach
    void setUp() {
        when(productServiceFactory.getService(any())).thenReturn(productService);
        sut = new ProductController(productServiceFactory);
    }

    @Nested
    @DisplayName("Get All Products - Tests")
    class GetAllProducts {

        ProductDTO productDTO;

        @BeforeEach
        void setUp() {
            productDTO = new ProductDTO();
            productDTO.setId(1L);
            productDTO.setGtin("gtin");
        }

        @Test
        @DisplayName("Get all products when auth is null -> should return Unauthorized")
        void getAllProducts_whenAuthIsNull_returnsUnauthorized() {
            // Arrange
            String auth = null;

            // Act
            ResponseEntity<List<ProductDTO>> response = sut.getAll(null, null, null, null, auth);

            // Assert
            assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        }

        @Test
        @DisplayName("Get all products when auth is empty -> should return Unauthorized")
        void getAllProducts_whenAuthIsEmpty_returnsUnauthorized() {
            // Arrange
            String auth = "";

            // Act
            ResponseEntity<List<ProductDTO>> response = sut.getAll(null, null, null, null, auth);

            // Assert
            assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        }

        @Test
        @DisplayName("Get all products when auth is invalid -> should return Unauthorized")
        void getAllProducts_whenAuthIsInvalid_returnsUnauthorized() {
            // Arrange
            String auth = JWT;

            // Act
            ResponseEntity<List<ProductDTO>> response = sut.getAll(null, null, null, null, auth);

            // Assert
            assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        }

        @Test
        @DisplayName("Get all products when all parameters are null -> should return Unauthorized")
        void getAllProducts_whenAllParamsAreNull_returnsUnauthorized() {
            // Arrange
            Long idProducto = null;
            Long idCuenta = null;
            Long idCategoria = null;
            String gtin = null;

            // Act
            ResponseEntity<List<ProductDTO>> response = sut.getAll(idProducto, idCuenta, idCategoria, gtin, AUTH_HEADER);

            // Assert
            assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        }

        @Test
        @DisplayName("Get all products when Gtin is empty and remaining params are null -> should return Unauthorized")
        void getAllProducts_whenGtinIsEmptyAndRemainingParamsAreNull_returnsUnauthorized() {
            // Arrange
            Long idProducto = null;
            Long idCuenta = null;
            Long idCategoria = null;
            String gtin = "";

            // Act
            ResponseEntity<List<ProductDTO>> response = sut.getAll(idProducto, idCuenta, idCategoria, gtin, AUTH_HEADER);

            // Assert
            assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        }

        @Test
        @DisplayName("Get all products when productId and accountId are null -> should return Unauthorized")
        void getAllProducts_whenIdProductoAndIdCuentaAreNegative_returnsUnauthorized() {
            // Arrange
            Long idProducto = -1L;
            Long idCuenta = -1L;
            Long idCategoria = null;
            String gtin = null;

            // Act
            ResponseEntity<List<ProductDTO>> response = sut.getAll(idProducto, idCuenta, idCategoria, gtin, AUTH_HEADER);

            // Assert
            assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        }

        @Test
        @DisplayName("Get all products when categoryId and accountId are null -> should return Unauthorized")
        void getAllProducts_whenIdCategoriaAndIdCuentaAreNegative_returnsUnauthorized() {
            // Arrange
            Long idProducto = null;
            Long idCuenta = -1L;
            Long idCategoria = -1L;
            String gtin = null;

            // Act
            ResponseEntity<List<ProductDTO>> response = sut.getAll(idProducto, idCuenta, idCategoria, gtin, AUTH_HEADER);

            // Assert
            assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        }

        @Test
        @DisplayName("Get all products when product service returns null -> should return Unauthorized")
        void getAllProducts_byGtinButServiceReturnsNull_returnsUnauthorized() {
            // Arrange
            Long idProducto = null;
            Long idCuenta = null;
            Long idCategoria = null;
            String gtin = "gtin";

            // Act
            ResponseEntity<List<ProductDTO>> response = sut.getAll(idProducto, idCuenta, idCategoria, gtin, AUTH_HEADER);

            // Assert
            assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        }

        @Test
        @DisplayName("Get all products when product service returns empty list -> should return Unauthorized")
        void getAllProducts_byGtinButServiceReturnsEmptyList_returnsUnauthorized() {
            // Arrange
            Long idProducto = null;
            Long idCuenta = null;
            Long idCategoria = null;
            String gtin = "gtin";

            // Act
            ResponseEntity<List<ProductDTO>> response = sut.getAll(idProducto, idCuenta, idCategoria, gtin, AUTH_HEADER);

            // Assert
            assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        }

        @Test
        @DisplayName("Get all products when product service throws UnauthorizedAccessException -> should return Forbidden")
        void getAllProducts_byGtinButServiceThrowsUnauthorizedAccessException_returnsForbidden() {
            // Arrange
            Long idProducto = null;
            Long idCuenta = null;
            Long idCategoria = null;
            String gtin = "gtin";

            when(productService.getAll(JWT, idProducto, idCuenta, idCategoria, gtin))
                    .thenThrow(UnauthorizedAccessException.class);

            // Act
            ResponseEntity<List<ProductDTO>> response = sut.getAll(idProducto, idCuenta, idCategoria, gtin, AUTH_HEADER);

            // Assert
            assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        }

        @Test
        @DisplayName("Get all products when product service throws unexpected exception -> should return Forbidden")
        void getAllProducts_byGtinButUnexpectedExceptionIsThrown_returnsInternalServerError() {
            // Arrange
            Long idProducto = null;
            Long idCuenta = null;
            Long idCategoria = null;
            String gtin = "gtin";

            when(productService.getAll(JWT, idProducto, idCuenta, idCategoria, gtin))
                    .thenThrow(RuntimeException.class);

            // Act
            ResponseEntity<List<ProductDTO>> response = sut.getAll(idProducto, idCuenta, idCategoria, gtin, AUTH_HEADER);

            // Assert
            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        }

        @Test
        @DisplayName("Get all products when product service returns a list -> should return OK")
        void getAllProducts_byGtin_returnsSingletonListAndOk() {
            // Arrange
            Long idProducto = null;
            Long idCuenta = null;
            Long idCategoria = null;
            String gtin = "gtin";

            when(productService.getAll(JWT, idProducto, idCuenta, idCategoria, gtin)).thenReturn(List.of(productDTO));

            // Act
            ResponseEntity<List<ProductDTO>> response = sut.getAll(idProducto, idCuenta, idCategoria, gtin, AUTH_HEADER);

            // Assert
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(1, response.getBody().size());
        }
    }

    @Nested
    @DisplayName("Create Product - Tests")
    class CreateProduct {

        ProductDTO productDTO;

        @BeforeEach
        void setUp() {
            productDTO = new ProductDTO();
            productDTO.setGtin("gtin");
            productDTO.setSku("sku");
            productDTO.setNombre("product-name");
        }

        @Test
        @DisplayName("Create product when auth is null -> should return Unauthorized")
        void createProduct_whenAuthIsNull_returnsUnauthorized() {
            // Arrange
            String auth = null;

            // Act
            ResponseEntity<ProductDTO> response = sut.create(ACCOUNT_ID, productDTO, auth);

            // Assert
            assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        }

        @Test
        @DisplayName("Create product when auth is empty -> should return Unauthorized")
        void createProduct_whenAuthIsEmpty_returnsUnauthorized() {
            // Arrange
            String auth = "";

            // Act
            ResponseEntity<ProductDTO> response = sut.create(ACCOUNT_ID, productDTO, auth);

            // Assert
            assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        }

        @Test
        @DisplayName("Create product when auth is invalid -> should return Unauthorized")
        void createProduct_whenAuthIsInvalid_returnsUnauthorized() {
            // Arrange
            // Act
            ResponseEntity<ProductDTO> response = sut.create(ACCOUNT_ID, productDTO, JWT);

            // Assert
            assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        }

        @Test
        @DisplayName("Create product when accountId is null -> should return Unauthorized")
        void createProduct_whenAccountIdIsNull_returnsUnauthorized() {
            // Arrange
            Long accountId = null;

            // Act
            ResponseEntity<ProductDTO> response = sut.create(accountId, productDTO, AUTH_HEADER);

            // Assert
            assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        }

        @Test
        @DisplayName("Create product when accountId is negative -> should return Unauthorized")
        void createProduct_whenAccountIdIsNegative_returnsUnauthorized() {
            // Arrange
            Long accountId = -1L;

            // Act
            ResponseEntity<ProductDTO> response = sut.create(accountId, productDTO, AUTH_HEADER);

            // Assert
            assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        }

        @Test
        @DisplayName("Create product when productDTO is null -> should return Unauthorized")
        void createProduct_whenProductDTOIsNull_returnsUnauthorized() {
            // Arrange
            ProductDTO nullProductDTO = null;

            // Act
            ResponseEntity<ProductDTO> response = sut.create(ACCOUNT_ID, nullProductDTO, AUTH_HEADER);

            // Assert
            assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        }

        @Test
        @DisplayName("Create product when product service returns null -> should return Unauthorized")
        void createProduct_whenProductServiceReturnsNull_returnsUnauthorized() {
            // Arrange
            when(productService.create(JWT, ACCOUNT_ID, productDTO)).thenReturn(null);

            // Act
            ResponseEntity<ProductDTO> response = sut.create(ACCOUNT_ID, productDTO, AUTH_HEADER);

            // Assert
            assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        }

        @Test
        @DisplayName("Create product when PIMException is thrown -> should return Forbidden")
        void createProduct_whenPIMExceptionIsThrown_returnsForbidden() {
            // Arrange
            when(productService.create(JWT, ACCOUNT_ID, productDTO))
                    .thenThrow(PIMException.class);

            // Act
            ResponseEntity<ProductDTO> response = sut.create(ACCOUNT_ID, productDTO, AUTH_HEADER);

            // Assert
            assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        }

        @Test
        @DisplayName("Create product when UnauthorizedAccessException is thrown -> should return Forbidden")
        void createProduct_whenUnauthorizedAccessExceptionIsThrown_returnsForbidden() {
            // Arrange
            when(productService.create(JWT, ACCOUNT_ID, productDTO))
                    .thenThrow(UnauthorizedAccessException.class);

            // Act
            ResponseEntity<ProductDTO> response = sut.create(ACCOUNT_ID, productDTO, AUTH_HEADER);

            // Assert
            assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        }

        @Test
        @DisplayName("Create product when ExceedsLimitException is thrown -> should return Forbidden")
        void createProduct_whenExceedsLimitExceptionIsThrown_returnsForbidden() {
            // Arrange
            when(productService.create(JWT, ACCOUNT_ID, productDTO))
                    .thenThrow(ExceedsLimitException.class);

            // Act
            ResponseEntity<ProductDTO> response = sut.create(ACCOUNT_ID, productDTO, AUTH_HEADER);

            // Assert
            assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        }

        @Test
        @DisplayName("Create product when unexpected exception is thrown -> should return InternalServerError")
        void createProduct_whenUnexpectedExceptionIsThrown_returnsInternalServerError() {
            // Arrange
            when(productService.create(JWT, ACCOUNT_ID, productDTO))
                    .thenThrow(RuntimeException.class);

            // Act
            ResponseEntity<ProductDTO> response = sut.create(ACCOUNT_ID, productDTO, AUTH_HEADER);

            // Assert
            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        }

        @Test
        @DisplayName("Create product when all conditions are met -> should return Ok")
        void createProduct_whenAllConditionsMet_returnsOk() {
            // Arrange
            productDTO.setId(1L);

            when(productService.create(JWT, ACCOUNT_ID, productDTO))
                    .thenReturn(productDTO);

            // Act
            ResponseEntity<ProductDTO> response = sut.create(ACCOUNT_ID, productDTO, AUTH_HEADER);

            // Assert
            assertEquals(HttpStatus.CREATED, response.getStatusCode());
        }

    }

    @Nested
    @DisplayName("Update Product - Tests")
    class UpdateProduct {

        Long productId;
        ProductDTO productDTO;

        @BeforeEach
        void setUp() {
            productId = 1L;
            productDTO = new ProductDTO();
            productDTO.setId(productId);
            productDTO.setGtin("gtin");
            productDTO.setSku("sku");
            productDTO.setNombre("product-name");
        }

        @Test
        @DisplayName("Update product when auth is null -> should return Unauthorized")
        void updateProduct_whenAuthIsNull_returnsUnauthorized() {
            // Arrange
            String auth = null;

            // Act
            ResponseEntity<ProductDTO> response = sut.update(productId, productDTO, auth);

            // Assert
            assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        }

        @Test
        @DisplayName("Update product when auth is empty -> should return Unauthorized")
        void updateProduct_whenAuthIsEmpty_returnsUnauthorized() {
            // Arrange
            String auth = "";

            // Act
            ResponseEntity<ProductDTO> response = sut.update(productId, productDTO, auth);

            // Assert
            assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        }

        @Test
        @DisplayName("Update product when auth is invalid -> should return Unauthorized")
        void updateProduct_whenAuthIsInvalid_returnsUnauthorized() {
            // Arrange
            String auth = JWT;

            // Act
            ResponseEntity<ProductDTO> response = sut.update(productId, productDTO, auth);

            // Assert
            assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        }

        @Test
        @DisplayName("Update product when productId is null -> should return Unauthorized")
        void updateProduct_whenProductIdIsNull_returnsUnauthorized() {
            // Arrange
            Long nullProductId = null;

            // Act
            ResponseEntity<ProductDTO> response = sut.update(nullProductId, productDTO, AUTH_HEADER);

            // Assert
            assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        }

        @Test
        @DisplayName("Update product when productId is negative -> should return Unauthorized")
        void updateProduct_whenProductIdIsNegative_returnsUnauthorized() {
            // Arrange
            Long negativeProductId = -1L;

            // Act
            ResponseEntity<ProductDTO> response = sut.update(negativeProductId, productDTO, AUTH_HEADER);

            // Assert
            assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        }

        @Test
        @DisplayName("Update product when productDTO is null -> should return Unauthorized")
        void updateProduct_whenProductDTOIsNull_returnsUnauthorized() {
            // Arrange
            ProductDTO nullProductDTO = null;

            // Act
            ResponseEntity<ProductDTO> response = sut.update(productId, nullProductDTO, AUTH_HEADER);

            // Assert
            assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        }

        @Test
        @DisplayName("Update product when PIMException is thrown -> should return Unauthorized")
        void updateProduct_whenPIMExceptionIsThrown_returnsUnauthorized() {
            // Arrange
            when(productService.update(JWT, productId, productDTO))
                    .thenThrow(PIMException.class);

            // Act
            ResponseEntity<ProductDTO> response = sut.update(productId, productDTO, AUTH_HEADER);

            // Assert
            assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        }

        @Test
        @DisplayName("Update product when UnauthorizedAccessException is thrown -> should return Forbidden")
        void updateProduct_whenUnauthorizedAccessExceptionIsThrown_returnsForbidden() {
            // Arrange
            when(productService.update(JWT, productId, productDTO))
                    .thenThrow(UnauthorizedAccessException.class);

            // Act
            ResponseEntity<ProductDTO> response = sut.update(productId, productDTO, AUTH_HEADER);

            // Assert
            assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        }

        @Test
        @DisplayName("Update product when ExceedsLimitException is thrown -> should return Forbidden")
        void updateProduct_whenUnexpectedExceptionIsThrown_returnsInternalServerError() {
            // Arrange
            when(productService.update(JWT, productId, productDTO))
                    .thenThrow(RuntimeException.class);

            // Act
            ResponseEntity<ProductDTO> response = sut.update(productId, productDTO, AUTH_HEADER);

            // Assert
            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        }

        @Test
        @DisplayName("Update product when all conditions are met -> should return OK")
        void updateProduct_whenAllConditionsMet_returnsOk() {
            // Arrange
            // Act
            ResponseEntity<ProductDTO> response = sut.update(productId, productDTO, AUTH_HEADER);

            // Assert
            assertEquals(HttpStatus.OK, response.getStatusCode());
        }
    }

    @Nested
    @DisplayName("Delete Product - Tests")
    class DeleteProduct {

        Long productId;

        @BeforeEach
        void setUp() {
            productId = 1L;
        }

        @Test
        @DisplayName("Delete product when auth is null -> should return Unauthorized")
        void deleteProduct_whenAuthIsNull_returnsUnauthorized() {
            // Arrange
            String auth = null;

            // Act
            ResponseEntity<Void> response = sut.delete(productId, auth);

            // Assert
            assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        }

        @Test
        @DisplayName("Delete product when auth is empty -> should return Unauthorized")
        void deleteProduct_whenAuthIsEmpty_returnsUnauthorized() {
            // Arrange
            String auth = "";

            // Act
            ResponseEntity<Void> response = sut.delete(productId, auth);

            // Assert
            assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        }

        @Test
        @DisplayName("Delete product when auth is invalid -> should return Unauthorized")
        void deleteProduct_whenAuthIsInvalid_returnsUnauthorized() {
            // Arrange
            // Act
            ResponseEntity<Void> response = sut.delete(productId, JWT);

            // Assert
            assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        }

        @Test
        @DisplayName("Delete product when productId is null -> should return Unauthorized")
        void deleteProduct_whenProductIdIsNull_returnsUnauthorized() {
            // Arrange
            Long nullProductId = null;

            // Act
            ResponseEntity<Void> response = sut.delete(nullProductId, AUTH_HEADER);

            // Assert
            assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        }

        @Test
        @DisplayName("Delete product when productId is negative -> should return Unauthorized")
        void deleteProduct_whenProductIdIsNegative_returnsUnauthorized() {
            // Arrange
            Long negativeProductId = -1L;

            // Act
            ResponseEntity<Void> response = sut.delete(negativeProductId, AUTH_HEADER);

            // Assert
            assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        }

        @Test
        @DisplayName("Delete product when PIMException is thrown -> should return Unauthorized")
        void deleteProduct_whenPIMExceptionIsThrown_returnsUnauthorized() {
            // Arrange
            doThrow(new PIMException("")).when(productService).delete(JWT, productId);

            // Act
            ResponseEntity<Void> response = sut.delete(productId, AUTH_HEADER);

            // Assert
            assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        }

        @Test
        @DisplayName("Delete product when UnauthorizedAccessException is thrown -> should return Forbidden")
        void deleteProduct_whenUnauthorizedAccessExceptionIsThrown_returnsForbidden() {
            // Arrange
            doThrow(new UnauthorizedAccessException("")).when(productService).delete(JWT, productId);

            // Act
            ResponseEntity<Void> response = sut.delete(productId, AUTH_HEADER);

            // Assert
            assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        }

        @Test
        @DisplayName("Delete product when ExceedsLimitException is thrown -> should return Forbidden")
        void deleteProduct_whenUnexpectedExceptionIsThrown_returnsInternalServerError() {
            // Arrange
            doThrow(new RuntimeException("")).when(productService).delete(JWT, productId);

            // Act
            ResponseEntity<Void> response = sut.delete(productId, AUTH_HEADER);

            // Assert
            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        }

        @Test
        @DisplayName("Delete product when all conditions are met -> should return OK")
        void deleteProduct_whenAllConditionsMet_returnsOk() {
            // Arrange
            // Act
            ResponseEntity<Void> response = sut.delete(productId, AUTH_HEADER);

            // Assert
            assertEquals(HttpStatus.OK, response.getStatusCode());
        }
    }
}