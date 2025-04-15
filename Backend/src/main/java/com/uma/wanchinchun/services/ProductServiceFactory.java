package com.uma.wanchinchun.services;

import java.util.Map;

public class ProductServiceFactory {

    private final Map<String, IProductService> services;

    public ProductServiceFactory(Map<String, IProductService> services) {
        this.services = services;
    }

    public IProductService getService(String serviceType) {
        return services.getOrDefault(serviceType, services.get("productService"));
    }
}
