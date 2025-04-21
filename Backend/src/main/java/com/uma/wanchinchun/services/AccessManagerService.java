package com.uma.wanchinchun.services;

import com.uma.wanchinchun.dtos.AccountDTO;
import com.uma.wanchinchun.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class AccessManagerService implements IAccessManagerService {

    @Value("${microservice.users.baseurl}")
    private String baseUrl;
    private final RestTemplate restTemplate;
    private final ProductRepository productRepository;

    public AccessManagerService(RestTemplate restTemplate, ProductRepository productRepository) {
        this.restTemplate = restTemplate;
        this.productRepository = productRepository;
    }

    @Override
    public boolean hasAccessToAccount(Long idCuenta) {
        if (idCuenta == null) {
            return false;
        }
        ResponseEntity<String> response = restTemplate.getForEntity(baseUrl + "/cuenta/" + idCuenta + "/propietario", String.class);
        HttpStatusCode statusCode = response.getStatusCode();

        return statusCode.is2xxSuccessful();
    }

    /**
     * Precondicion: Debe tener acceso a la cuenta
     * @param idCuenta
     * @return
     */
    @Override
    public boolean exceedsLimit(Long idCuenta) {
        ResponseEntity<AccountDTO> response = restTemplate.getForEntity(baseUrl + "/cuenta/?idCuenta=" + idCuenta, AccountDTO.class);
        AccountDTO accountInfo = response.getBody();
        if (accountInfo == null) {
            throw new NullPointerException("Account info does not exist for the given idCuenta argument");
        }

        Integer productsQuantity = productRepository.countProductsOwnedByAccount(idCuenta);
        Integer maxProductsQuantity = accountInfo.getPlan().getMaxProductos();

        return productsQuantity + 1 <= maxProductsQuantity;
    }
}
