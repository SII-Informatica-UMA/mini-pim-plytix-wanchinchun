package com.uma.wanchinchun.controllers;

import com.uma.wanchinchun.dtos.ProductDTO;
import com.uma.wanchinchun.services.IProductService;
import com.uma.wanchinchun.services.ProductService;
import com.uma.wanchinchun.services.ProductServiceFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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
    public ResponseEntity<List<ProductDTO>> getAll(@RequestParam Long idProducto, @RequestParam Long idCuenta,
                                                           @RequestParam Long idCategoria, @RequestParam String gtin) {

        if (idProducto == null && idCuenta == null && idCategoria == null && (gtin == null || gtin.isEmpty())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        //boolean hasAccess = productService.hasAccessToAccount(idCuenta);
        //if (!hasAccess) {
        //    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        //}

        List<ProductDTO> products = productService.getAll(idProducto, idCuenta, idCategoria, gtin);
        if (products == null || products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(products);
    }

    @PostMapping
    public ResponseEntity<ProductDTO> create(@RequestParam Long idCuenta, @RequestBody ProductDTO productDTO) {

        if (idCuenta == null || productDTO == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        boolean exceedsLimit = productService.exceedsLimit(idCuenta);
        if (exceedsLimit) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        ProductDTO createdProduct = productService.create(idCuenta, productDTO);
        if (createdProduct == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        URI location = URI.create("/producto/?idProducto=" + createdProduct.getId());

        return ResponseEntity.created(location).body(productDTO);
    }

    @PutMapping("/{idProducto}")
    public ResponseEntity<ProductDTO> update(@PathVariable Long idProducto, @RequestBody ProductDTO productDTO) {

        return null;
    }

    @DeleteMapping("/{idProducto}")
    public ResponseEntity<Void> delete(@PathVariable Long idProducto) {

        return null;
    }
}
