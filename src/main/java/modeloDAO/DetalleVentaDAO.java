package modeloDAO;

import config.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.DetalleVenta;

public class DetalleVentaDAO {

    private Connection conexion;

    public DetalleVentaDAO() {
        this.conexion = Conexion.Conectar();
    }

    public List<DetalleVenta> getDetallesPorVenta(int ventaId) {
    List<DetalleVenta> lista = new ArrayList<>();
    String sql = "SELECT dv.*, p.nombre AS nombreProducto " +
                 "FROM detalle_venta dv " +
                 "JOIN producto p ON dv.producto_id = p.id " +
                 "WHERE dv.venta_id = ?";

    try (Connection con = Conexion.Conectar();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, ventaId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            DetalleVenta detalle = new DetalleVenta();
            detalle.setProductoId(rs.getInt("producto_id"));
            detalle.setCantidad(rs.getInt("cantidad"));
            detalle.setPrecioUnitario(rs.getDouble("precio_unitario"));
            detalle.setTotalArticulo(rs.getDouble("total_articulo"));
            detalle.setNombreProducto(rs.getString("nombreProducto"));
            detalle.setVentaId(ventaId);

            lista.add(detalle);
        }

    } catch (SQLException e) {
        System.err.println("Error al obtener detalles de venta: " + e.getMessage());
    }

