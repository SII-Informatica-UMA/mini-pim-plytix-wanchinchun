package com.uma.wanchinchun.services;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CategoryServiceFactory {
    private final Map<String, ICategoryService> services;

    public CategoryServiceFactory(Map<String, ICategoryService> services) {
        this.services = services;
    }

    public ICategoryService getService(String serviceType) {
        return services.getOrDefault(serviceType, services.get("categoryService"));
    }
}