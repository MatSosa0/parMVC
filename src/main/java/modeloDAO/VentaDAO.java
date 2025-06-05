package modeloDAO;

import config.Conexion;
import modelo.Venta;
import modelo.DetalleVenta;
import java.sql.*;
import java.util.List;

public class VentaDAO implements InterfazVentaDAO {

    @Override
    public int registrarVenta(Venta venta) {
        int idGenerado = 0;
        String sql = "INSERT INTO venta (idCliente, total) VALUES (?, ?)";

        try {
            PreparedStatement ps = Conexion.Conectar().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, venta.getIdCliente());
            ps.setDouble(2, venta.getTotal());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                idGenerado = rs.getInt(1);
            }

        } catch (SQLException e) {
            System.err.println("Error al registrar venta: " + e);
        }

        Conexion.cerrarConexion();
        return idGenerado;
    }

    @Override
    public boolean registrarDetalle(List<DetalleVenta> detalles) {
        String sql = "INSERT INTO detalle_venta (idVenta, idProducto, cantidad, precioUnitario, subtotal) VALUES (?, ?, ?, ?, ?)";

        try {
            for (DetalleVenta d : detalles) {
                PreparedStatement ps = Conexion.Conectar().prepareStatement(sql);
                ps.setInt(1, d.getIdVenta());
                ps.setInt(2, d.getIdProducto());
                ps.setInt(3, d.getCantidad());
                ps.setDouble(4, d.getPrecioUnitario());
                ps.setDouble(5, d.getSubtotal());
                ps.executeUpdate();
            }
            Conexion.cerrarConexion();
            return true;

        } catch (SQLException e) {
            System.err.println("Error al registrar detalle venta: " + e);
            Conexion.cerrarConexion();
            return false;
        }
    }
}
