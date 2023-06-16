package org.oefa.com.ssigner.task.platform;

import io.github.palexdev.materialfx.enums.NotificationPos;
import javafx.application.Platform;
import javafx.concurrent.Task;
import org.oefa.com.ssigner.application.RestService;
import org.oefa.com.ssigner.configuration.ApplicationConfiguration;
import org.oefa.com.ssigner.core.component.platform.CertificateComponent;
import org.oefa.com.ssigner.core.component.platform.StepComponent;
import org.oefa.com.ssigner.core.notification.NotificationBuilder;
import org.oefa.com.ssigner.domain.SignConfigurationModel;
import org.oefa.com.ssigner.domain.util.Log;
import org.oefa.com.ssigner.util.CertificateUtil;
import org.oefa.com.ssigner.util.LogUtil;
import org.oefa.com.ssigner.util.TimeUtil;

public class SignProcessTask extends Task<SignConfigurationModel> {


    private final CertificateComponent certificateComponent;
    private final StepComponent stepComponent;
    private final int stepNumber;


    public SignProcessTask(int stepNumber, StepComponent stepComponent, CertificateComponent certificateComponent){
        this.stepComponent = stepComponent;
        this.certificateComponent = certificateComponent;
        this.stepNumber = stepNumber;
    }


    @Override
    protected SignConfigurationModel call() throws Exception {
        TimeUtil.showTime("COMSUMIENTO SERVICIO");
        Thread.sleep(500);
        while (CertificateUtil.certificateList == null) {
            Thread.sleep(200);
        }

        return RestService.getSigConfiguration();

    }


    @Override
    protected void succeeded() {
        TimeUtil.showTime("SERVICIO CONSUMIDO");
        LogUtil.setInfo(new Log("Se obtuvo información del proceso de firma", this.getClass().getName()));
        super.succeeded();

        Platform.runLater(new Runnable() {
            @Override public void run() {
                certificateComponent.loadCertificates(CertificateUtil.getUserCertificateList(ApplicationConfiguration.DNI_CLIENT));
                stepComponent.showStepCompleted(stepNumber);
                stepComponent.enableButtons();
            }
        });

    }


    @Override
    protected void failed() {
        super.failed();
        LogUtil.setError(
                new Log(
                        "Error obteniendo el proceso de firma",
                        this.getClass().getName(),
                        (Exception) super.getException()
                )
        );
        NotificationBuilder.showErrorNotification(NotificationPos.BOTTOM_RIGHT, "Error obteniendo el proceso de firma", (Exception) super.getException());
        //Platform.exit();
        //System.exit(0);

    }
}
