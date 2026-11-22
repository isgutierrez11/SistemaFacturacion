/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.SQLException;

/**
 *
 * @author UTN
 */
public class ErrorBD {

  
    public static String mensaje(SQLException ex) {
        if ("45000".equals(ex.getSQLState())) {
            return ex.getMessage();
        }
        switch (ex.getErrorCode()) {
            case 1062:
                return "Ya existe un registro con esa llave primaria.";
            case 1451:
                return "No se puede eliminar: el registro tiene datos asociados.";
            case 1452:
                return "El registro relacionado (cliente o producto) no existe.";
            case 1045:
                return "Usuario o contraseña de MySQL incorrectos (revise Conexion.java).";
            case 1049:
                return "La base de datos 'ventas' no existe. Ejecute el script SQL.";
            case 1305:
                return "Falta un procedimiento almacenado. Ejecute el script SQL completo.";
            default:
                return "Error de base de datos: " + ex.getMessage();
        }
    }
}

