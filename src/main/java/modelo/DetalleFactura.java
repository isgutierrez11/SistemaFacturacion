/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author UTN
 */
public class DetalleFactura {

    private String codigoProducto, descripcion;
    private double precio, importe;
    private int cantidad;

    public DetalleFactura() {
    }

    public DetalleFactura(String codigoProducto, String descripcion, double precio, int cantidad) {
        this.codigoProducto = codigoProducto;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidad = cantidad;
        this.importe = precio * cantidad;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getImporte() {
        return importe;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        this.importe = precio * cantidad;
    }
}

