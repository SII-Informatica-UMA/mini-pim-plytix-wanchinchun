package com.uma.wanchinchun.services;

import com.uma.wanchinchun.dtos.ProductDTO;

import java.util.List;

public interface IProductService {
    List<ProductDTO> getAll(String jwt, Long idProducto, Long idCuenta, Long idCategoria, String gtin);
    ProductDTO create(String jwt, Long idCuenta, ProductDTO productDTO);
    ProductDTO update(String jwt, Long idProducto, ProductDTO productDTO);
    void delete(String jwt, Long idProducto);
}
