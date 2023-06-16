package org.oefa.com.ssigner.infra.input.adapter;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.oefa.com.ssigner.core.stage.StageBuilder;
import org.oefa.com.ssigner.core.stage.StageInfo;
import org.oefa.com.ssigner.util.TimeUtil;


public class LoaderController extends RootController{

    @FXML
    private StackPane parentContainer;
    @FXML
    private GridPane loaderParent;


    public static void init(Stage stage){
        LoaderService loaderService = new LoaderService(stage);
        loaderService.initializeConfiguration();
    }


    public void showUserInterface(){
        TimeUtil.showTime("que fue");
        try {
            Parent platformParent = StageBuilder.getPlatformParent();
            Scene currentScene = parentContainer.getScene();

            parentContainer.getChildren().add(0, platformParent);

            Timeline timeline = new Timeline();
            KeyValue kv = new KeyValue(loaderParent.translateYProperty(), -currentScene.getHeight(), Interpolator.EASE_IN);
            KeyFrame kf = new KeyFrame(Duration.seconds(1.5), kv);
            timeline.getKeyFrames().add(kf);
            timeline.play();

            PlatformController.start();

        }catch (Exception e){
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
    }


}
