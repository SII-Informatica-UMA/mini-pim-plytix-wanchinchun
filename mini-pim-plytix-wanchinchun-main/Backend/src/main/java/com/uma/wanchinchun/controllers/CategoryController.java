package com.uma.wanchinchun.controllers;

import com.uma.wanchinchun.dtos.CategoryDTO;
import com.uma.wanchinchun.services.ICategoryService;
import com.uma.wanchinchun.services.CategoryServiceFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoryController {

    private final ICategoryService categoryService;

    public CategoryController(CategoryServiceFactory categoryServiceFactory) {
        this.categoryService = categoryServiceFactory.getService(null);
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
        CategoryDTO category = categoryService.getCategoryById(id);
        return category != null ? ResponseEntity.ok(category) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO) {
        CategoryDTO createdCategory = categoryService.createCategory(categoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO updatedCategory = categoryService.updateCategory(id, categoryDTO);
        return updatedCategory != null ? ResponseEntity.ok(updatedCategory) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/producto/{productId}")
    public ResponseEntity<List<CategoryDTO>> getCategoriesByProductId(@PathVariable Long productId) {
        List<CategoryDTO> categories = categoryService.getCategoriesByProductId(productId);
        return ResponseEntity.ok(categories);
    }

    @PostMapping("/{categoryId}/productos/{productId}")
    public ResponseEntity<Void> addProductToCategory(
            @PathVariable Long categoryId,
            @PathVariable Long productId) {
        categoryService.addProductToCategory(categoryId, productId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{categoryId}/productos/{productId}")
    public ResponseEntity<Void> removeProductFromCategory(
            @PathVariable Long categoryId,
            @PathVariable Long productId) {
        categoryService.removeProductFromCategory(categoryId, productId);
        return ResponseEntity.noContent().build();
    }
}