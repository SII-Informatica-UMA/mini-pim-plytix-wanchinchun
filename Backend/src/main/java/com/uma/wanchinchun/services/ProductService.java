package com.uma.wanchinchun.services;

import com.uma.wanchinchun.dtos.ProductReadDTO;
import com.uma.wanchinchun.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductReadDTO> getAllProducts(Integer idProducto, Integer idCuenta, Integer idCategoria, String gtin) {
        return null;
    }

    @Override
    public boolean belongsToAccountWithAccess(Integer idProducto, Integer idCategoria) {
        return false;
    }
}
