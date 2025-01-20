package com.test.dux.dto;

import java.io.Serializable;

public class ErrorResponse implements Serializable {
    private String mensaje;
    private Integer codigo;

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }
}
