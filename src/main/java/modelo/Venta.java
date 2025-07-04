package modelo;

import java.util.Date;
import java.util.List;

public class Venta {

    private Integer id;
    private String numeroFactura;
    private Date fecha;
    private Integer clienteId;
    private String formaPago;
    private Double totalFactura;
    private List<DetalleVenta> detalles;
    private boolean anulado;

    public Venta() {
    }

    public Venta(Integer id, String numeroFactura, Date fecha, String formaPago, Double totalFactura, Integer clienteId, boolean anulado) {
        this.id = id;
        this.numeroFactura = numeroFactura;
        this.fecha = fecha;
        this.formaPago = formaPago;
        this.totalFactura = totalFactura;
        this.clienteId = clienteId;
        this.anulado = anulado;
    }

    public Venta(String numeroFactura, Date fecha, Integer clienteId, String formaPago, Double totalFactura) {
        this.numeroFactura = numeroFactura;
        this.fecha = fecha;
        this.clienteId = clienteId;
        this.formaPago = formaPago;
        this.totalFactura = totalFactura;
    }

    public Venta(Integer id, String numeroFactura, Date fecha, String formaPago, Double totalFactura, Integer clienteId) {
        this.id = id;
        this.numeroFactura = numeroFactura;
        this.fecha = fecha;
        this.formaPago = formaPago;
        this.totalFactura = totalFactura;
        this.clienteId = clienteId;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public boolean isAnulado() {
        return anulado;
    }

    public void setAnulado(boolean anulado) {
        this.anulado = anulado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public Double getTotalFactura() {
        return totalFactura;
    }

    public void setTotalFactura(Double totalFactura) {
        this.totalFactura = totalFactura;
    }

    public List<DetalleVenta> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleVenta> detalles) {
        this.detalles = detalles;
    }

    private String clienteNombre;

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }
}
