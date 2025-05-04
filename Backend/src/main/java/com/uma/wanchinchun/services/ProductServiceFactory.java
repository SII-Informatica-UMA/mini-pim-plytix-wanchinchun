package com.uma.wanchinchun.services;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ProductServiceFactory {

    private final Map<String, IProductService> services;

    public ProductServiceFactory(Map<String, IProductService> services) {
        this.services = services;
    }

    public IProductService getService(String serviceType) {
        return services.getOrDefault(serviceType, services.get("productService"));
    }
}
