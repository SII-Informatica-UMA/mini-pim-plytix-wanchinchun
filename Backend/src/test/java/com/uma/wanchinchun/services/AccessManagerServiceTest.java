package com.uma.wanchinchun.services;

import com.uma.wanchinchun.dtos.AccountDTO;
import com.uma.wanchinchun.dtos.PlanDTO;
import com.uma.wanchinchun.repositories.ProductRepository;
import jdk.jfr.Description;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Access Manager Service - Tests")
class AccessManagerServiceTest {

    @Mock
    RestTemplate restTemplate;
    @Mock
    ProductRepository productRepository;
    AccessManagerService sut;

    static String JWT = "jwt_token";
    static Long ACCOUNT_ID = 1L;
    static Integer MAX_PRODUCTS = 4;

    @BeforeEach
    void setUp() {
        sut = new AccessManagerService(restTemplate, productRepository);
    }

    @Nested
    @DisplayName("Has Access To Account - Tests")
    class HasAccessToAccount {

        @Test
        @Description("Tries access when server returns 500 -> should throw exception")
        void access_whenServerError_throwsException() {
            // Arrange
            when(restTemplate.exchange(any(RequestEntity.class), eq(String.class)))
                    .thenThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

            // Act
            boolean hasAccess = sut.hasAccessToAccount(JWT, ACCOUNT_ID);

            // Assert
            assertFalse(hasAccess);
        }

        @Test
        @Description("Tries access when has no access -> should return false")
        void access_whenHasNotAccess_returnsFalse() {
            // Arrange
            var response = new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
            when(restTemplate.exchange(any(RequestEntity.class), eq(String.class))).thenReturn(response);

            // Act
            boolean hasAccess = sut.hasAccessToAccount(JWT, ACCOUNT_ID);

            // Assert
            assertFalse(hasAccess);
        }

        @Test
        @Description("Tries access when has access -> should return true")
        void access_whenHasAccess_returnsTrue() {
            // Arrange
            var response = new ResponseEntity<String>("OK", HttpStatus.OK);
            when(restTemplate.exchange(any(RequestEntity.class), eq(String.class)))
                    .thenReturn(response);

            // Act
            boolean hasAccess = sut.hasAccessToAccount(JWT, ACCOUNT_ID);

            // Assert
            assertTrue(hasAccess);
        }
    }

    @Nested
    @DisplayName("Exceeds Limit - Tests")
    class ExceedsLimit {

        @Test
        @Description("Checks limits but server returns 500 -> should throw exception")
        void checksLimit_whenServerError_throwsException() {
            // Arrange
            when(restTemplate.exchange(any(RequestEntity.class), eq(AccountDTO.class)))
                    .thenThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

            // Act & Assert
            assertThrows(RuntimeException.class, () ->
                    sut.exceedsLimit(JWT, ACCOUNT_ID)
            );
        }

        @Test
        @Description("Checks limits but server returns 200 with null body -> should throw exception")
        void checksLimit_whenServerReturnsNullBody_throwsException() {
            // Arrange
            AccountDTO body = null;
            var response = new ResponseEntity<AccountDTO>(body, HttpStatus.OK);
            when(restTemplate.exchange(any(RequestEntity.class), eq(AccountDTO.class)))
                    .thenReturn(response);

            // Act & Assert
            assertThrows(NullPointerException.class, () ->
                    sut.exceedsLimit(JWT, ACCOUNT_ID)
            );
        }

        @Test
        @Description("Checks limits when exceeds limit -> should return true")
        void checksLimit_whenExceedsLimit_returnsTrue() {
            // Arrange
            PlanDTO planDTO = new PlanDTO();
            planDTO.setMaxProductos(MAX_PRODUCTS);
            AccountDTO accountDTO = new AccountDTO();
            accountDTO.setPlan(planDTO);
            Integer productsAmount = MAX_PRODUCTS;

            var response = new ResponseEntity<AccountDTO>(accountDTO, HttpStatus.OK);
            when(restTemplate.exchange(any(RequestEntity.class), eq(AccountDTO.class)))
                    .thenReturn(response);

            when(productRepository.countProductsOwnedByAccount(ACCOUNT_ID))
                    .thenReturn(productsAmount);

            // Act
            boolean exceedsLimit = sut.exceedsLimit(JWT, ACCOUNT_ID);

            // Arrange
            assertTrue(exceedsLimit);
        }

        @Test
        @Description("Checks limits when does not exceed limit -> should return false")
        void checksLimit_whenDoesNotExceedLimit_returnsFalse() {
            // Arrange
            PlanDTO planDTO = new PlanDTO();
            planDTO.setMaxProductos(MAX_PRODUCTS);
            AccountDTO accountDTO = new AccountDTO();
            accountDTO.setPlan(planDTO);
            Integer productsAmount = MAX_PRODUCTS - 1;

            var response = new ResponseEntity<AccountDTO>(accountDTO, HttpStatus.OK);
            when(restTemplate.exchange(any(RequestEntity.class), eq(AccountDTO.class)))
                    .thenReturn(response);

            when(productRepository.countProductsOwnedByAccount(ACCOUNT_ID))
                    .thenReturn(productsAmount);

            // Act
            boolean exceedsLimit = sut.exceedsLimit(JWT, ACCOUNT_ID);

            // Arrange
            assertFalse(exceedsLimit);
        }
    }
}