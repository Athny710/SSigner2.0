package org.oefa.com.ssigner.task.platform;

import javafx.application.Platform;
import javafx.concurrent.Task;
import org.oefa.com.ssigner.application.SignService;
import org.oefa.com.ssigner.configuration.ApplicationConfiguration;
import org.oefa.com.ssigner.core.component.platform.StepComponent;
import org.oefa.com.ssigner.domain.CertificateModel;
import org.oefa.com.ssigner.domain.FileModel;
import org.oefa.com.ssigner.domain.SignConfigurationModel;
import org.oefa.com.ssigner.domain.util.Log;
import org.oefa.com.ssigner.util.CertificateUtil;
import org.oefa.com.ssigner.util.LogUtil;
import org.oefa.com.ssigner.util.TimeUtil;

import java.util.ArrayList;

public class SignFilesTask extends Task<ArrayList<FileModel>> {


    private final SignService signService;
    private final StepComponent stepComponent;
    private final int stepNumber;


    public SignFilesTask(int stepNumber, StepComponent stepComponent, SignConfigurationModel signConfigurationModel, CertificateModel certificateModel){
        this.signService = new SignService(signConfigurationModel, certificateModel);
        this.stepComponent = stepComponent;
        this.stepNumber = stepNumber;
    }


    @Override
    protected ArrayList<FileModel> call() throws Exception {
        TimeUtil.showTime("--> Firmando archivos");
        return signService.signFiles();

    }


    @Override
    protected void succeeded() {
        LogUtil.setInfo(new Log("Se configuró la base de datos en la aplicación", this.getClass().getName()));
        TimeUtil.showTime("=> Archivos firmados");
        super.succeeded();

        Platform.runLater(new Runnable() {
            @Override public void run() {
                stepComponent.showStepCompleted(stepNumber);
                stepComponent.disableButtons();
            }
        });
    }


    @Override
    protected void failed() {
        super.failed();
        LogUtil.setError(
                new Log(
                        "Error firmando los archivos",
                        this.getClass().getName(),
                        (Exception) super.getException()
                )
        );
        Platform.exit();

    }
}
