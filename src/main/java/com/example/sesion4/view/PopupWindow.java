package com.example.sesion4.view;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Utility class for displaying various types of popup windows in the application.
 * Provides static methods to show error, information, and other types of alert dialogs.
 * 
 * @author Santiago Vanegas Torres
 * @version 1.0
 */
public class PopupWindow {

    /**
     * Displays an error popup window with the specified title and message.
     * 
     * @param title The title to be displayed in the error dialog's title bar
     * @param msg The detailed error message to be displayed in the dialog content
     * @deprecated As of version 1.0, replaced by {@link #showPopup(AlertType, String, String)}
     *             and {@link #showInfoWindow(String, String)}
     */
    @Deprecated
    public static void Window(String title, String msg) {
        showPopup(AlertType.ERROR, title, msg);
    }

    /**
     * Displays an informational popup window with the specified title and message.
     * 
     * @param title The title to be displayed in the dialog's title bar
     * @param msg The informational message to be displayed in the dialog content
     */
    public static void showInfoWindow(String title, String msg) {
        showPopup(AlertType.INFORMATION, title, msg);
    }

    /**
     * Internal helper method that creates and displays a popup window.
     * 
     * @param alertType The type of alert to display (ERROR, INFORMATION, etc.)
     * @param title The title to be displayed in the dialog's title bar
     * @param msg The message to be displayed in the dialog content
     * @throws IllegalArgumentException if any of the parameters are null
     */
    private static void showPopup(AlertType alertType, String title, String msg) {
        if (alertType == null || title == null || msg == null) {
            throw new IllegalArgumentException("Parameters cannot be null");
        }

        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null); // No header text
        alert.setContentText(msg);
        alert.showAndWait();
    }
}