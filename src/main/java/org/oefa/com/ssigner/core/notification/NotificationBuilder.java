package org.oefa.com.ssigner.core.notification;

import io.github.palexdev.materialfx.enums.NotificationPos;
import io.github.palexdev.materialfx.notifications.MFXNotificationSystem;
import io.github.palexdev.materialfx.notifications.base.INotification;
import io.github.palexdev.materialfx.utils.StringUtils;
import io.github.palexdev.mfxresources.fonts.fontawesome.FontAwesomeSolid;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.oefa.com.ssigner.domain.app.NotificationModel;

public class NotificationBuilder {


    public static void showErrorNotification(NotificationPos position, String message, Exception e){
        MFXNotificationSystem.instance()
                .setPosition(position)
                .publish(createNotification(message + "\n" + e.getMessage(), FontAwesomeSolid.ANCHOR_CIRCLE_EXCLAMATION));

    }

    public static void showNotification(NotificationPos position, String message){
        MFXNotificationSystem.instance()
                .setPosition(position)
                .publish(createNotification(message, FontAwesomeSolid.ANCHOR_CIRCLE_EXCLAMATION));

    }

    public static INotification createNotification(String message, FontAwesomeSolid icon){
        NotificationModel notification = new NotificationModel(icon);
        notification.setContentText(message);
        //notification.setTimeToStringConverter();
        return notification;

    }
}
