package com.uma.wanchinchun.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceFactoryTest {

    @Mock
    private IProductService defaultService;

    @Mock
    private IProductService otherService;

    private ProductServiceFactory productServiceFactory;

    @BeforeEach
    void setUp() {
        productServiceFactory = new ProductServiceFactory(
            Map.of(
                "productService", defaultService,
                "otherService", otherService
            )
        );
    }

    @Test
    @DisplayName("getService - Tipo null retorna servicio por defecto")
    void getService_NullType_ReturnsDefaultService() {
        IProductService result = productServiceFactory.getService(null);
        assertSame(defaultService, result);
    }

    @Test
    @DisplayName("getService - Tipo desconocido retorna servicio por defecto")
    void getService_UnknownType_ReturnsDefaultService() {
        IProductService result = productServiceFactory.getService("unknown");
        assertSame(defaultService, result);
    }

    @Test
    @DisplayName("getService - Tipo conocido retorna servicio específico")
    void getService_KnownType_ReturnsSpecificService() {
        IProductService result = productServiceFactory.getService("otherService");
        assertSame(otherService, result);
    }
}