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
        List<DetalleVenta> detalles = new ArrayList<>();
        String sql = "SELECT dv.*, p.nombre as producto_nombre FROM detalle_venta dv " +
                     "JOIN productos p ON dv.producto_id = p.id WHERE dv.venta_id = ?";
        
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, ventaId);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DetalleVenta detalle = new DetalleVenta();
                    detalle.setId(rs.getInt("id"));
                    detalle.setVentaId(rs.getInt("venta_id"));
                    detalle.setProductoId(rs.getInt("producto_id"));
                    detalle.setCantidad(rs.getInt("cantidad"));
                    detalle.setPrecioUnitario(rs.getDouble("precio_unitario"));
                    detalle.setTotalArticulo(rs.getDouble("total_articulo"));
                    
                    detalles.add(detalle);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener detalles: " + e);
        } finally {
            Conexion.cerrarConexion();
        }
        
        return detalles;
    }
    
    public boolean addDetalles(List<DetalleVenta> detalles, int ventaId) {
        String sql = "INSERT INTO detalle_venta(venta_id, producto_id, cantidad, precio_unitario, total_articulo) " +
                     "VALUES (?, ?, ?, ?, ?)";
        String actualizarStock = "UPDATE productos SET stock = stock - ? WHERE id = ?";
        
        try {
            conexion.setAutoCommit(false);
            
            // Registrar todos los detalles
            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
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
            try (PreparedStatement ps = conexion.prepareStatement(actualizarStock)) {
                for (DetalleVenta detalle : detalles) {
                    ps.setInt(1, detalle.getCantidad());
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
            System.err.println("Error al agregar detalles: " + e);
            return false;
        } finally {
            try {
                conexion.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("Error al restaurar autoCommit: " + e);
            }
            Conexion.cerrarConexion();
        }
    }
    
    public boolean updateDetalles(List<DetalleVenta> detalles, int ventaId) {
        String deleteSql = "DELETE FROM detalle_venta WHERE venta_id = ?";
        String insertSql = "INSERT INTO detalle_venta(venta_id, producto_id, cantidad, precio_unitario, total_articulo) " +
                          "VALUES (?, ?, ?, ?, ?)";
        String ajusteStock = "UPDATE productos SET stock = stock + ? WHERE id = ?";
        
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
            Conexion.cerrarConexion();
        }
    }
    
    public boolean deleteDetalles(int ventaId) {
        String sql = "DELETE FROM detalle_venta WHERE venta_id = ?";
        String ajustarStock = "UPDATE productos SET stock = stock + ? WHERE id = ?";
        
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
            Conexion.cerrarConexion();
        }
    }
    
    public boolean verificarStockDisponible(int productoId, int cantidad) {
        String sql = "SELECT stock FROM productos WHERE id = ? AND stock >= ?";
        
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
            Conexion.cerrarConexion();
        }
    }
}