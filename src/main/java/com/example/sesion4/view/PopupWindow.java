package com.example.sesion4.view;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * @author Santiago Vanegas Torres
 */
/**
 * The PopupWindow class provides utility methods for displaying popup windows.
 * It includes methods for showing error messages and win/lose popups.
 */
public class PopupWindow {

    /**
     * Displays an error popup window with the specified title and message.
     *
     * @param title the title of the popup window
     * @param msg the message to display in the popup window
     */
    public static void Window(String title, String msg) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public static void showInfoWindow(String title, String msg) {
        showPopup(AlertType.INFORMATION, title, msg);
    }

    /**
     * Displays a popup window with the specified alert type, title, and message.
     *
     * @param alertType the type of alert to display
     * @param title the title of the popup window
     * @param msg the message to display in the popup window
     */
    private static void showPopup(AlertType alertType, String title, String msg) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

}