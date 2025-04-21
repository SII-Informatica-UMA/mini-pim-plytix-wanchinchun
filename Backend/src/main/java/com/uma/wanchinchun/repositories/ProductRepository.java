package com.uma.wanchinchun.repositories;

import com.uma.wanchinchun.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByGtin(String gtin);
    List<Product> findByCategoriasId(Long categoriaId);
    List<Product> findByCategoriasNombre(String nombreCategoria);
    @Query("SELECT COUNT(prods) FROM Productos p WHERE p.idCuenta = :idCuenta")
    Integer countProductsOwnedByAccount(@Param("idCuenta") Long idCuenta);
}
