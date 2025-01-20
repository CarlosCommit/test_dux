package com.test.dux.dto;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public class TeamDTO implements Serializable {

    private Long id;
    @NotBlank(message = "el campo nombre no debe estar vacio o nulo")
    private String nombre;
    @NotBlank(message = "el campo liga no debe estar vacio o nulo")
    private String liga;
    @NotBlank(message = "el campo pais no debe estar vacio o nulo")
    private String pais;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLiga() {
        return liga;
    }

    public void setLiga(String liga) {
        this.liga = liga;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "TeamDTO{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", liga='" + liga + '\'' +
                ", pais='" + pais + '\'' +
                '}';
    }
}
