package com.uma.wanchinchun.services;

import com.uma.wanchinchun.dtos.ProductDTO;
import com.uma.wanchinchun.exceptions.ExceedsLimitException;
import com.uma.wanchinchun.exceptions.PIMException;
import com.uma.wanchinchun.exceptions.UnauthorizedAccessException;
import com.uma.wanchinchun.mappers.IMapper;
import com.uma.wanchinchun.models.Category;
import com.uma.wanchinchun.models.Product;
import com.uma.wanchinchun.repositories.ProductRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@DisplayName("Product Service - Tests")
class ProductServiceTest {
    @Mock
    ProductRepository productRepository;
    @Mock
    IAccessManagerService accessManagerService;
    @Mock
    IMapper<Product, ProductDTO> mapper;
    IProductService sut;

    static String JWT;
    static Long ID_CUENTA;

    @BeforeAll
    static void setUpAll() {
        JWT = "jwt";
        ID_CUENTA = 1L;
    }

    @BeforeEach
    void setUp() {
        sut = new ProductService(accessManagerService, productRepository, mapper);
    }

    @Nested
    @DisplayName("Get All Products - Tests")
    class GetAllProducts {

        @Test
        @DisplayName("Get all products with no access to account -> Should throw exception")
        void getAllProducts_butNoAccess_throwsException() {
            // Arrange
            when(accessManagerService.hasAccessToAccount(JWT, ID_CUENTA)).thenReturn(false);

            // Act & Assert
            assertThrows(UnauthorizedAccessException.class, () ->
                    sut.getAll(JWT, null, ID_CUENTA, null, null)
            );
        }

        @Test
        @DisplayName("Get all products with access but no GTIN -> Should return empty list")
        void getAllProducts_withAccessButNullGtin_returnsEmptyList() {
            // Arrange
            String gtin = null;

            when(accessManagerService.hasAccessToAccount(JWT, ID_CUENTA)).thenReturn(true);

            // Act
            List<ProductDTO> resultList = sut.getAll(JWT, null, ID_CUENTA, null, gtin);

            // Assert
            assertTrue(resultList.isEmpty());
        }

        @Test
        @DisplayName("Get all products with access but empty GTIN -> Should return empty list")
        void getAllProducts_withAccessButEmptyGtin_returnsEmptyList() {
            // Arrange
            String gtin = "";

            when(accessManagerService.hasAccessToAccount(JWT, ID_CUENTA)).thenReturn(true);

            // Act
            List<ProductDTO> resultList = sut.getAll(JWT, null, ID_CUENTA, null, gtin);

            // Assert
            assertTrue(resultList.isEmpty());
        }

        @Test
        @DisplayName("Get all products by gtin with access but no product matches -> Should return empty list")
        void getAllProducts_byGtinWithAccessButNoProduct_returnsSingletonList() {
            // Arrange
            String gtin = "gtin";
            Product product = new Product();
            product.setGtin(gtin);

            when(accessManagerService.hasAccessToAccount(JWT, ID_CUENTA)).thenReturn(true);
            when(productRepository.findByGtin(gtin)).thenReturn(Optional.empty());

            // Act
            List<ProductDTO> resultList = sut.getAll(JWT, null, ID_CUENTA, null, gtin);

            // Assert
            assertTrue(resultList.isEmpty());
        }

        @Test
        @DisplayName("Get all products by gtin with access -> Should return singleton list")
        void getAllProducts_byGtinWithAccess_returnsSingletonList() {
            // Arrange
            String gtin = "gtin";
            Product product = new Product();
            product.setGtin(gtin);
            ProductDTO productDTO = new ProductDTO();
            productDTO.setGtin(product.getGtin());

            when(productRepository.findByGtin(gtin)).thenReturn(Optional.of(product));
            when(mapper.mapToDTO(product)).thenReturn(productDTO);

            // Act
            List<ProductDTO> resultList = sut.getAll(JWT, null, ID_CUENTA, null, gtin);

            // Assert
            assertEquals(product.getGtin(), resultList.getFirst().getGtin());
            assertEquals(1, resultList.size());
        }

