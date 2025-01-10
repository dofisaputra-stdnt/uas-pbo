package edu.umb.uaspbo.util;

import javax.swing.*;

public class DialogUtil {

    public static void showInfo(String message) {
        JOptionPane.showMessageDialog(null, message, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static boolean showConfirm(String message) {
        int response = JOptionPane.showConfirmDialog(null, message, "Confirm", JOptionPane.YES_NO_OPTION);
        return response == JOptionPane.YES_OPTION;
    }

}
