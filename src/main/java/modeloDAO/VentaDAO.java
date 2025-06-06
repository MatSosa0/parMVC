package modeloDAO;

import config.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.Venta;

public class VentaDAO {
    private Connection conexion;
    
    public VentaDAO() {
        this.conexion = Conexion.Conectar();
    }
    
    public List<Venta> getVentas() {
        List<Venta> ventas = new ArrayList<>();
        String sql = "SELECT v.*, c.nombre as cliente_nombre FROM venta v JOIN clientes c ON v.cliente_id = c.id";
        
        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Venta venta = new Venta();
                venta.setId(rs.getInt("id"));
                venta.setNumeroFactura(rs.getString("numero_factura"));
                venta.setFecha(rs.getTimestamp("fecha"));
                venta.setClienteId(rs.getInt("cliente_id"));
                venta.setFormaPago(rs.getString("forma_pago"));
                venta.setTotalFactura(rs.getDouble("total_factura"));
                
                ventas.add(venta);
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e);
        } finally {
            Conexion.cerrarConexion();
        }
        
        return ventas;
    }
    
    public Venta getId(int id) {
        String sql = "SELECT * FROM venta WHERE id = ?";
        Venta venta = new Venta();
        
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    venta.setId(rs.getInt("id"));
                    venta.setNumeroFactura(rs.getString("numero_factura"));
                    venta.setFecha(rs.getTimestamp("fecha"));
                    venta.setClienteId(rs.getInt("cliente_id"));
                    venta.setFormaPago(rs.getString("forma_pago"));
                    venta.setTotalFactura(rs.getDouble("total_factura"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e);
        } finally {
            Conexion.cerrarConexion();
        }
        
        return venta;
    }
    
    public int add(Venta venta) {
        int resultado = 0;
        String sql = "INSERT INTO venta(numero_factura, fecha, cliente_id, forma_pago, total_factura) VALUES (?, ?, ?, ?, ?)";
        
        try {
            conexion.setAutoCommit(false);
            
            try (PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, venta.getNumeroFactura());
                ps.setTimestamp(2, new java.sql.Timestamp(venta.getFecha().getTime()));
                ps.setInt(3, venta.getClienteId());
                ps.setString(4, venta.getFormaPago());
                ps.setDouble(5, venta.getTotalFactura());
                
                resultado = ps.executeUpdate();
                
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        venta.setId(rs.getInt(1));
                    }
                }
            }
            
            conexion.commit();
        } catch (SQLException e) {
            try {
                conexion.rollback();
            } catch (SQLException ex) {
                System.err.println("Error en rollback: " + ex);
            }
            System.err.println("Error al agregar venta: " + e);
        } finally {
            try {
                conexion.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("Error al restaurar autoCommit: " + e);
            }
            Conexion.cerrarConexion();
        }
        
        return resultado;
    }
    
    // En tu VentaDAO.java
    public int update(Venta venta) {
        int resultado = 0;
        String sql = "UPDATE venta SET numero_factura=?, fecha=?, cliente_id=?, forma_pago=?, total_factura=? WHERE id=?";
        
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, venta.getNumeroFactura());
            ps.setTimestamp(2, new java.sql.Timestamp(venta.getFecha().getTime()));
            ps.setInt(3, venta.getClienteId());
            ps.setString(4, venta.getFormaPago());
            ps.setDouble(5, venta.getTotalFactura());
            ps.setInt(6, venta.getId());
            
            resultado = ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar venta: " + e);
        } finally {
            Conexion.cerrarConexion();
        }
        
        return resultado;
    }

    public int delete(int id) {
        int resultado = 0;
        String sql = "DELETE FROM venta WHERE id=?";
        
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            resultado = ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar venta: " + e);
        } finally {
            Conexion.cerrarConexion();
        }
        
        return resultado;
    }
}