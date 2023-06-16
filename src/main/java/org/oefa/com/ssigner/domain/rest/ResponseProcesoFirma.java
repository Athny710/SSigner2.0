package org.oefa.com.ssigner.domain.rest;

public class ResponseProcesoFirma {

    private boolean status;
    private String message;
    private GrupoProcesFirma gpf;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public GrupoProcesFirma getGpf() {
        return gpf;
    }

    public void setGpf(GrupoProcesFirma gpf) {
        this.gpf = gpf;
    }
}
