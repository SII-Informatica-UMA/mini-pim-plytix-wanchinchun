package com.uma.wanchinchun.controllers;

import com.uma.wanchinchun.dtos.ProductDTO;
import com.uma.wanchinchun.exceptions.ExceedsLimitException;
import com.uma.wanchinchun.exceptions.PIMException;
import com.uma.wanchinchun.exceptions.UnauthorizedAccessException;
import com.uma.wanchinchun.services.IProductService;
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

        // condición que se debe cumplir
        // (gtin && gtin >= 0) ||
        // (idProducto && idCuenta && idProducto >= 0 && idCuenta >= 0) ||
        // (idCategoria && idCuenta && idCategoria >= 0 && idCuenta >= 0)

        // morgan -> \
        // (!gtin || gtin < 0) &&
        // (!idProducto || !idCuenta || idProducto < 0 || idCuenta < 0) &&
        // (!idCategoria || !idCuenta || idCategoria < 0 || idCuenta < 0)
        if ((gtin == null || !gtin.isEmpty())
                && (idProducto == null || idCuenta == null || idProducto < 0 || idCuenta < 0)
                && (idCategoria == null || idCuenta == null || idCategoria < 0 || idCuenta < 0)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            List<ProductDTO> products = productService.getAll(idProducto, idCuenta, idCategoria, gtin);
            if (products == null || products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            return ResponseEntity.ok(products);
        } catch (UnauthorizedAccessException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<ProductDTO> create(@RequestParam Long idCuenta, @RequestBody ProductDTO productDTO) {

        if (idCuenta == null || idCuenta < 0 || productDTO == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            ProductDTO createdProduct = productService.create(idCuenta, productDTO);
            if (createdProduct == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            URI location = URI.create("/producto/?idProducto=" + createdProduct.getId());
            return ResponseEntity.created(location).body(productDTO);
        } catch (ExceedsLimitException | UnauthorizedAccessException | PIMException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{idProducto}")
    public ResponseEntity<ProductDTO> update(@PathVariable Long idProducto, @RequestBody ProductDTO productDTO) {

        if (idProducto == null || idProducto < 0 || productDTO == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            productService.update(idProducto, productDTO);
            return ResponseEntity.ok(productDTO);
        } catch (PIMException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (UnauthorizedAccessException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{idProducto}")
    public ResponseEntity<Void> delete(@PathVariable Long idProducto) {

        if (idProducto == null || idProducto < 0) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            productService.delete(idProducto);
            return ResponseEntity.ok().build();
        } catch (PIMException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (UnauthorizedAccessException  e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
