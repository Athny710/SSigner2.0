package org.oefa.com.ssigner.task.platform;

import javafx.application.Platform;
import javafx.concurrent.Task;
import org.oefa.com.ssigner.application.RestService;
import org.oefa.com.ssigner.core.component.platform.StepComponent;
import org.oefa.com.ssigner.domain.FileModel;
import org.oefa.com.ssigner.domain.util.Log;
import org.oefa.com.ssigner.util.LogUtil;
import org.oefa.com.ssigner.util.TimeUtil;

import java.util.ArrayList;

public class UploadFilesTask extends Task<Void> {


    private final StepComponent stepComponent;
    private final ArrayList<FileModel> fileList;
    private final int stepNumber;


    public UploadFilesTask(int stepNumber, StepComponent stepComponent,  ArrayList<FileModel> fileList){
        this.stepComponent = stepComponent;
        this.stepNumber = stepNumber;
        this.fileList = fileList;
    }


    @Override
    protected Void call() throws Exception {
        TimeUtil.showTime("Subiendo archivos");
        RestService.uploadFilesSigned(fileList);
        return null;
    }


    @Override
    protected void succeeded() {
        TimeUtil.showTime("Archivos subidos");
        LogUtil.setInfo(new Log("Se configuró la base de datos en la aplicación", this.getClass().getName()));
        super.succeeded();

        Platform.runLater(new Runnable() {
            @Override public void run() {
                stepComponent.showStepCompleted(stepNumber);
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
        Platform.exit();
        System.exit(0);

    }
}
