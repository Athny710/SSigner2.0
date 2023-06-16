package org.oefa.com.ssigner;

import io.github.palexdev.materialfx.css.themes.MFXThemeManager;
import io.github.palexdev.materialfx.css.themes.Themes;
import javafx.application.Application;
import javafx.stage.Stage;
import org.oefa.com.ssigner.configuration.ApplicationConfiguration;
import org.oefa.com.ssigner.core.router.Router;
import org.oefa.com.ssigner.core.stage.StageBuilder;
import org.oefa.com.ssigner.domain.util.Log;
import org.oefa.com.ssigner.util.LogUtil;
import org.oefa.com.ssigner.util.TimeUtil;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) {

        Router.initializeApp(getParameters(), stage);
        /*
        if(getParameters().getRaw().size() > 0)
            ApplicationConfiguration.setExecutionParams(getParameters());

        ApplicationConfiguration.loadAppScenes();
        StageBuilder.showLoaderView(stage);

         */
    }

    public static void main(String[] linkArray) {
        TimeUtil.showTime("APP LAUNCHED");
        LogUtil.setInfo(new Log("=============================================", MainApplication.class.getName()));
        launch(linkArray);

    }

}