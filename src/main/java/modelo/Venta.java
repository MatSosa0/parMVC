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

    public Venta() {
    }

    public Venta(String numeroFactura, Date fecha, Integer clienteId, String formaPago, Double totalFactura) {
        this.numeroFactura = numeroFactura;
        this.fecha = fecha;
        this.clienteId = clienteId;
        this.formaPago = formaPago;
        this.totalFactura = totalFactura;
    }

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNumeroFactura() { return numeroFactura; }
    public void setNumeroFactura(String numeroFactura) { this.numeroFactura = numeroFactura; }
    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
    public Integer getClienteId() { return clienteId; }
    public void setClienteId(Integer clienteId) { this.clienteId = clienteId; }
    public String getFormaPago() { return formaPago; }
    public void setFormaPago(String formaPago) { this.formaPago = formaPago; }
    public Double getTotalFactura() { return totalFactura; }
    public void setTotalFactura(Double totalFactura) { this.totalFactura = totalFactura; }
    public List<DetalleVenta> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleVenta> detalles) { this.detalles = detalles; }
    
    private String clienteNombre;

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }
}