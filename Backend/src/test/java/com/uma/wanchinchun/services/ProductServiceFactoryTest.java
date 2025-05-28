package com.uma.wanchinchun.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Product Service Factory - Test")
class ProductServiceFactoryTest {

    private ProductServiceFactory sut;
    @Mock
    private IProductService defaultService;
    @Mock
    private IProductService serviceV2;

    @BeforeEach
    void setUp() {
        Map<String, IProductService> services = new HashMap<>();
        services.put("productService", defaultService);
        services.put("productServiceV2", serviceV2);

        sut = new ProductServiceFactory(services);
    }

    @Test
    @DisplayName("")
    void getService_whenNullArgument_returnsDefaultService() {
        // Arrange
        String serviceType = null;

        // Act
        IProductService productService = sut.getService(serviceType);

        // Assert
        assertSame(defaultService, productService);
    }

    @Test
    @DisplayName("")
    void getService_whenMatchingService_returnsMatchingService() {
        // Arrange
        String serviceType = "productServiceV2";

        // Act
        IProductService productService = sut.getService(serviceType);

        // Assert
        assertSame(serviceV2, productService);
    }
}