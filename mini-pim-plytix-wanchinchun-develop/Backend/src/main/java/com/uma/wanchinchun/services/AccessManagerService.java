package com.uma.wanchinchun.services;

import com.uma.wanchinchun.dtos.AccountDTO;
import com.uma.wanchinchun.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
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
    public boolean hasAccessToAccount(String jwt, Long idCuenta) {
        var request = RequestEntity.get(baseUrl + "/cuenta/" + idCuenta + "/propietario")
                .header("Authorization", "Bearer " + jwt)
                .build();

        try {
            var response = restTemplate.exchange(request, String.class);
            HttpStatusCode statusCode = response.getStatusCode();
            return statusCode.is2xxSuccessful();
        } catch (Exception e) {
            System.out.println("Fetching could not be done.");
            return false;
        }
    }

    /**
     * Precondition: Must have access to account
     * @param idCuenta
     * @return
     */
    @Override
    public boolean exceedsLimit(String jwt, Long idCuenta) {

        var request = RequestEntity.get(baseUrl + "/cuenta/?idCuenta=" + idCuenta)
                .header("Authorization", "Bearer " + jwt)
                .build();

        AccountDTO accountInfo;
        try {
            var response = restTemplate.exchange(request, AccountDTO.class);
            accountInfo = response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Could not fetch data from server.");
        }

        if (accountInfo == null) {
            throw new NullPointerException("Account info does not exist for the given idCuenta argument");
        }

        Integer productsQuantity = productRepository.countProductsOwnedByAccount(idCuenta);
        Integer maxProductsQuantity = accountInfo.getPlan().getMaxProductos();

        return productsQuantity + 1 <= maxProductsQuantity;
    }
}
