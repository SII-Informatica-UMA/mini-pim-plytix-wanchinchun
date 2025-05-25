package com.uma.wanchinchun.services;

import com.uma.wanchinchun.dtos.CategoryDTO;
import com.uma.wanchinchun.models.Category;
import com.uma.wanchinchun.models.Product;
import com.uma.wanchinchun.repositories.CategoryRepository;
import com.uma.wanchinchun.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        return category != null ? convertToDTO(category) : null;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setNombre(categoryDTO.getNombre());
        Category savedCategory = categoryRepository.save(category);
        return convertToDTO(savedCategory);
    }

    @Override
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        return categoryRepository.findById(id)
                .map(existingCategory -> {
                    existingCategory.setNombre(categoryDTO.getNombre());
                    Category updatedCategory = categoryRepository.save(existingCategory);
                    return convertToDTO(updatedCategory);
                })
                .orElse(null);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.findById(id).ifPresent(category -> {
            if (category.getProductos().isEmpty()) {
                categoryRepository.delete(category);
            }
        });
    }

    @Override
    public List<CategoryDTO> getCategoriesByProductId(Long productId) {
        return categoryRepository.findByProductosId(productId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void addProductToCategory(Long categoryId, Long productId) {
        categoryRepository.findById(categoryId).ifPresent(category -> {
            productRepository.findById(productId).ifPresent(product -> {
                category.getProductos().add(product);
                product.getCategorias().add(category);
                categoryRepository.save(category);
                productRepository.save(product);
            });
        });
    }

    @Override
    @Transactional
    public void removeProductFromCategory(Long categoryId, Long productId) {
        categoryRepository.findById(categoryId).ifPresent(category -> {
            productRepository.findById(productId).ifPresent(product -> {
                category.getProductos().remove(product);
                product.getCategorias().remove(category);
                categoryRepository.save(category);
                productRepository.save(product);
            });
        });
    }

    private CategoryDTO convertToDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setNombre(category.getNombre());
        dto.setProductosIds(
            category.getProductos().stream()
                .map(Product::getId)
                .collect(Collectors.toSet())
        );
        return dto;
    }
}