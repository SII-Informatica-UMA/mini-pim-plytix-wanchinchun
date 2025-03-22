package com.uma.wanchinchun.repositories;

import com.uma.wanchinchun.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
