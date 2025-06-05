package modeloDAO;

import modelo.Venta;
import modelo.DetalleVenta;
import java.util.List;

public interface InterfazVentaDAO {
    public int registrarVenta(Venta venta);
    public boolean registrarDetalle(List<DetalleVenta> detalles);
}