        @Test
        @DisplayName("Get all products by id with access but no product matches -> Should return empty list")
        void getAllProducts_ByIdProductButNoProductMatches_returnsEmptyList_() {
            // Arrange
            Long idProducto = 1L;

            when(accessManagerService.hasAccessToAccount(JWT, ID_CUENTA)).thenReturn(true);
            when(productRepository.findById(idProducto)).thenReturn(Optional.empty());

            // Act
            List<ProductDTO> resultList = sut.getAll(JWT, idProducto, ID_CUENTA, null, null);

            // Assert
            assertTrue(resultList.isEmpty());
        }

        @Test
        @DisplayName("Get all products by id with access -> Should return singleton list")
        void getAllProducts_ByIdProduct_returnsSingletonList() {
            // Arrange
            Long idProducto = 1L;
            Product product = new Product();
            product.setId(idProducto);
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(product.getId());

            when(accessManagerService.hasAccessToAccount(JWT, ID_CUENTA)).thenReturn(true);
            when(productRepository.findById(idProducto)).thenReturn(Optional.of(product));
            when(mapper.mapToDTO(product)).thenReturn(productDTO);

            // Act
            List<ProductDTO> resultList = sut.getAll(JWT, idProducto, ID_CUENTA, null, null);

            // Assert
            assertEquals(product.getId(), resultList.getFirst().getId());
            assertEquals(1, resultList.size());
        }

        @Test
        @DisplayName("Get all products by categoryId with access but no products in that category -> Should return empty list")
        void getAllProducts_ByCategoryIdButNoProducts_returnsEmptyList() {
            // Arrange
            Long categoryId = 1L;

            when(accessManagerService.hasAccessToAccount(JWT, ID_CUENTA)).thenReturn(true);
            when(productRepository.findByCategoriasId(categoryId)).thenReturn(List.of());

            // Act
            List<ProductDTO> resultList = sut.getAll(JWT, null, ID_CUENTA, categoryId, null);

            // Assert
            assertTrue(resultList.isEmpty());
        }

        @Test
        @DisplayName("Get all products by categoryId with access but repository returns null -> Should return empty list")
        void getAllProducts_ByCategoryIdButRepoReturnsNull_returnsEmptyList() {
            // Arrange
            Long categoryId = 1L;

            when(accessManagerService.hasAccessToAccount(JWT, ID_CUENTA)).thenReturn(true);
            when(productRepository.findByCategoriasId(categoryId)).thenReturn(null);

            // Act
            List<ProductDTO> resultList = sut.getAll(JWT, null, ID_CUENTA, categoryId, null);

            // Assert
            assertTrue(resultList.isEmpty());
        }

        @Test
        @DisplayName("Get all products by categoryId with access -> Should return list")
        void getAllProducts_ByCategoryId_returnsList() {
            // Arrange
            Category category = new Category();
            Long categoryId = 1L;
            category.setId(categoryId);
            Product product1 = new Product();
            product1.setId(1L);
            product1.setCategorias(Set.of(category));
            ProductDTO productDTO1 = new ProductDTO();
            productDTO1.setId(product1.getId());
            productDTO1.setCategorias(product1.getCategorias());
            Product product2 = new Product();
            product2.setId(2L);
            product2.setCategorias(Set.of(category));
            ProductDTO productDTO2 = new ProductDTO();
            productDTO1.setId(product2.getId());
            productDTO1.setCategorias(product2.getCategorias());

            when(accessManagerService.hasAccessToAccount(JWT, ID_CUENTA)).thenReturn(true);
            when(productRepository.findByCategoriasId(categoryId)).thenReturn(List.of(product1, product2));
            when(mapper.mapToDTO(product1)).thenReturn(productDTO1);
            when(mapper.mapToDTO(product2)).thenReturn(productDTO2);

            // Act
            List<ProductDTO> resultList = sut.getAll(JWT, null, ID_CUENTA, categoryId, null);

            // Assert
            assertEquals(2, resultList.size());
        }
    }

