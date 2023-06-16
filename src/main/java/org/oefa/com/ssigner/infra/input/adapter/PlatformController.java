package org.oefa.com.ssigner.infra.input.adapter;

import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXProgressBar;
import io.github.palexdev.materialfx.css.themes.MFXThemeManager;
import io.github.palexdev.materialfx.css.themes.Themes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.oefa.com.ssigner.domain.app.PlatformModel;
import org.oefa.com.ssigner.domain.util.Log;
import org.oefa.com.ssigner.infra.input.port.PlatformPort;
import org.oefa.com.ssigner.util.LogUtil;
import org.oefa.com.ssigner.util.TimeUtil;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class PlatformController extends RootController implements Initializable, PlatformPort{


    private static PlatformService platformService;

    @FXML
    private MFXComboBox<String> certificateComboBox;
    @FXML
    private MFXProgressBar stepProgressBar;
    @FXML
    private Button confirmButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button updateButton;
    @FXML
    private Label stepLabel1;
    @FXML
    private Label stepLabel2;
    @FXML
    private Label stepLabel3;
    @FXML
    private Label stepLabel4;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PlatformModel platformModel = new PlatformModel(
                Arrays.asList(stepLabel1, stepLabel2, stepLabel3, stepLabel4),
                stepProgressBar,
                certificateComboBox,
                confirmButton,
                cancelButton,
                updateButton
        );

        platformService = new PlatformService(platformModel);

    }


    public static void start(){
        TimeUtil.showTime("=> Iniciando vista de firma");
        LogUtil.setInfo(new Log("Aplicación inicializada", PlatformController.class.getName()));
        platformService.start();

    }


    @FXML
    public void onCancel(ActionEvent event){
        LogUtil.setInfo(new Log("Se canceló el proceso de firma", this.getClass().getName()));
        platformService.onCancel();

    }


    @FXML
    public void onConfirm(ActionEvent event){
        LogUtil.setInfo(new Log("Se confirmó la firma/visado de los archivos", this.getClass().getName()));
        platformService.onConfirm(certificateComboBox.getValue());

    }


    @FXML
    public void onReloadCertificates(ActionEvent event){
        LogUtil.setInfo(new Log("Actualizando lista de certificados disponibles", this.getClass().getName()));
        platformService.onReloadCertificates();

    }

}
