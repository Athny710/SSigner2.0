package org.oefa.com.ssigner.task.platform;

import javafx.application.Platform;
import javafx.concurrent.Task;
import org.oefa.com.ssigner.core.component.platform.StepComponent;
import org.oefa.com.ssigner.domain.util.Log;
import org.oefa.com.ssigner.util.LogUtil;

public class ConfirmTask extends Task<Void> {

    private final StepComponent stepComponent;
    private final int stepNumber;

    public ConfirmTask(int stepNumber, StepComponent stepComponent){
        this.stepComponent = stepComponent;
        this.stepNumber = stepNumber;
    }

    @Override
    protected Void call() throws Exception {
        return null;
    }

    @Override
    protected void succeeded() {
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
                        "Error obteniendo el proceso de firma",
                        this.getClass().getName(),
                        (Exception) super.getException()
                )
        );
        //Platform.exit();
        //System.exit(0);

    }
}
