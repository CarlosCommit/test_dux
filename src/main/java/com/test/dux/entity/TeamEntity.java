package com.test.dux.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "TEAMS")
public class TeamEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String nombre;
    @Column
    private String liga;
    @Column
    private String pais;

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

    @Override
    public String toString() {
        return "TeamEntity{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", liga='" + liga + '\'' +
                ", pais='" + pais + '\'' +
                '}';
    }
}
