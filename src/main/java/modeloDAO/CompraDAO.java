package modeloDAO;

import config.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.Compra;

public class CompraDAO {

    public int add(Compra compra) {
        int idGenerado = 0;
        String sql = "INSERT INTO compra (numero_factura, fecha, proveedor_id, forma_pago, total_factura) " +
                     "VALUES (?, NOW(), ?, ?, ?)";

        try {
            PreparedStatement ps = Conexion.Conectar().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, compra.getNumeroFactura());
            ps.setInt(2, compra.getProveedorId());
            ps.setString(3, compra.getFormaPago());
            ps.setDouble(4, compra.getTotalFactura());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                idGenerado = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error al agregar compra: " + e);
        }

        Conexion.cerrarConexion();
        return idGenerado;
    }
    public Compra getCompraPorId(int id) {
    Compra compra = null;
    String sql = "SELECT * FROM compra WHERE id = ?";
    try {
        PreparedStatement ps = Conexion.Conectar().prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            compra = new Compra();
            compra.setId(rs.getInt("id"));
            compra.setProveedorId(rs.getInt("proveedor_id"));
            compra.setFecha(rs.getDate("fecha"));
            compra.setFormaPago(rs.getString("forma_pago"));
            compra.setTotalFactura(rs.getDouble("total_factura"));
        }
    } catch (SQLException e) {
        System.err.println("Error al obtener compra por ID: " + e.getMessage());
    } finally {
        Conexion.cerrarConexion();
    }
    return compra;
}

}
