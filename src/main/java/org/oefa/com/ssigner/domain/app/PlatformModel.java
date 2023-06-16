package org.oefa.com.ssigner.domain.app;

import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXProgressBar;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.util.List;

public class PlatformModel {
    private List<Label> stepLabels;
    private MFXProgressBar stepProgressBar;
    private MFXComboBox<String> certificateComboBox;
    private Button confirmButton;
    private Button cancelButton;
    private Button updateButton;

    public PlatformModel(List<Label> stepLabels, MFXProgressBar stepProgressBar, MFXComboBox<String> certificateComboBox, Button confirmButton, Button cancelButton, Button updateButton) {
        this.stepLabels = stepLabels;
        this.stepProgressBar = stepProgressBar;
        this.certificateComboBox = certificateComboBox;
        this.confirmButton = confirmButton;
        this.cancelButton = cancelButton;
        this.updateButton = updateButton;
    }

    public List<Label> getStepLabels() {
        return stepLabels;
    }

    public void setStepLabels(List<Label> stepLabels) {
        this.stepLabels = stepLabels;
    }

    public MFXProgressBar getStepProgressBar() {
        return stepProgressBar;
    }

    public void setStepProgressBar(MFXProgressBar stepProgressBar) {
        this.stepProgressBar = stepProgressBar;
    }

    public MFXComboBox<String> getCertificateComboBox() {
        return certificateComboBox;
    }

    public void setCertificateComboBox(MFXComboBox<String> certificateComboBox) {
        this.certificateComboBox = certificateComboBox;
    }

    public Button getConfirmButton() {
        return confirmButton;
    }

    public void setConfirmButton(Button confirmButton) {
        this.confirmButton = confirmButton;
    }

    public Button getCancelButton() {
        return cancelButton;
    }

    public void setCancelButton(Button cancelButton) {
        this.cancelButton = cancelButton;
    }

    public Button getUpdateButton() {
        return updateButton;
    }

    public void setUpdateButton(Button updateButton) {
        this.updateButton = updateButton;
    }
}
