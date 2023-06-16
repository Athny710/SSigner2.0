package org.oefa.com.ssigner.core.stage;

import io.github.palexdev.materialfx.css.themes.MFXThemeManager;
import io.github.palexdev.materialfx.css.themes.Themes;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.oefa.com.ssigner.MainApplication;
import org.oefa.com.ssigner.configuration.ApplicationConfiguration;
import org.oefa.com.ssigner.domain.util.Log;
import org.oefa.com.ssigner.infra.input.adapter.LoaderController;
import org.oefa.com.ssigner.util.LogUtil;
import org.oefa.com.ssigner.util.TimeUtil;

public class StageBuilder {



    public static Scene getLoaderScene() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("view/LoaderView.fxml"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        scene.setFill(Color.TRANSPARENT);

        StageInfo.CURRENT_SCENE = scene;
        StageInfo.CURRENT_CONTROLLER = fxmlLoader.getController();
        return scene;

    }

    public static Parent getPlatformParent()throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("view/PlatformView.fxml"));
        Parent parent = fxmlLoader.load();

        StageInfo.CURRENT_CONTROLLER = fxmlLoader.getController();
        return parent;

    }


    public static Scene getPlatformScene() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("view/PlatformView.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.LEGACY);
        root.requestFocus();

        return scene;

    }
}
