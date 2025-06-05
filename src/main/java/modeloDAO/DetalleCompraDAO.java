package modeloDAO;

import config.Conexion;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import modelo.DetalleCompra;

public class DetalleCompraDAO {

    public int add(DetalleCompra detalle) {
        int resultado = 0;
        String sql = "INSERT INTO detalle_compra (compra_id, producto_id, cantidad, precio_unitario, total_articulo) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = Conexion.Conectar().prepareStatement(sql);
            ps.setInt(1, detalle.getCompraId());
            ps.setInt(2, detalle.getProductoId());
            ps.setInt(3, detalle.getCantidad());
            ps.setDouble(4, detalle.getPrecioUnitario());
            ps.setDouble(5, detalle.getTotalArticulo());
            resultado = ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al agregar detalle de compra: " + e);
        }

        Conexion.cerrarConexion();
        return resultado;
    }
}
