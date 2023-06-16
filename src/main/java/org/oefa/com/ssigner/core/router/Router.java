package org.oefa.com.ssigner.core.router;

import io.github.palexdev.materialfx.css.themes.MFXThemeManager;
import io.github.palexdev.materialfx.css.themes.Themes;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.oefa.com.ssigner.configuration.ApplicationConfiguration;
import org.oefa.com.ssigner.core.stage.StageBuilder;
import org.oefa.com.ssigner.domain.util.Log;
import org.oefa.com.ssigner.infra.input.adapter.LoaderController;
import org.oefa.com.ssigner.util.LogUtil;
import org.oefa.com.ssigner.util.StringUtil;
import org.oefa.com.ssigner.util.TimeUtil;

import java.util.ArrayList;


public class Router {


    private static final String FIRMA_KEY = ApplicationConfiguration.getKey("FIRMA_KEY");
    private static final String VERIFICA_KEY = ApplicationConfiguration.getKey("VERIFICA_KEY");


    public static void initializeApp(Application.Parameters parameters, Stage stage){
        try {
            ArrayList<String> params = StringUtil.getParametersFromUrl(parameters.getRaw().get(0));
            String key = params.get(0);

            if(key.equals(FIRMA_KEY)){
               ApplicationConfiguration.ID_CLIENT = params.get(1);
               ApplicationConfiguration.ID_GROUP = params.get(2);
               initSignAppByPlatform(stage);

            }else if(key.equals(VERIFICA_KEY)){
                initVerificaAppByPlatform();

            }else {
               initFullAppByUser();

            }

        }catch (Exception e){
            LogUtil.setError(
                    new Log(
                            "Error obteniendo parámetros de ejecución para iniciar la aplicación",
                            ApplicationConfiguration.class.getName(),
                            e
                    )
            );
        }
    }


    private static void initSignAppByPlatform(Stage stage) throws Exception {
        Scene scene = StageBuilder.getLoaderScene();

        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.setOnShowing((event)-> {
            TimeUtil.showTime("SHOWING");
            LogUtil.setInfo(new Log("Loader inicializado", StageBuilder.class.getName()));
            LoaderController.init(stage);
        });
        stage.show();

    }


    private static void initVerificaAppByPlatform(){

    }


    private static void initFullAppByUser(){

    }

}
