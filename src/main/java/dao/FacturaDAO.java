/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import modelo.DetalleFactura;
import modelo.Factura;

/**
 *
 * @author UTN
 */
public class FacturaDAO {

    public int siguienteNumero() throws SQLException {
        try (Connection con = ConexionBD.getConexion(); CallableStatement cs = con.prepareCall("{CALL sp_factura_siguiente_numero()}"); ResultSet rs = cs.executeQuery()) {
            return rs.next() ? rs.getInt("siguiente") : 1;
        }
    }

    public int guardar(String cedulaCliente, List<DetalleFactura> detalle) throws SQLException {
        Connection con = ConexionBD.getConexion();
        try {
            con.setAutoCommit(false);
            int numero;
            try (CallableStatement cs = con.prepareCall("{CALL sp_factura_insertar(?,?)}")) {
                cs.setString(1, cedulaCliente);
                cs.registerOutParameter(2, Types.INTEGER);
                cs.execute();
                numero = cs.getInt(2);
            }try (CallableStatement cs = con.prepareCall("{CALL sp_detalle_insertar(?,?,?)}")) {
                for (DetalleFactura d : detalle) {
                    cs.setInt(1, numero);
                    cs.setString(2, d.getCodigoProducto());
                    cs.setInt(3, d.getCantidad());
                    cs.execute();
                }
            }try (CallableStatement cs = con.prepareCall("{CALL sp_factura_actualizar_total(?)}")) {
                cs.setInt(1, numero);
                cs.execute();
            }
            con.commit();
            return numero;
        } catch (SQLException ex) {
            con.rollback();
            throw ex;
        } finally {
            con.setAutoCommit(true);
            con.close();
        }
    }

    public List<Factura> listar(String filtro) throws SQLException {
        List<Factura> lista = new ArrayList<>();
        try (Connection con = ConexionBD.getConexion(); CallableStatement cs = con.prepareCall("{CALL sp_factura_listar(?)}")) {
            cs.setString(1, filtro);
            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearEncabezado(rs));
                }
            }
        }
        return lista;
    }

  
    public Factura obtener(int numero) throws SQLException {
        try (Connection con = ConexionBD.getConexion(); CallableStatement cs = con.prepareCall("{CALL sp_factura_obtener(?)}")) {
            cs.setInt(1, numero);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return mapearEncabezado(rs);
                }
            }
        }
        return null;
    }

    public List<DetalleFactura> listarDetalle(int numero) throws SQLException {
        List<DetalleFactura> lista = new ArrayList<>();
        try (Connection con = ConexionBD.getConexion(); CallableStatement cs = con.prepareCall("{CALL sp_factura_detalle(?)}")) {
            cs.setInt(1, numero);
            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    lista.add(new DetalleFactura(rs.getString("codigo_producto"),
                            rs.getString("descripcion"),
                            rs.getDouble("precio_unitario"),
                            rs.getInt("cantidad")));
                }
            }
        }
        return lista;
    }

    private Factura mapearEncabezado(ResultSet rs) throws SQLException {
        return new Factura(rs.getInt("numero_factura"),
                rs.getDate("fecha").toLocalDate(),
                rs.getString("cedula_cliente"),
                rs.getString("nombre"),
                rs.getDouble("total"));
    }
}
