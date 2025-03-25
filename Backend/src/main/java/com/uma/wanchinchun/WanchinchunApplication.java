package com.uma.wanchinchun;

import com.uma.wanchinchun.models.Category;
import com.uma.wanchinchun.models.Product;
import com.uma.wanchinchun.repositories.CategoryRepository;
import com.uma.wanchinchun.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class WanchinchunApplication {

    public static void main(String[] args) {
        SpringApplication.run(WanchinchunApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(ProductRepository productRepository, 
                                    CategoryRepository categoryRepository) {
        return args -> {
            System.out.println("=== Generando esquema DDL y datos de prueba ===");
            
            // 1. Crear categorías
            Category electronica = new Category();
            electronica.setNombre("Electrónica");
            
            Category computacion = new Category();
            computacion.setNombre("Computación");
            
            categoryRepository.save(electronica);
            categoryRepository.save(computacion);

            // 2. Crear productos
            Product laptop = new Product();
            laptop.setNombre("Laptop HP EliteBook");
            laptop.setGtin("1234567890123");
            laptop.setSku("LP-HP-001");
            laptop.setTextoCorto("Portátil profesional");
            laptop.setCreado(new Date());
            
            Product mouse = new Product();
            mouse.setNombre("Mouse inalámbrico");
            mouse.setGtin("9876543210987");
            mouse.setSku("MS-WL-001");
            mouse.setTextoCorto("Mouse ergonómico");
            mouse.setCreado(new Date());

            // 3. Establecer relaciones ManyToMany
            laptop.getCategorias().add(electronica);
            laptop.getCategorias().add(computacion);
            mouse.getCategorias().add(electronica);

            // 4. Guardar productos (las categorías se actualizan por cascade)
            productRepository.save(laptop);
            productRepository.save(mouse);

            System.out.println("=== Datos iniciales creados correctamente ===");
            System.out.println("=== Consulta los logs para ver el DDL generado ===");
        };
    }
}