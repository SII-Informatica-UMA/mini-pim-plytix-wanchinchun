package com.uma.wanchinchun.controllers;

import com.uma.wanchinchun.dtos.ProductReadDTO;
import com.uma.wanchinchun.services.IProductService;
import com.uma.wanchinchun.services.ProductServiceFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/producto")
public class ProductController {

    private final IProductService productService;

    // solo quería aprender a usar Observer honestly
    public ProductController(ProductServiceFactory productServiceFactory) {
        this.productService = productServiceFactory.getService(null);
    }

    @GetMapping
    public ResponseEntity<List<ProductReadDTO>> getAllProducts(@RequestParam Integer idProducto, @RequestParam Integer idCuenta,
                                                               @RequestParam Integer idCategoria, @RequestParam String gtin) {
        if (idProducto == null || idCuenta == null || idCategoria == null || gtin == null ||
                (gtin != null && gtin.isEmpty())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        boolean hasAccess = productService.belongsToAccountWithAccess(idProducto, idCategoria);
        if (!hasAccess) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        List<ProductReadDTO> products = productService.getAllProducts(idProducto, idCuenta, idCategoria, gtin);
        if (products == null || products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(products);
    }

}
