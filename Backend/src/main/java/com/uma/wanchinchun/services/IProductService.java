package com.uma.wanchinchun.services;

import com.uma.wanchinchun.dtos.ProductReadDTO;

import java.util.List;

public interface IProductService {
    List<ProductReadDTO> getAllProducts(Integer idProducto, Integer idCuenta, Integer idCategoria, String gtin);
    boolean belongsToAccountWithAccess(Integer idProducto, Integer idCategoria);
}
