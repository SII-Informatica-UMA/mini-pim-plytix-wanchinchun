package com.uma.wanchinchun.dtos;

import com.uma.wanchinchun.models.Attribute;
import com.uma.wanchinchun.models.Category;
import com.uma.wanchinchun.models.ProductRelationship;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ProductDTOTest {

    @Test
    @DisplayName("ProductDTO - Debe crear una instancia correctamente con el constructor vacío")
    void constructorVacio_CreaInstanciaCorrectamente() {
        // Act
        ProductDTO product = new ProductDTO();

        // Assert
        assertNotNull(product);
    }

    @Test
    @DisplayName("ProductDTO - Debe crear una instancia correctamente con el constructor con parámetros")
    void constructorConParametros_CreaInstanciaCorrectamente() {
        // Arrange
        OffsetDateTime now = OffsetDateTime.now();
        List<Attribute> attributes = Collections.emptyList();
        Set<Category> categories = Collections.emptySet();
        List<ProductRelationship> relationships = Collections.emptyList();

        // Act
        ProductDTO product = new ProductDTO(
                1L, "1234567890123", "SKU123", "Producto de prueba",
                "Descripción corta", now, now, "thumbnail.jpg",
                attributes, categories, relationships
        );

        // Assert
        assertNotNull(product);
        assertEquals(1L, product.getId());
        assertEquals("1234567890123", product.getGtin());
        assertEquals("SKU123", product.getSku());
        assertEquals("Producto de prueba", product.getNombre());
        assertEquals("Descripción corta", product.getTextoCorto());
        assertEquals(now, product.getCreado());
        assertEquals(now, product.getModificado());
        assertEquals("thumbnail.jpg", product.getMiniatura());
        assertEquals(attributes, product.getAtributos());
        assertEquals(categories, product.getCategorias());
        assertEquals(relationships, product.getRelaciones());
    }

    @Test
    @DisplayName("ProductDTO - Debe manejar correctamente los getters y setters")
    void gettersYSetters_FuncionanCorrectamente() {
        // Arrange
        ProductDTO product = new ProductDTO();
        OffsetDateTime now = OffsetDateTime.now();
        List<Attribute> attributes = Collections.emptyList();
        Set<Category> categories = Collections.emptySet();
        List<ProductRelationship> relationships = Collections.emptyList();

        // Act
        product.setId(1L);
        product.setGtin("1234567890123");
        product.setSku("SKU123");
        product.setNombre("Producto de prueba");
        product.setTextoCorto("Descripción corta");
        product.setCreado(now);
        product.setModificado(now);
        product.setMiniatura("thumbnail.jpg");
        product.setAtributos(attributes);
        product.setCategorias(categories);
        product.setRelaciones(relationships);

        // Assert
        assertEquals(1L, product.getId());
        assertEquals("1234567890123", product.getGtin());
        assertEquals("SKU123", product.getSku());
        assertEquals("Producto de prueba", product.getNombre());
        assertEquals("Descripción corta", product.getTextoCorto());
        assertEquals(now, product.getCreado());
        assertEquals(now, product.getModificado());
        assertEquals("thumbnail.jpg", product.getMiniatura());
        assertEquals(attributes, product.getAtributos());
        assertEquals(categories, product.getCategorias());
        assertEquals(relationships, product.getRelaciones());
    }

    @Test
    @DisplayName("ProductDTO - equals() debe devolver true para objetos iguales")
    void equals_ObjetosIguales_DevuelveTrue() {
        // Arrange
        OffsetDateTime now = OffsetDateTime.now();
        ProductDTO product1 = new ProductDTO(
                1L, "1234567890123", "SKU123", "Producto",
                "Desc", now, now, "thumb.jpg",
                Collections.emptyList(), Collections.emptySet(), Collections.emptyList()
        );
        ProductDTO product2 = new ProductDTO(
                1L, "1234567890123", "SKU123", "Producto",
                "Desc", now, now, "thumb.jpg",
                Collections.emptyList(), Collections.emptySet(), Collections.emptyList()
        );

        // Act & Assert
        assertEquals(product1, product2);
    }

    @Test
    @DisplayName("ProductDTO - equals() debe devolver false para objetos diferentes")
    void equals_ObjetosDiferentes_DevuelveFalse() {
        // Arrange
        OffsetDateTime now = OffsetDateTime.now();
        ProductDTO product1 = new ProductDTO(
                1L, "1234567890123", "SKU123", "Producto",
                "Desc", now, now, "thumb.jpg",
                Collections.emptyList(), Collections.emptySet(), Collections.emptyList()
        );
        ProductDTO product2 = new ProductDTO(
                2L, "9876543210987", "SKU987", "Otro Producto",
                "Otra Desc", now, now, "other.jpg",
                Collections.emptyList(), Collections.emptySet(), Collections.emptyList()
        );

        // Act & Assert
        assertNotEquals(product1, product2);
    }

    @Test
    @DisplayName("ProductDTO - hashCode() debe ser consistente")
    void hashCode_EsConsistente() {
        // Arrange
        OffsetDateTime now = OffsetDateTime.now();
        ProductDTO product = new ProductDTO(
                1L, "1234567890123", "SKU123", "Producto",
                "Desc", now, now, "thumb.jpg",
                Collections.emptyList(), Collections.emptySet(), Collections.emptyList()
        );
        int expectedHash = product.hashCode();

        // Act & Assert
        assertEquals(expectedHash, product.hashCode());
    }

    @Test
    @DisplayName("ProductDTO - hashCode() debe ser igual para objetos iguales")
    void hashCode_ObjetosIguales_MismoHashCode() {
        // Arrange
        OffsetDateTime now = OffsetDateTime.now();
        ProductDTO product1 = new ProductDTO(
                1L, "1234567890123", "SKU123", "Producto",
                "Desc", now, now, "thumb.jpg",
                Collections.emptyList(), Collections.emptySet(), Collections.emptyList()
        );
        ProductDTO product2 = new ProductDTO(
                1L, "1234567890123", "SKU123", "Producto",
                "Desc", now, now, "thumb.jpg",
                Collections.emptyList(), Collections.emptySet(), Collections.emptyList()
        );

        // Act & Assert
        assertEquals(product1.hashCode(), product2.hashCode());
    }
}