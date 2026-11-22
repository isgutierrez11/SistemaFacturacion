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
import modelo.Producto;

/**
 *
 * @author UTN
 */
public class ProductoDAO {
    public void insertar(Producto p) throws SQLException {
        try (Connection con = ConexionBD.getConexion();
             CallableStatement cs = con.prepareCall("{CALL sp_producto_insertar(?,?,?,?)}")) {
            cs.setString(1, p.getCodigo());
            cs.setString(2, p.getDescripcion());
            cs.setDouble(3, p.getPrecio());
            cs.setInt(4, p.getExistencia());
            cs.execute();
        }
    }

    public void actualizar(Producto p) throws SQLException {
        try (Connection con = ConexionBD.getConexion();
             CallableStatement cs = con.prepareCall("{CALL sp_producto_actualizar(?,?,?,?)}")) {
            cs.setString(1, p.getCodigo());
            cs.setString(2, p.getDescripcion());
            cs.setDouble(3, p.getPrecio());
            cs.setInt(4, p.getExistencia());
            cs.execute();
        }
    }

    public void eliminar(String codigo) throws SQLException {
        try (Connection con = ConexionBD.getConexion();
             CallableStatement cs = con.prepareCall("{CALL sp_producto_eliminar(?)}")) {
            cs.setString(1, codigo);
            cs.execute();
        }
    }

    /** Busca por código o descripción. Filtro vacío = todos. */
    public List<Producto> buscar(String filtro) throws SQLException {
        return leerLista("{CALL sp_producto_buscar(?)}", filtro);
    }

    /** Extra opcional: productos con existencia menor o igual al límite. */
    public List<Producto> stockBajo(int limite) throws SQLException {
        return leerLista("{CALL sp_producto_stock_bajo(?)}", limite);
    }

    /** Usado por facturación: devuelve null si no existe. */
    public Producto buscarPorCodigo(String codigo) throws SQLException {
        try (Connection con = ConexionBD.getConexion();
             CallableStatement cs = con.prepareCall("{CALL sp_producto_obtener(?)}")) {
            cs.setString(1, codigo);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    // ---- utilidades privadas ----
    private List<Producto> leerLista(String llamada, Object parametro) throws SQLException {
        List<Producto> lista = new ArrayList<>();
        try (Connection con = ConexionBD.getConexion();
             CallableStatement cs = con.prepareCall(llamada)) {
            cs.setObject(1, parametro);
            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        }
        return lista;
    }

    private Producto mapear(ResultSet rs) throws SQLException {
        return new Producto(rs.getString("codigo"), rs.getString("descripcion"),
                            rs.getDouble("precio"), rs.getInt("existencia"));
    }
}

