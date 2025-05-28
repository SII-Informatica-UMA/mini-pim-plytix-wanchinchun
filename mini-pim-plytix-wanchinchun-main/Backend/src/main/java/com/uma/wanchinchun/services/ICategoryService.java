package com.uma.wanchinchun.services;

import com.uma.wanchinchun.dtos.CategoryDTO;
import java.util.List;

public interface ICategoryService {
    List<CategoryDTO> getAllCategories();
    CategoryDTO getCategoryById(Long id);
    CategoryDTO createCategory(CategoryDTO categoryDTO);
    CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO);
    void deleteCategory(Long id);
    List<CategoryDTO> getCategoriesByProductId(Long productId);
    void addProductToCategory(Long categoryId, Long productId);
    void removeProductFromCategory(Long categoryId, Long productId);
}