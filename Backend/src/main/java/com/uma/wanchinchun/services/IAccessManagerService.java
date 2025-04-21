package com.uma.wanchinchun.services;

public interface IAccessManagerService {
    boolean hasAccessToAccount(Long idCuenta);
    boolean exceedsLimit(Long idCuenta);
}
