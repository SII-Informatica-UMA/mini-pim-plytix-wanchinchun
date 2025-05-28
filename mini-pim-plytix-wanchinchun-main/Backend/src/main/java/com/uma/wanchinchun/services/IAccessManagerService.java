package com.uma.wanchinchun.services;

public interface IAccessManagerService {
    boolean hasAccessToAccount(String jwt, Long idCuenta);
    boolean exceedsLimit(String jwt, Long idCuenta);
}
