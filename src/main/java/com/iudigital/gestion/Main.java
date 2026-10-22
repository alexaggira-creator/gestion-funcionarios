package com.iudigital.gestion;

import com.iudigital.gestion.view.FuncionarioView;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                System.err.println("No se pudo cargar el estilo del sistema: " + ex.getMessage());
            }
            new FuncionarioView().setVisible(true);
        });
    }
}
