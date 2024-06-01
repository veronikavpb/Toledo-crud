package org.example.view;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;

public class ViewUtils {
    public static void showSuccessMessage(String text) {
        Notification notification = Notification.show(text);
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }

    public static void showErrorMessage(String text) {
        Notification notification = Notification.show(text);
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
    }
}
