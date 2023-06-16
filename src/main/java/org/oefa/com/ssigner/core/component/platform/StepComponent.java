package org.oefa.com.ssigner.core.component.platform;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.util.Duration;
import org.oefa.com.ssigner.domain.app.PlatformModel;
import java.util.List;

public class StepComponent {

    public final int stepsNumber = 4;
    public final PlatformModel platformModel;
    private final List<String> stepSuccededLabelsList= List.of(
            "Se obtuvo el proceso de firma",
            "Se almacenaron los archivos",
            "Confirmación exitosa",
            "Se firmaron los archivos",
            "Se subieron los archivos"
    );

    public StepComponent(PlatformModel platformModel){
        this.platformModel = platformModel;
    }


    public void showStepCompleted(int stepNumber){
        this.updateProgressBar();
        this.updateLabels(stepNumber);

    }


    public void enableButtons(){
        platformModel.getConfirmButton().setDisable(false);
        platformModel.getUpdateButton().setDisable(false);
    }


    public void disableButtons(){
        platformModel.getConfirmButton().setDisable(true);
        platformModel.getUpdateButton().setDisable(true);
    }


    private void updateLabels(int stepNumber){

        platformModel.getStepLabels().get(stepNumber).setText(this.stepSuccededLabelsList.get(stepNumber));
        // Se valida que exista un siguiente label
        if(stepNumber + 1 >= this.stepsNumber)
            return;

        platformModel.getStepLabels().get(stepNumber+1).getStyleClass().clear();
        platformModel.getStepLabels().get(stepNumber+1).getStyleClass().add("active-text");
    }


    private void updateProgressBar(){
        double currentProgress = platformModel.getStepProgressBar().getProgress();
        double stepProgress = currentProgress + 1.0/this.stepsNumber;

        Timeline task = new Timeline(
                new KeyFrame(
                        Duration.ZERO,
                        new KeyValue(platformModel.getStepProgressBar().progressProperty(), platformModel.getStepProgressBar().getProgress())
                ),
                new KeyFrame(
                        Duration.seconds(0.25),
                        new KeyValue(platformModel.getStepProgressBar().progressProperty(), stepProgress)
                )
        );
        task.playFromStart();

    }

}
