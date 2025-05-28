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
    public ResponseEntity<List<ProductDTO>> getAll(@RequestParam Long idProducto,
                                                   @RequestParam Long idCuenta,
                                                   @RequestParam Long idCategoria,
                                                   @RequestParam String gtin,
                                                   @RequestHeader(value = "Authorization", required = false) String authorization) {

        String jwt;
        if (authorization != null && authorization.startsWith("Bearer ")) {
            jwt = authorization.substring(7);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if ((gtin == null || !gtin.isEmpty())
                && (idProducto == null || idCuenta == null || idProducto < 0 || idCuenta < 0)
                && (idCategoria == null || idCuenta == null || idCategoria < 0 || idCuenta < 0)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            List<ProductDTO> products = productService.getAll(jwt, idProducto, idCuenta, idCategoria, gtin);
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
    public ResponseEntity<ProductDTO> create(@RequestParam Long idCuenta, @RequestBody ProductDTO productDTO,
                                             @RequestHeader(value = "Authorization", required = false) String authorization) {

        String jwt;
        if (authorization != null && authorization.startsWith("Bearer ")) {
            jwt = authorization.substring(7);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (idCuenta == null || idCuenta < 0 || productDTO == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            ProductDTO createdProduct = productService.create(jwt, idCuenta, productDTO);
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
    public ResponseEntity<ProductDTO> update(@PathVariable Long idProducto, @RequestBody ProductDTO productDTO,
                                             @RequestHeader(value = "Authorization", required = false) String authorization) {

        String jwt;
        if (authorization != null && authorization.startsWith("Bearer ")) {
            jwt = authorization.substring(7);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (idProducto == null || idProducto < 0 || productDTO == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            productService.update(jwt, idProducto, productDTO);
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
    public ResponseEntity<Void> delete(@PathVariable Long idProducto,
                                       @RequestHeader(value = "Authorization", required = false) String authorization) {

        String jwt;
        if (authorization != null && authorization.startsWith("Bearer ")) {
            jwt = authorization.substring(7);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (idProducto == null || idProducto < 0) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            productService.delete(jwt, idProducto);
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
