package modelo;

public class DetalleCompra {
    private int id;
    private int compraId;
    private int productoId;
    private int cantidad;
    private double precioUnitario;
    private double totalArticulo;

    public DetalleCompra() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCompraId() {
        return compraId;
    }

    public void setCompraId(int compraId) {
        this.compraId = compraId;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public double getTotalArticulo() {
        return totalArticulo;
    }

    public void setTotalArticulo(double totalArticulo) {
        this.totalArticulo = totalArticulo;
    }
}
