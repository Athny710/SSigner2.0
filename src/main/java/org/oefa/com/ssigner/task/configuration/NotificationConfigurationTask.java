package org.oefa.com.ssigner.task.configuration;

import io.github.palexdev.materialfx.css.themes.MFXThemeManager;
import io.github.palexdev.materialfx.css.themes.Themes;
import io.github.palexdev.materialfx.notifications.MFXNotificationSystem;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.stage.Stage;
import org.oefa.com.ssigner.domain.util.Log;
import org.oefa.com.ssigner.util.LogUtil;
import org.oefa.com.ssigner.util.TimeUtil;

public class NotificationConfigurationTask extends Task<Void> {


    private final Stage stage;
    private final ConfigurationTaskManager configurationTaskManager;


    public NotificationConfigurationTask(Stage stage, ConfigurationTaskManager configurationTaskManager){
        this.configurationTaskManager = configurationTaskManager;
        this.stage = stage;
    }


    @Override
    protected Void call() throws Exception {
        TimeUtil.showTime("-> INICIO: Notificaciones");
        // Sin esta pausa la aplicación no se mostrará hasta que termine esta configuración
        Thread.sleep(500);
        MFXThemeManager.addOn(stage.getScene(), Themes.DEFAULT, Themes.LEGACY);

        return null;

    }


    @Override
    protected void succeeded() {
        TimeUtil.showTime("-> FIN: Notificaciones");
        LogUtil.setInfo(new Log("-> FIN: Notificaciones", this.getClass().getName()));
        super.succeeded();
        this.configurationTaskManager.configurationFinished();

    }


    @Override
    protected void failed() {
        super.failed();
        LogUtil.setError(
                new Log(
                        "Error configurando notificaciones en la aplicación",
                        this.getClass().getName(),
                        (Exception) super.getException()
                )
        );
        Platform.exit();

    }

}
