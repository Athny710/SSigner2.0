package org.oefa.com.ssigner.task.configuration;

import javafx.application.Platform;
import javafx.concurrent.Task;
import org.oefa.com.ssigner.application.DatabaseService;
import org.oefa.com.ssigner.domain.util.Log;
import org.oefa.com.ssigner.util.LogUtil;
import org.oefa.com.ssigner.util.TimeUtil;

import java.sql.Time;

public class DatabaseConfigurationTask extends Task<Void>{


    private final ConfigurationTaskManager configurationTaskManager;


    public DatabaseConfigurationTask(ConfigurationTaskManager configurationTaskManager) {
        this.configurationTaskManager = configurationTaskManager;
    }


    @Override
    protected Void call() throws Exception {
        TimeUtil.showTime("-> INICIO: Base de datos");
        DatabaseService.setupDatabase();
        return null;

    }


    @Override
    protected void succeeded() {
        TimeUtil.showTime("-> FIN: Base de datos");
        LogUtil.setInfo(new Log("Se configuró la base de datos en la aplicación", this.getClass().getName()));
        super.succeeded();
        this.configurationTaskManager.configurationFinished();

    }


    @Override
    protected void failed() {
        super.failed();
        LogUtil.setError(
                new Log(
                        "Error configurando la base de datos en la aplicación",
                        this.getClass().getName(),
                        (Exception) super.getException()
                )
        );
        Platform.exit();

    }
}
