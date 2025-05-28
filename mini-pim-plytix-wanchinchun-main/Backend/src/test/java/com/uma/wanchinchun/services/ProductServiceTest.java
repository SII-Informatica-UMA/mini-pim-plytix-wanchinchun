package com.uma.wanchinchun.services;

import com.uma.wanchinchun.dtos.ProductDTO;
import com.uma.wanchinchun.exceptions.ExceedsLimitException;
import com.uma.wanchinchun.exceptions.PIMException;
import com.uma.wanchinchun.exceptions.UnauthorizedAccessException;
import com.uma.wanchinchun.mappers.IMapper;
import com.uma.wanchinchun.models.Product;
import com.uma.wanchinchun.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private IAccessManagerService accessManagerService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private IMapper<Product, ProductDTO> mapper;

    @InjectMocks
    private ProductService productService;

    private ProductDTO productDTO;
    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        productDTO = new ProductDTO(
                1L, "1234567890123", "SKU123", "Producto de prueba",
                "Descripción corta", OffsetDateTime.now(), OffsetDateTime.now(),
                "thumbnail.jpg", Collections.emptyList(), Collections.emptySet(),
                Collections.emptyList()
        );

        product = new Product(
                1L, "1234567890123", "SKU123", "Producto de prueba",
                "Descripción corta", OffsetDateTime.now(), OffsetDateTime.now(),
                "thumbnail.jpg", 1L, Collections.emptyList(), Collections.emptySet(),
                Collections.emptyList(), Collections.emptyList()
        );

        when(mapper.mapToEntity(any(ProductDTO.class))).thenReturn(product);
        when(mapper.mapToDTO(any(Product.class))).thenReturn(productDTO);
    }

    @Test
    @DisplayName("getAll - Debe retornar producto por GTIN")
    void getAll_ByGtin_ReturnsProduct() throws UnauthorizedAccessException {
        // Arrange
        when(productRepository.findByGtin(anyString())).thenReturn(Optional.of(product));

        // Act
        List<ProductDTO> result = productService.getAll("jwt", null, 1L, null, "1234567890123");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(productDTO, result.get(0));
    }

    @Test
    @DisplayName("getAll - Debe lanzar UnauthorizedAccessException sin acceso a cuenta")
    void getAll_NoAccess_ThrowsUnauthorizedAccessException() {
        // Arrange
        when(accessManagerService.hasAccessToAccount(anyString(), anyLong())).thenReturn(false);

        // Act & Assert
        assertThrows(UnauthorizedAccessException.class, () -> 
            productService.getAll("jwt", null, 1L, null, null));
    }

    @Test
    @DisplayName("getAll - Debe retornar producto por ID")
    void getAll_ById_ReturnsProduct() throws UnauthorizedAccessException {
        // Arrange
        when(accessManagerService.hasAccessToAccount(anyString(), anyLong())).thenReturn(true);
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        // Act
        List<ProductDTO> result = productService.getAll("jwt", 1L, 1L, null, null);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(productDTO, result.get(0));
    }

    @Test
    @DisplayName("getAll - Debe retornar productos por categoría")
    void getAll_ByCategory_ReturnsProducts() throws UnauthorizedAccessException {
        // Arrange
        when(accessManagerService.hasAccessToAccount(anyString(), anyLong())).thenReturn(true);
        when(productRepository.findByCategoriasId(anyLong())).thenReturn(Collections.singletonList(product));

        // Act
        List<ProductDTO> result = productService.getAll("jwt", null, 1L, 1L, null);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(productDTO, result.get(0));
    }

    @Test
    @DisplayName("create - Debe crear producto correctamente")
    void create_ValidProduct_ReturnsCreatedProduct() throws ExceedsLimitException, UnauthorizedAccessException, PIMException {
        // Arrange
        when(accessManagerService.hasAccessToAccount(anyString(), anyLong())).thenReturn(true);
        when(accessManagerService.exceedsLimit(anyString(), anyLong())).thenReturn(false);
        when(productRepository.findByGtin(anyString())).thenReturn(Optional.empty());
        when(productRepository.save(any(Product.class))).thenReturn(product);

        // Act
        ProductDTO result = productService.create("jwt", 1L, productDTO);

        // Assert
        assertNotNull(result);
        assertEquals(productDTO, result);
    }

    @Test
    @DisplayName("create - Debe lanzar UnauthorizedAccessException sin acceso a cuenta")
    void create_NoAccess_ThrowsUnauthorizedAccessException() {
        // Arrange
        when(accessManagerService.hasAccessToAccount(anyString(), anyLong())).thenReturn(false);

        // Act & Assert
        assertThrows(UnauthorizedAccessException.class, () -> 
            productService.create("jwt", 1L, productDTO));
    }

    @Test
    @DisplayName("create - Debe lanzar ExceedsLimitException cuando se excede el límite")
    void create_ExceedsLimit_ThrowsExceedsLimitException() {
        // Arrange
        when(accessManagerService.hasAccessToAccount(anyString(), anyLong())).thenReturn(true);
        when(accessManagerService.exceedsLimit(anyString(), anyLong())).thenReturn(true);

        // Act & Assert
        assertThrows(ExceedsLimitException.class, () -> 
            productService.create("jwt", 1L, productDTO));
    }

    @Test
    @DisplayName("create - Debe lanzar PIMException cuando GTIN ya existe")
    void create_DuplicateGtin_ThrowsPIMException() {
        // Arrange
        when(accessManagerService.hasAccessToAccount(anyString(), anyLong())).thenReturn(true);
        when(accessManagerService.exceedsLimit(anyString(), anyLong())).thenReturn(false);
        when(productRepository.findByGtin(anyString())).thenReturn(Optional.of(product));

        // Act & Assert
        assertThrows(PIMException.class, () -> 
            productService.create("jwt", 1L, productDTO));
    }

    @Test
    @DisplayName("update - Debe actualizar producto correctamente")
    void update_ValidProduct_ReturnsUpdatedProduct() throws UnauthorizedAccessException, PIMException {
        // Arrange
        when(productRepository.findByGtin(anyString())).thenReturn(Optional.empty());
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(accessManagerService.hasAccessToAccount(anyString(), anyLong())).thenReturn(true);

        // Act
        ProductDTO result = productService.update("jwt", 1L, productDTO);

        // Assert
        assertNotNull(result);
        assertEquals(productDTO, result);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    @DisplayName("delete - Debe eliminar producto correctamente")
    void delete_ValidId_DeletesProduct() throws UnauthorizedAccessException, PIMException {
        // Arrange
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(accessManagerService.hasAccessToAccount(anyString(), anyLong())).thenReturn(true);

        // Act
        productService.delete("jwt", 1L);

        // Assert
        verify(productRepository, times(1)).deleteById(anyLong());
    }
}