    @Nested
    @DisplayName("Create Product - Tests")
    class CreateProduct {

        @Test
        @DisplayName("Create product with no account access -> Should throw exception")
        void createProduct_butNoAccess_throwsException() {
            // Arrange
            ProductDTO productDTO = new ProductDTO();

            when(accessManagerService.hasAccessToAccount(JWT, ID_CUENTA)).thenReturn(false);

            // Act & Assert
            assertThrows(UnauthorizedAccessException.class, () ->
                    sut.create(JWT, ID_CUENTA, productDTO)
            );
        }

        @Test
        @DisplayName("Create product with access but exceeds account limit -> Should throw exception")
        void createProduct_withAccessButExceedsLimit_throwsException() {
            // Arrange
            ProductDTO productDTO = new ProductDTO();

            when(accessManagerService.hasAccessToAccount(JWT, ID_CUENTA)).thenReturn(true);
            when(accessManagerService.exceedsLimit(JWT, ID_CUENTA)).thenReturn(true);

            // Act & Assert
            assertThrows(ExceedsLimitException.class, () ->
                    sut.create(JWT, ID_CUENTA, productDTO)
            );
        }

        @Test
        @DisplayName("Create product with access and within limits but GTIN already exists -> Should throw exception")
        void createProduct_butGtinExists_throwsException() {
            // Arrange
            String gtin = "gtin";
            ProductDTO productDTO = new ProductDTO();
            productDTO.setGtin(gtin);

            when(accessManagerService.hasAccessToAccount(JWT, ID_CUENTA)).thenReturn(true);
            when(accessManagerService.exceedsLimit(JWT, ID_CUENTA)).thenReturn(false);
            when(productRepository.findByGtin(gtin)).thenReturn(Optional.of(new Product()));

            // Act & Assert
            assertThrows(PIMException.class, () ->
                    sut.create(JWT, ID_CUENTA, productDTO)
            );
        }

        @Test
        @DisplayName("Create product with all conditions met -> Succeeds")
        void createProduct_withAllConditionsMet_Succeeds() {
            // Arrange
            String gtin = "gtin";
            ProductDTO productDTO = new ProductDTO();
            productDTO.setGtin(gtin);
            Product product = new Product();

            when(accessManagerService.hasAccessToAccount(JWT, ID_CUENTA)).thenReturn(true);
            when(accessManagerService.exceedsLimit(JWT, ID_CUENTA)).thenReturn(false);
            when(productRepository.findByGtin(gtin)).thenReturn(Optional.empty());
            when(mapper.mapToEntity(productDTO)).thenReturn(product);
            when(productRepository.save(product)).thenReturn(product);
            when(mapper.mapToDTO(product)).thenReturn(productDTO);

            // Act
            ProductDTO resultDTO = sut.create(JWT, ID_CUENTA, productDTO);

            // Assert
            assertNotNull(resultDTO);
        }
    }

    @Nested
    @DisplayName("Update Product - Tests")
    class UpdateProduct {

        @Test
        @DisplayName("Update product with no access to account -> Should throw exception")
        void updateProduct_butNoAccess_throwsException() {
            // Arrange
            Long idProducto = 1L;
            String gtin = "gtin";
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(idProducto);
            productDTO.setGtin(gtin);
            Product product = new Product();
            product.setId(idProducto);
            product.setIdCuenta(ID_CUENTA);

            when(productRepository.findByGtin(gtin)).thenReturn(Optional.empty());
            when(productRepository.findById(idProducto)).thenReturn(Optional.of(product));
            when(accessManagerService.hasAccessToAccount(JWT, ID_CUENTA)).thenReturn(false);
            
            // Act & Assert
            assertThrows(UnauthorizedAccessException.class, () -> 
                    sut.update(JWT, idProducto, productDTO)
            );
        }
        
