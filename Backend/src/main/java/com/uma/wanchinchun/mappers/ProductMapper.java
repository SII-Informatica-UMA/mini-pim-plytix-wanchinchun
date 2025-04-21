package com.uma.wanchinchun.mappers;

import com.uma.wanchinchun.dtos.ProductDTO;
import com.uma.wanchinchun.models.Product;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class ProductMapper implements IMapper<Product, ProductDTO> {
    @Override
    public ProductDTO mapToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        //dto.setId(product.getId());
        dto.setGtin(product.getGtin());
        dto.setSku(product.getSku());
        dto.setNombre(product.getNombre());
        dto.setTextoCorto(product.getTextoCorto());
        dto.setCreado(product.getCreado());
        dto.setModificado(product.getModificado());
        dto.setMiniatura(product.getMiniatura());
        dto.setAtributos(product.getAttributes());
        dto.setCategorias(product.getCategorias());
        dto.setRelaciones(product.getRelacionesOrigen());
        dto.setRelaciones(product.getRelacionesDestino()); // FIX

        return dto;
    }

    @Override
    public Product mapToEntity(ProductDTO dto) {
        Product product = new Product();
        //product.setId(dto.getId());
        product.setGtin(dto.getGtin());
        product.setSku(dto.getSku());
        product.setNombre(dto.getNombre());
        product.setTextoCorto(dto.getTextoCorto());
        product.setCreado(dto.getCreado());
        product.setModificado(dto.getModificado());
        product.setMiniatura(dto.getMiniatura());
        product.setAttributes(dto.getAtributos());
        product.setCategorias(dto.getCategorias());
        product.setRelacionesOrigen(dto.getRelaciones());
        product.setRelacionesDestino(dto.getRelaciones()); // FIX

        return product;
    }

    @Override
    public void updateEntity(Product toUpdate, Product source) {
        toUpdate.setGtin(source.getGtin());
        toUpdate.setSku(source.getSku());
        toUpdate.setNombre(source.getNombre());
        toUpdate.setTextoCorto(source.getTextoCorto());
        toUpdate.setCreado(source.getCreado());
        toUpdate.setModificado(source.getModificado());
        toUpdate.setMiniatura(source.getMiniatura());
        toUpdate.setAttributes(source.getAttributes());
        toUpdate.setCategorias(source.getCategorias());
        toUpdate.setRelacionesOrigen(source.getRelacionesOrigen());
        toUpdate.setRelacionesDestino(source.getRelacionesDestino());
    }

    @Override
    public void updateEntityFromDTO(Product toUpdate, ProductDTO source) {
        updateEntity(toUpdate, mapToEntity(source));
    }
}
