package modelo;

public class DetalleVenta {
    private Integer id;
    private Integer ventaId;
    private Integer productoId;
    private Integer cantidad;
    private Double precioUnitario;
    private Double totalArticulo;
    
    // 👇 Este es el nuevo campo que necesitas para mostrar en la vista
    private String nombreProducto;

    public DetalleVenta() {}

    public DetalleVenta(Integer ventaId, Integer productoId, Integer cantidad, Double precioUnitario, Double totalArticulo) {
        this.ventaId = ventaId;
        this.productoId = productoId;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.totalArticulo = totalArticulo;
    }

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getVentaId() { return ventaId; }
    public void setVentaId(Integer ventaId) { this.ventaId = ventaId; }
    public Integer getProductoId() { return productoId; }
    public void setProductoId(Integer productoId) { this.productoId = productoId; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    public Double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(Double precioUnitario) { this.precioUnitario = precioUnitario; }
    public Double getTotalArticulo() { return totalArticulo; }
    public void setTotalArticulo(Double totalArticulo) { this.totalArticulo = totalArticulo; }

    // 👇 Nuevo getter y setter
    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }
}
