/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.print.DocFlavor.URL;

/**
 *
 * @author UTN
 */
public class ConexionBD {

    private static final String URL
            = "jdbc:mysql://localhost:3306/ventas?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=America/Costa_Rica";
    private static final String USUARIO = "root";
    private static final String CLAVE = "";

    public static Connection getConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CLAVE);
    }

}
