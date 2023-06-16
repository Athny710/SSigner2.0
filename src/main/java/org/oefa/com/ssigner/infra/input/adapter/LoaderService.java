package org.oefa.com.ssigner.infra.input.adapter;

import javafx.scene.Parent;
import javafx.stage.Stage;
import org.oefa.com.ssigner.task.configuration.ConfigurationTaskManager;

public class LoaderService {

    private final Stage stage;

    public LoaderService(Stage stage){
        this.stage = stage;
    }

    public void initializeConfiguration(){
        ConfigurationTaskManager configurationTaskManager = new ConfigurationTaskManager(this.stage);
        configurationTaskManager.initializeConfigurationTask();
    }
}
