package modeloDAO;

import java.util.List;
import modelo.Compra;

public interface InterfazCompraDAO {
    
    public List<Compra> getCompras(); // Obtener todas las compras

    public Compra getCompraPorId(int id); // Obtener una compra específica por ID

    public int add(Compra compra); // Insertar una nueva compra

    public int update(Compra compra); // Actualizar compra (opcional según el flujo)

    public int delete(int id); // Eliminar compra

    public List<Compra> getComprasPorRangoFechas(String fechaInicio, String fechaFin); // Reportes por rango
}
