package org.oefa.com.ssigner.domain.app;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXIconWrapper;
import io.github.palexdev.materialfx.controls.MFXSimpleNotification;
import io.github.palexdev.materialfx.enums.NotificationState;
import io.github.palexdev.materialfx.factories.InsetsFactory;
import io.github.palexdev.materialfx.utils.RandomUtils;
import io.github.palexdev.mfxresources.fonts.IconDescriptor;
import io.github.palexdev.mfxresources.fonts.IconsProviders;
import io.github.palexdev.mfxresources.fonts.MFXFontIcon;
import io.github.palexdev.mfxresources.fonts.fontawesome.FontAwesomeBrands;
import io.github.palexdev.mfxresources.fonts.fontawesome.FontAwesomeRegular;
import io.github.palexdev.mfxresources.fonts.fontawesome.FontAwesomeSolid;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import org.oefa.com.ssigner.util.ResourceLoaderUtil;

public class NotificationModel extends MFXSimpleNotification {

    private final StringProperty headerText = new SimpleStringProperty("Notification Header");
    private final StringProperty contentText = new SimpleStringProperty();

    public NotificationModel(FontAwesomeSolid iconSolid) {

        MFXFontIcon fi = new MFXFontIcon();
        IconDescriptor desc = iconSolid;
        /*
        if (desc instanceof FontAwesomeRegular) {
            fi.setIconsProvider(IconsProviders.FONTAWESOME_REGULAR);
        } else if (desc instanceof FontAwesomeBrands) {
            fi.setIconsProvider(IconsProviders.FONTAWESOME_BRANDS);
        }

         */
        fi.setDescription(desc.getDescription());
        fi.setSize(16);
        MFXIconWrapper icon = new MFXIconWrapper(fi, 32);
        Label headerLabel = new Label();
        headerLabel.textProperty().bind(headerText);

        /*
        MFXIconWrapper readIcon = new MFXIconWrapper("fas-eye", 16, 32);
        ((MFXFontIcon) readIcon.getIcon()).descriptionProperty().bind(Bindings.createStringBinding(
                () -> (getState() == NotificationState.READ) ? "fas-eye" : "fas-eye-slash",
                notificationStateProperty()
        ));


        StackPane.setAlignment(readIcon, Pos.CENTER_RIGHT);

         */


        StackPane placeHolder = new StackPane();


        placeHolder.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(placeHolder, Priority.ALWAYS);


        HBox header = new HBox(10, icon, headerLabel);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(InsetsFactory.of(5, 0, 5, 0));
        header.setMaxWidth(Double.MAX_VALUE);



        Label contentLabel = new Label();
        contentLabel.getStyleClass().add("content");
        contentLabel.textProperty().bind(contentText);
        contentLabel.setWrapText(true);
        contentLabel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        contentLabel.setAlignment(Pos.TOP_LEFT);

        MFXButton action1 = new MFXButton("Action 1");
        MFXButton action2 = new MFXButton("Action 2");
        HBox actionsBar = new HBox(15, action1, action2);
        actionsBar.getStyleClass().add("actions-bar");
        actionsBar.setAlignment(Pos.CENTER_RIGHT);
        actionsBar.setPadding(InsetsFactory.all(5));

        BorderPane container = new BorderPane();
        container.getStyleClass().add("notification");
        container.setTop(header);
        container.setCenter(contentLabel);
        container.setBottom(actionsBar);
        container.getStylesheets().add(ResourceLoaderUtil.load("css/NotificationStyle.css"));
        container.setMinHeight(200);
        container.setMaxWidth(400);

        setContent(container);
    }

    public String getHeaderText() {
        return headerText.get();
    }

    public StringProperty headerTextProperty() {
        return headerText;
    }

    public void setHeaderText(String headerText) {
        this.headerText.set(headerText);
    }

    public String getContentText() {
        return contentText.get();
    }

    public StringProperty contentTextProperty() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText.set(contentText);
    }


}
