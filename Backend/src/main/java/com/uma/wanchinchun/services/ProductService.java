package com.uma.wanchinchun.services;

import com.uma.wanchinchun.dtos.ProductDTO;
import com.uma.wanchinchun.exceptions.ExceedsLimitException;
import com.uma.wanchinchun.exceptions.PIMException;
import com.uma.wanchinchun.exceptions.UnauthorizedAccessException;
import com.uma.wanchinchun.mappers.IMapper;
import com.uma.wanchinchun.models.Product;
import com.uma.wanchinchun.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService {

    @Qualifier("accessManagerService")
    private final IAccessManagerService accessManagerService;
    private final ProductRepository productRepository;
    @Qualifier("productMapper")
    private final IMapper<Product, ProductDTO> mapper;

    public ProductService(IAccessManagerService accessManagerService,
                          ProductRepository productRepository,
                          IMapper<Product, ProductDTO> mapper) {
        this.accessManagerService = accessManagerService;
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    @Override
    public List<ProductDTO> getAll(Long idProducto, Long idCuenta, Long idCategoria, String gtin) {

        if (gtin != null && !gtin.isEmpty()) {
            Optional<Product> productFromDB = productRepository.findByGtin(gtin);
            if (productFromDB.isPresent()) {
                ProductDTO dto = mapper.mapToDTO(productFromDB.get());
                return List.of(dto);
            }
        }

        boolean hasAccess = accessManagerService.hasAccessToAccount(idCuenta);
        if (!hasAccess) {
            throw new UnauthorizedAccessException("User has not permission to access this data.");
        }

        if (idProducto != null) {
            Optional<Product> productFromDB = productRepository.findById(idProducto);
            if (productFromDB.isPresent()) {
                ProductDTO dto = mapper.mapToDTO(productFromDB.get());
                return List.of(dto);
            }
        }

        if (idCategoria != null) {
            List<Product> products = productRepository.findByCategoriasId(idCategoria);
            if (products != null && !products.isEmpty()) {
                return products.stream()
                        .map(p -> mapper.mapToDTO(p))
                        .toList();
            }
        }

        return List.of();
    }

    @Override
    public ProductDTO create(Long idCuenta, ProductDTO productDTO) {

        if (!accessManagerService.hasAccessToAccount(idCuenta)) {
            throw new UnauthorizedAccessException("User has no access to this account.");
        }

        if (accessManagerService.exceedsLimit(idCuenta)) {
            throw new ExceedsLimitException("The account is already at its limit of products.");
        }

        if (existsGtin(productDTO.getGtin())) {
            throw new PIMException("There is another product with the same GTIN.");
        }

        Product productToSave = mapper.mapToEntity(productDTO);
        Product createdProduct = productRepository.save(productToSave);

        return mapper.mapToDTO(createdProduct);
    }

    @Override
    public ProductDTO update(Long idProducto, ProductDTO productDTO) {

        Optional<Product> productSameGtin = productRepository.findByGtin(productDTO.getGtin());
        if (productSameGtin.isPresent()) {
            throw new UnauthorizedAccessException("There is another product with the same GTIN");
        }

        Optional<Product> productFromDB = productRepository.findById(idProducto);
        if (productFromDB.isEmpty()) {
            throw new PIMException("There is no product with that ID");
        }

        Product productToUpdate = productFromDB.get();
        boolean hasAccess = accessManagerService.hasAccessToAccount(productToUpdate.getIdCuenta());
        if (!hasAccess) {
            throw new UnauthorizedAccessException("User has no access to this account");
        }

        mapper.updateEntityFromDTO(productToUpdate, productDTO);
        productRepository.save(productToUpdate);

        return productDTO;
    }

    @Override
    public void delete(Long idProducto) {
        Optional<Product> product = productRepository.findById(idProducto);
        if (product.isEmpty()) {
            throw new PIMException("There is no product with that ID");
        }

        boolean hasAccess = accessManagerService.hasAccessToAccount(product.get().getIdCuenta());
        if (!hasAccess) {
            throw new UnauthorizedAccessException("User has no access to this account");
        }
        productRepository.deleteById(idProducto);
    }

    private boolean existsGtin(String gtin) {
        return productRepository.findByGtin(gtin).isPresent();
    }
}
