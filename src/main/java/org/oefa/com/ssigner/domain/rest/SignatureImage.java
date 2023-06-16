package org.oefa.com.ssigner.domain.rest;

public class SignatureImage {

    private byte[] imagen;
    private Integer estado;

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }
}
