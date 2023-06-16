package org.oefa.com.ssigner.task.platform;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import org.oefa.com.ssigner.application.RestService;
import org.oefa.com.ssigner.core.component.platform.CertificateComponent;
import org.oefa.com.ssigner.core.component.platform.StepComponent;
import org.oefa.com.ssigner.domain.CertificateModel;
import org.oefa.com.ssigner.domain.FileModel;
import org.oefa.com.ssigner.domain.SignConfigurationModel;
import org.oefa.com.ssigner.util.TaskUtil;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PlatformTaskManager {


    public final CertificateComponent certificateComponent;
    public final StepComponent stepComponent;
    private SignConfigurationModel signConfigurationModel;
    private ArrayList<FileModel> signedFilesList;

    public PlatformTaskManager(StepComponent stepComponent, CertificateComponent certificateComponent){
        this.stepComponent = stepComponent;
        this.certificateComponent = certificateComponent;
    }


    public void startSigningProcess(){
        SignProcessTask signProcessTask = new SignProcessTask(0,this.stepComponent, this.certificateComponent);
        signProcessTask.addEventHandler(
                WorkerStateEvent.WORKER_STATE_SUCCEEDED,
                workerStateEvent -> signConfigurationModel = signProcessTask.getValue()
        );

        TaskUtil.executeTask(signProcessTask);

    }


    public void confirmSigningProcess(CertificateModel certificateSelected){
        ExecutorService executorService  = Executors.newSingleThreadExecutor();

        ConfirmTask confirmTask = new ConfirmTask(1, this.stepComponent);

        SignFilesTask signFilesTask = new SignFilesTask(2, this.stepComponent, this.signConfigurationModel, certificateSelected);
        signFilesTask.addEventHandler(
                WorkerStateEvent.WORKER_STATE_SUCCEEDED,
                workerStateEvent -> {
                    ArrayList<FileModel> list = signFilesTask.getValue();
                    System.out.println("----------");
                    UploadFilesTask uploadFilesTask = new UploadFilesTask(3, this.stepComponent, list);
                    TaskUtil.executeTask(uploadFilesTask);
                }
        );

        executorService.submit(confirmTask);
        executorService.submit(signFilesTask);



    }

    public void cancelSignProcess(){
        try {
            RestService restService = new RestService();
            restService.cancelSignProcess();
        }catch (Exception e){

        }
    }


}
