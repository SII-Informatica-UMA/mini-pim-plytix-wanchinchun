package com.uma.wanchinchun.repositories;

import com.uma.wanchinchun.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT DISTINCT c FROM Category c JOIN c.productos p WHERE p.id = :productId")
    List<Category> findByProductosId(@Param("productId") Long productId);

}