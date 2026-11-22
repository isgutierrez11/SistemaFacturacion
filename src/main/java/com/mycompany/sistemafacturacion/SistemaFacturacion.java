package com.mycompany.sistemafacturacion;

import vistas.frmLogin;

/**
 * Punto de entrada principal del Sistema de Facturación
 */
public class SistemaFacturacion {

    public static void main(String[] args) {
        // Establecer Look and Feel agradable si está disponible
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {
        }

        // Iniciar pantalla de login
        java.awt.EventQueue.invokeLater(() -> {
            new frmLogin().setVisible(true);
        });
    }
}
