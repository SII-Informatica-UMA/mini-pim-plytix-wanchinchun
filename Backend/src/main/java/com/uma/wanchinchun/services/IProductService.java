package com.uma.wanchinchun.services;

import com.uma.wanchinchun.dtos.ProductDTO;

import java.util.List;

public interface IProductService {
    List<ProductDTO> getAll(Long idProducto, Long idCuenta, Long idCategoria, String gtin);
    ProductDTO create(Long idCuenta, ProductDTO productDTO);
    ProductDTO update(Long idProducto, ProductDTO productDTO);
    void delete(Long idProducto);
    boolean hasAccessToAccount(Long idCuenta);
    boolean exceedsLimit(Long idCuenta);
}
