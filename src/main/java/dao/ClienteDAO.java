/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.CallableStatement;
import modelo.Cliente;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Yumor
 */
public class ClienteDAO {
    public void insertar(Cliente c) throws SQLException {
        try (Connection con = ConexionBD.getConexion();
             CallableStatement cs = con.prepareCall("{CALL sp_cliente_insertar(?,?,?,?)}")) {
            cs.setString(1, c.getCedula());
            cs.setString(2, c.getNombre());
            cs.setString(3, c.getTelefono());
            cs.setString(4, c.getCorreo());
            cs.execute();
        }
    }

    public void actualizar(Cliente c) throws SQLException {
        try (Connection con = ConexionBD.getConexion();
             CallableStatement cs = con.prepareCall("{CALL sp_cliente_actualizar(?,?,?,?)}")) {
            cs.setString(1, c.getCedula());
            cs.setString(2, c.getNombre());
            cs.setString(3, c.getTelefono());
            cs.setString(4, c.getCorreo());
            cs.execute();
        }
    }

    public void eliminar(String cedula) throws SQLException {
        try (Connection con = ConexionBD.getConexion();
             CallableStatement cs = con.prepareCall("{CALL sp_cliente_eliminar(?)}")) {
            cs.setString(1, cedula);
            cs.execute();
        }
    }

    /** Busca por cédula o nombre. Con filtro vacío devuelve todos. */
    public List<Cliente> buscar(String filtro) throws SQLException {
        List<Cliente> lista = new ArrayList<>();
        try (Connection con = ConexionBD.getConexion();
             CallableStatement cs = con.prepareCall("{CALL sp_cliente_buscar(?)}")) {
            cs.setString(1, filtro);
            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Cliente(rs.getString("cedula"), rs.getString("nombre"),
                                          rs.getString("telefono"), rs.getString("correo")));
                }
            }
        }
        return lista;
    }

    /** Usado por facturación: devuelve null si no existe. */
    public Cliente buscarPorCedula(String cedula) throws SQLException {
        try (Connection con = ConexionBD.getConexion();
             CallableStatement cs = con.prepareCall("{CALL sp_cliente_obtener(?)}")) {
            cs.setString(1, cedula);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return new Cliente(rs.getString("cedula"), rs.getString("nombre"),
                                       rs.getString("telefono"), rs.getString("correo"));
                }
            }
        }
        return null;
    }
}
