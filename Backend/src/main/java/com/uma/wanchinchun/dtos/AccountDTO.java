package com.uma.wanchinchun.dtos;

import java.util.Objects;

public class AccountDTO {
    private Long id;
    private String nombre;
    private String direccion;
    private String nif;
    private String fechaAlta;
    private PlanDTO plan;

    public AccountDTO() {}

    public AccountDTO(Long id, String nombre, String direccion, String nif, String fechaAlta, PlanDTO plan) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.nif = nif;
        this.fechaAlta = fechaAlta;
        this.plan = plan;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(String fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public PlanDTO getPlan() {
        return plan;
    }

    public void setPlan(PlanDTO plan) {
        this.plan = plan;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AccountDTO that = (AccountDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(nombre, that.nombre) && Objects.equals(direccion, that.direccion) && Objects.equals(nif, that.nif) && Objects.equals(fechaAlta, that.fechaAlta) && Objects.equals(plan, that.plan);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, direccion, nif, fechaAlta, plan);
    }
}