        @Test
        @DisplayName("Update product but Gtin exists -> Should throw exception")
        void updateProduct_butGtinExists_throwsException() {
            // Arrange
            Long idProducto = 1L;
            String gtin = "gtin";
            ProductDTO productDTO = new ProductDTO();
            productDTO.setGtin(gtin);

            when(productRepository.findByGtin(gtin)).thenReturn(Optional.of(new Product()));
            
            // Act & Assert
            assertThrows(UnauthorizedAccessException.class, () -> 
                    sut.update(JWT, idProducto, productDTO)
            );
        }

        @Test
        @DisplayName("Update product with access but product does not exist -> Should throw exception")
        void updateProduct_withAccessbutProductDoesNotExist_throwsException() {
            // Arrange
            Long idProducto = 1L;
            String gtin = "gtin";
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(idProducto);
            productDTO.setGtin(gtin);

            when(productRepository.findByGtin(gtin)).thenReturn(Optional.empty());
            when(productRepository.findById(idProducto)).thenReturn(Optional.empty());
            
            // Act & Assert
            assertThrows(PIMException.class, () -> 
                    sut.update(JWT, idProducto, productDTO)
            );
        }

        @Test
        @DisplayName("Update product with all conditions met -> Succeeds")
        void updateProduct_withAllConditionsMet_Succeeds() {
            // Arrange
            Long idProducto = 1L;
            String gtin = "gtin";
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(idProducto);
            productDTO.setGtin(gtin);
            Product product = new Product();
            product.setIdCuenta(ID_CUENTA);


            when(productRepository.findByGtin(gtin)).thenReturn(Optional.empty());
            when(productRepository.findById(idProducto)).thenReturn(Optional.of(product));
            when(accessManagerService.hasAccessToAccount(JWT, ID_CUENTA)).thenReturn(true);
            doNothing().when(mapper).updateEntityFromDTO(product, productDTO);
            when(productRepository.save(product)).thenReturn(product);
            
            // Act
            sut.update(JWT, idProducto, productDTO);

            // Assert
            assertNotNull(productDTO);
        }
    }

    @Nested
    @DisplayName("Delete Product - Tests")
    class DeleteProduct {

        @Test
        @DisplayName("Delete product with no access to account -> Should throw exception")
        void deleteProduct_withExistentIdButNoAccess_throwsException() {
            // Arrange
            Long idProducto = 1L;
            Product product = new Product();
            product.setId(idProducto);
            product.setGtin("gtin");
            product.setSku("sku");
            product.setIdCuenta(ID_CUENTA);

            when(productRepository.findById(idProducto)).thenReturn(Optional.of(product));
            when(accessManagerService.hasAccessToAccount(JWT, ID_CUENTA)).thenReturn(false);
            
            // Act & Assert
            assertThrows(UnauthorizedAccessException.class, () -> 
                    sut.delete(JWT, idProducto)
            );
        }

        @Test
        @DisplayName("Delete product with access but product does not exist -> Should throw exception")
        void deleteProduct_withAccessButProductDoesNotExist_throwsException() {
            // Arrange
            Long idProducto = 1L;

            when(productRepository.findById(idProducto)).thenReturn(Optional.empty());
            
            // Act & Assert
            assertThrows(PIMException.class, () -> 
                    sut.delete(JWT, idProducto)
            );
        }

        @Test
        @DisplayName("Delete product with access and product exists -> Succeeds")
        void deleteProduct_withAccessAndIdExists() {
            // Arrange
            Long idProducto = 1L;
            Product product = new Product();
            product.setId(idProducto);
            product.setGtin("gtin");
            product.setSku("sku");
            product.setIdCuenta(ID_CUENTA);

            when(productRepository.findById(idProducto)).thenReturn(Optional.of(product));
            when(accessManagerService.hasAccessToAccount(JWT, ID_CUENTA)).thenReturn(true);
            
            // Act
            sut.delete(JWT, idProducto);

            // Assert
            verify(productRepository).deleteById(idProducto);
        }
    }
}