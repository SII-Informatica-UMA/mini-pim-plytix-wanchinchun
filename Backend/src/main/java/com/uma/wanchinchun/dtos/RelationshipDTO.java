package com.uma.wanchinchun.dtos;

import java.io.Serializable;

public class RelationshipDTO {
    private Long id;
    private Long idOrigen;
    private Long idDestino;
    private String tipoRelacion;

    // Constructor
    public RelationshipDTO() {
    }

    public RelationshipDTO(Long id, Long idOrigen, Long idDestino, String tipoRelacion) {
        this.id = id;
        this.idOrigen = idOrigen;
        this.idDestino = idDestino;
        this.tipoRelacion = tipoRelacion;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdOrigen() {
        return idOrigen;
    }

    public void setIdOrigen(Long idOrigen) {
        this.idOrigen = idOrigen;
    }

    public Long getIdDestino() {
        return idDestino;
    }

    public void setIdDestino(Long idDestino) {
        this.idDestino = idDestino;
    }

    public String getTipoRelacion() {
        return tipoRelacion;
    }

    public void setTipoRelacion(String tipoRelacion) {
        this.tipoRelacion = tipoRelacion;
    }

    @Override
    public String toString() {
        return "RelationshipDTO{" +
                "id=" + id +
                ", idOrigen=" + idOrigen +
                ", idDestino=" + idDestino +
                ", tipoRelacion='" + tipoRelacion + '\'' +
                '}';
    }
}