    return lista;
}



    public void agregar(DetalleVenta d) {
        String sql = "INSERT INTO detalle_venta(venta_id, producto_id, cantidad, precio_unitario, total_articulo) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = Conexion.Conectar().prepareStatement(sql);
            ps.setInt(1, d.getVentaId());
            ps.setInt(2, d.getProductoId());
            ps.setInt(3, d.getCantidad());
            ps.setDouble(4, d.getPrecioUnitario());
            ps.setDouble(5, d.getTotalArticulo());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al agregar detalle de venta: " + e.getMessage());
        }
    }

    public boolean addDetalles(List<DetalleVenta> detalles, int ventaId) {
        String sql = "INSERT INTO detalle_venta(venta_id, producto_id, cantidad, precio_unitario, total_articulo) "
                + "VALUES (?, ?, ?, ?, ?)";
        String actualizarStock = "UPDATE producto SET stock = stock - ? WHERE id = ?";

        try (Connection con = Conexion.Conectar()) {
            con.setAutoCommit(false); // Inicia transacción

            // Registrar todos los detalles
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                for (DetalleVenta detalle : detalles) {
                    ps.setInt(1, ventaId);
                    ps.setInt(2, detalle.getProductoId());
                    ps.setInt(3, detalle.getCantidad());
                    ps.setDouble(4, detalle.getPrecioUnitario());
                    ps.setDouble(5, detalle.getTotalArticulo());
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            // Actualizar stock de productos
            try (PreparedStatement ps = con.prepareStatement(actualizarStock)) {
                for (DetalleVenta detalle : detalles) {
                    ps.setInt(1, detalle.getCantidad());
                    ps.setInt(2, detalle.getProductoId());
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            con.commit();
            return true;

        } catch (SQLException e) {
            System.err.println("Error al agregar detalles: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateDetalles(List<DetalleVenta> detalles, int ventaId) {
        String deleteSql = "DELETE FROM detalle_venta WHERE venta_id = ?";
        String insertSql = "INSERT INTO detalle_venta(venta_id, producto_id, cantidad, precio_unitario, total_articulo) "
                + "VALUES (?, ?, ?, ?, ?)";
        String ajusteStock = "UPDATE producto SET stock = stock + ? WHERE id = ?";

        try {
            conexion.setAutoCommit(false);

            // 1. Obtener detalles antiguos para ajustar stock
            List<DetalleVenta> detallesAntiguos = getDetallesPorVenta(ventaId);

            // 2. Ajustar stock (devolver lo que se había quitado)
            try (PreparedStatement ps = conexion.prepareStatement(ajusteStock)) {
                for (DetalleVenta detalle : detallesAntiguos) {
                    ps.setInt(1, detalle.getCantidad());
                    ps.setInt(2, detalle.getProductoId());
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            // 3. Eliminar detalles antiguos
            try (PreparedStatement ps = conexion.prepareStatement(deleteSql)) {
                ps.setInt(1, ventaId);
                ps.executeUpdate();
            }

            // 4. Insertar nuevos detalles
            try (PreparedStatement ps = conexion.prepareStatement(insertSql)) {
                for (DetalleVenta detalle : detalles) {
                    ps.setInt(1, ventaId);
                    ps.setInt(2, detalle.getProductoId());
                    ps.setInt(3, detalle.getCantidad());
                    ps.setDouble(4, detalle.getPrecioUnitario());
                    ps.setDouble(5, detalle.getTotalArticulo());
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            // 5. Actualizar stock con los nuevos valores
            try (PreparedStatement ps = conexion.prepareStatement(ajusteStock)) {
                for (DetalleVenta detalle : detalles) {
                    ps.setInt(1, -detalle.getCantidad()); // Restar del stock
                    ps.setInt(2, detalle.getProductoId());
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            conexion.commit();
            return true;
        } catch (SQLException e) {
            try {
                conexion.rollback();
            } catch (SQLException ex) {
                System.err.println("Error en rollback: " + ex);
            }
            System.err.println("Error al actualizar detalles: " + e);
            return false;
        } finally {
            try {
                conexion.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("Error al restaurar autoCommit: " + e);
            }
            //Conexion.cerrarConexion();
        }
    }

    public boolean deleteDetalles(int ventaId) {
        String sql = "DELETE FROM detalle_venta WHERE venta_id = ?";
        String ajustarStock = "UPDATE producto SET stock = stock + ? WHERE id = ?";

        try {
            conexion.setAutoCommit(false);

            // 1. Obtener detalles para ajustar stock
            List<DetalleVenta> detalles = getDetallesPorVenta(ventaId);

            // 2. Ajustar stock (devolver productos)
            try (PreparedStatement ps = conexion.prepareStatement(ajustarStock)) {
                for (DetalleVenta detalle : detalles) {
                    ps.setInt(1, detalle.getCantidad());
                    ps.setInt(2, detalle.getProductoId());
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            // 3. Eliminar detalles
            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setInt(1, ventaId);
                int resultado = ps.executeUpdate();

                conexion.commit();
                return resultado > 0;
            }
        } catch (SQLException e) {
            try {
                conexion.rollback();
            } catch (SQLException ex) {
                System.err.println("Error en rollback: " + ex);
            }
            System.err.println("Error al eliminar detalles: " + e);
            return false;
        } finally {
            try {
                conexion.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("Error al restaurar autoCommit: " + e);
            }
            //Conexion.cerrarConexion();
        }
    }

    public DetalleVenta getById(int idDetalle) {
        DetalleVenta detalle = null;
        String sql = "SELECT id, id_venta, id_producto, cantidad, precio_unitario, total_articulo FROM detalle_venta WHERE id = ?";
        try (Connection con = Conexion.Conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idDetalle);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    detalle = new DetalleVenta();
                    detalle.setId(rs.getInt("id"));
                    detalle.setVentaId(rs.getInt("id_venta"));
                    detalle.setProductoId(rs.getInt("id_producto"));
                    detalle.setCantidad(rs.getInt("cantidad"));
                    detalle.setPrecioUnitario(rs.getDouble("precio_unitario"));
                    detalle.setTotalArticulo(rs.getDouble("total_articulo"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detalle;
    }

    public boolean deleteById(int idDetalle) {
        String sql = "DELETE FROM detalle_venta WHERE id = ?";
        try (Connection con = Conexion.Conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idDetalle);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean verificarStockDisponible(int productoId, int cantidad) {
        String sql = "SELECT unidades FROM producto WHERE id = ? AND unidades >= ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, productoId);
            ps.setInt(2, cantidad);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // Si hay resultados, hay stock suficiente
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar stock: " + e);
            return false;
        } finally {
            // Conexion.cerrarConexion();
        }
    }
}
