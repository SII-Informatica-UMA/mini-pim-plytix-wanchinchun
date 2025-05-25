package com.uma.wanchinchun.mappers;

import com.uma.wanchinchun.dtos.ProductDTO;
import com.uma.wanchinchun.models.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class ProductMapperTest {

    private ProductMapper productMapper = new ProductMapper();
    private Product product;
    private ProductDTO productDTO;

    @BeforeEach
    void setUp() {
        product = new Product(
            1L, "1234567890123", "SKU123", "Producto de prueba",
            "Descripción corta", OffsetDateTime.now(), OffsetDateTime.now(),
            "thumbnail.jpg", 1L, Collections.emptyList(), Collections.emptySet(),
            Collections.emptyList(), Collections.emptyList()
        );
        
        productDTO = new ProductDTO(
            1L, "1234567890123", "SKU123", "Producto de prueba",
            "Descripción corta", OffsetDateTime.now(), OffsetDateTime.now(),
            "thumbnail.jpg", Collections.emptyList(), Collections.emptySet(),
            Collections.emptyList()
        );
    }

    @Test
    @DisplayName("mapToDTO - Mapeo correcto de Product a ProductDTO")
    void mapToDTO_ShouldMapCorrectly() {
        ProductDTO result = productMapper.mapToDTO(product);
        assertNotNull(result);
        assertEquals(product.getGtin(), result.getGtin());
        assertEquals(product.getTextoCorto(), result.getTextoCorto());
    }

    @Test
    @DisplayName("mapToEntity - Mapeo correcto de ProductDTO a Product")
    void mapToEntity_ShouldMapCorrectly() {
        Product result = productMapper.mapToEntity(productDTO);
        assertNotNull(result);
        assertEquals(productDTO.getGtin(), result.getGtin());
        assertEquals(productDTO.getTextoCorto(), result.getTextoCorto());
    }

    @Test
    @DisplayName("updateEntity - Actualización correcta de entidad")
    void updateEntity_ShouldUpdateCorrectly() {
        Product source = new Product();
        source.setTextoCorto("Nueva descripción");
        
        productMapper.updateEntity(product, source);
        assertEquals("Nueva descripción", product.getTextoCorto());
    }

    @Test
    @DisplayName("updateEntityFromDTO - Actualización desde DTO")
    void updateEntityFromDTO_ShouldUpdateCorrectly() {
        ProductDTO source = new ProductDTO();
        source.setTextoCorto("Nueva descripción desde DTO");
        
        productMapper.updateEntityFromDTO(product, source);
        assertEquals("Nueva descripción desde DTO", product.getTextoCorto());
    }
}