package modeloDAO;

import config.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
    public List<DetalleCompra> getDetallesPorCompra(int idCompra) {
    List<DetalleCompra> lista = new ArrayList<>();
    String sql = "SELECT d.*, p.nombre FROM detalle_compra d INNER JOIN producto p ON d.producto_id = p.id WHERE d.compra_id = ?";
    try {
        PreparedStatement ps = Conexion.Conectar().prepareStatement(sql);
        ps.setInt(1, idCompra);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            DetalleCompra detalle = new DetalleCompra();
            detalle.setId(rs.getInt("id"));
            detalle.setCompraId(rs.getInt("compra_id"));
            detalle.setProductoId(rs.getInt("producto_id"));
            detalle.setCantidad(rs.getInt("cantidad"));
            detalle.setPrecioUnitario(rs.getDouble("precio_unitario"));
            detalle.setTotalArticulo(rs.getDouble("total_articulo"));
            detalle.setNombreProducto(rs.getString("nombre")); // <- Este es importante
            lista.add(detalle);
        }
    } catch (SQLException e) {
        System.err.println("Error al obtener detalles de compra: " + e.getMessage());
    } finally {
        Conexion.cerrarConexion();
    }
    return lista;
}

}
