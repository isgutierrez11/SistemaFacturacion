/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Usuario;

/**
 *
 * @author UTN
 */
public class UsuarioDAO {

    public Usuario validarLogin(String cedula, String contrasena) throws SQLException {
        try (Connection con = ConexionBD.getConexion();
             CallableStatement cs = con.prepareCall("{CALL sp_usuario_login(?,?)}")) {
            cs.setString(1, cedula);
            cs.setString(2, contrasena);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(rs.getString("cedula"), rs.getString("nombre"),
                                       rs.getString("contrasena"));
                }
            }
        }
        return null;
    }

    public void insertar(Usuario u) throws SQLException {
        try (Connection con = ConexionBD.getConexion();
             CallableStatement cs = con.prepareCall("{CALL sp_usuario_insertar(?,?,?)}")) {
            cs.setString(1, u.getCedula());
            cs.setString(2, u.getNombre());
            cs.setString(3, u.getContrasena());
            cs.execute();
        }
    }

    public void actualizar(Usuario u) throws SQLException {
        try (Connection con = ConexionBD.getConexion();
             CallableStatement cs = con.prepareCall("{CALL sp_usuario_actualizar(?,?,?)}")) {
            cs.setString(1, u.getCedula());
            cs.setString(2, u.getNombre());
            cs.setString(3, u.getContrasena());
            cs.execute();
        }
    }

    public void eliminar(String cedula) throws SQLException {
        try (Connection con = ConexionBD.getConexion();
             CallableStatement cs = con.prepareCall("{CALL sp_usuario_eliminar(?)}")) {
            cs.setString(1, cedula);
            cs.execute();
        }
    }

    /** Busca por cédula o nombre. Filtro vacío = todos. */
    public List<Usuario> buscar(String filtro) throws SQLException {
        List<Usuario> lista = new ArrayList<>();
        try (Connection con = ConexionBD.getConexion();
             CallableStatement cs = con.prepareCall("{CALL sp_usuario_buscar(?)}")) {
            cs.setString(1, filtro);
            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Usuario(rs.getString("cedula"), rs.getString("nombre"),
                                          rs.getString("contrasena")));
                }
            }
        }
        return lista;
    }
}

