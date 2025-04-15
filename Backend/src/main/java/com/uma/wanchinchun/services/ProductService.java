package com.uma.wanchinchun.services;

import com.uma.wanchinchun.dtos.ProductDTO;
import com.uma.wanchinchun.models.Product;
import com.uma.wanchinchun.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService {

    @Value("${microservice.users.baseurl}")
    private String baseUrl;
    private final RestTemplate restTemplate;
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository, RestTemplate restTemplate) {
        this.productRepository = productRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<ProductDTO> getAll(Long idProducto, Long idCuenta, Long idCategoria, String gtin) {

        if (gtin != null && !gtin.isEmpty()) {
            Optional<Product> productFromDB = productRepository.findByGtin(gtin);
            if (productFromDB.isPresent()) {
                ProductDTO dto = mapToProductDTO(productFromDB.get());
                return List.of(dto);
            }
        }

        boolean hasAccess = hasAccessToAccount(idCuenta);

        if (idProducto != null && hasAccess) {
            Optional<Product> productFromDB = productRepository.findById(idProducto);
            if (productFromDB.isPresent()) {
                ProductDTO dto = mapToProductDTO(productFromDB.get());
                return List.of(dto);
            }
        }

        if (idCategoria != null && hasAccess) {
            List<Product> products = productRepository.findByCategoriasId(idCategoria);
            if (products != null && !products.isEmpty()) {
                return products.stream()
                        .map(p -> mapToProductDTO(p))
                        .toList();
            }
        }

        return List.of();
    }

    @Override
    public ProductDTO create(Long idCuenta, ProductDTO productDTO) {
        return null;
    }

    @Override
    public ProductDTO update(Long idProducto, ProductDTO productDTO) {
        return null;
    }

    @Override
    public void delete(Long idProducto) {

    }

    @Override
    public boolean hasAccessToAccount(Long idCuenta) {
        if (idCuenta == null) {
            return false;
        }
        ResponseEntity<String> response = restTemplate.getForEntity(baseUrl + "/cuenta/" + idCuenta + "/propietario", String.class);
        HttpStatusCode statusCode = response.getStatusCode();

        return statusCode.is2xxSuccessful();
    }

    @Override
    public boolean exceedsLimit(Long idCuenta) {
        return false;
    }

    private ProductDTO mapToProductDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        // map
        return dto;
    }
}
