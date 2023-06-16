package org.oefa.com.ssigner.task.configuration;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.oefa.com.ssigner.configuration.ApplicationConfiguration;
import org.oefa.com.ssigner.core.stage.StageBuilder;
import org.oefa.com.ssigner.core.stage.StageInfo;
import org.oefa.com.ssigner.domain.util.Log;
import org.oefa.com.ssigner.infra.input.adapter.LoaderController;
import org.oefa.com.ssigner.infra.input.adapter.PlatformController;
import org.oefa.com.ssigner.util.LogUtil;
import org.oefa.com.ssigner.util.TaskUtil;
import org.oefa.com.ssigner.util.TimeUtil;

public class ConfigurationTaskManager {


    private final Stage stage;
    private final Scene currentScene;
    // Número total de configuraciones menos 1. Esto permite que la siguiente vista se muestre en menos tiempo.
    private int configurationTasks = 2;


    public ConfigurationTaskManager(Stage stage){
        this.stage = stage;
        this.currentScene = stage.getScene();
    }


    public void initializeConfigurationTask(){
        LogUtil.setInfo(new Log("=> Iniciando la configuración de la aplicación", this.getClass().getName()));

        NotificationConfigurationTask notificationConfigurationTask = new NotificationConfigurationTask(this.stage, this);
        DatabaseConfigurationTask databaseConfigurationTask = new DatabaseConfigurationTask(this);
        CertificateConfiguration certificateConfiguration = new CertificateConfiguration(this);

        TaskUtil.executeTask(notificationConfigurationTask);
        TaskUtil.executeTask(databaseConfigurationTask);
        //TaskUtil.executeTask(certificateConfiguration);

    }

    public void configurationFinished(){
        this.configurationTasks -= 1;

        if(this.configurationTasks == 0) {
            TimeUtil.showTime("CArgando vista");
            LoaderController controller = (LoaderController) StageInfo.CURRENT_CONTROLLER;
            controller.showUserInterface();
            TimeUtil.showTime("Vista cargado");
//            PlatformController.start();

        }

    }


}
