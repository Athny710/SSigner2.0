package org.oefa.com.ssigner.task.configuration;

import javafx.application.Platform;
import javafx.concurrent.Task;
import org.oefa.com.ssigner.domain.util.Log;
import org.oefa.com.ssigner.util.CertificateUtil;
import org.oefa.com.ssigner.util.LogUtil;
import org.oefa.com.ssigner.util.TimeUtil;

public class CertificateConfiguration extends Task<Void> {


    private final ConfigurationTaskManager configurationTaskManager;


    public CertificateConfiguration(ConfigurationTaskManager configurationTaskManager){
        this.configurationTaskManager = configurationTaskManager;
    }


    @Override
    protected Void call() throws Exception {
        TimeUtil.showTime("-> INICIO: Certificados");
        CertificateUtil.loadCertificates();
        return null;

    }


    @Override
    protected void succeeded() {
        LogUtil.setInfo(new Log("Se obtuvo el crl desde el servidor", this.getClass().getName()));
        TimeUtil.showTime("-> FIN: Certificados");
        super.succeeded();
        this.configurationTaskManager.configurationFinished();

    }


    @Override
    protected void failed() {
        super.failed();
        LogUtil.setError(
                new Log(
                        "Error obteniendo el crl",
                        this.getClass().getName(),
                        (Exception) super.getException()
                )
        );
        Platform.exit();

    }
}
