package modeloDAO;

import java.util.List;
import modelo.DetalleCompra;

public interface InterfazDetalleCompraDAO {

    public List<DetalleCompra> getDetallesPorCompra(int compraId); // Obtener los detalles de una compra

    public int add(DetalleCompra detalle); // Agregar un detalle de compra

    public int deletePorCompra(int compraId); // Eliminar los detalles de una compra (opcional)

}
