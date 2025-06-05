package modeloDAO;

import config.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Proveedor;

public class ProveedorDAO implements InterfazProveedorDAO {

    @Override
    public List<Proveedor> getProveedores() {
        List<Proveedor> lista = new ArrayList<>();
        String sql = "SELECT * FROM proveedor";
        try {
            PreparedStatement ps = Conexion.Conectar().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Proveedor p = new Proveedor();
                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("nombre"));
                p.setRuc(rs.getString("ruc"));
                p.setCorreo(rs.getString("correo"));
                p.setTelefono(rs.getString("telefono"));
                p.setDireccion(rs.getString("direccion"));
                lista.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar proveedores: " + e);
        }
        Conexion.cerrarConexion();
        return lista;
    }

    @Override
    public Proveedor getId(int id) {
        Proveedor p = new Proveedor();
        String sql = "SELECT * FROM proveedor WHERE id = ?";
        try {
            PreparedStatement ps = Conexion.Conectar().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("nombre"));
                p.setRuc(rs.getString("ruc"));
                p.setCorreo(rs.getString("correo"));
                p.setTelefono(rs.getString("telefono"));
                p.setDireccion(rs.getString("direccion"));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener proveedor por ID: " + e);
        }
        Conexion.cerrarConexion();
        return p;
    }

    @Override
    public int add(Proveedor proveedor) {
        int resultado = 0;
        String sql = "INSERT INTO proveedor (nombre, ruc, correo, telefono, direccion) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = Conexion.Conectar().prepareStatement(sql);
            ps.setString(1, proveedor.getNombre());
            ps.setString(2, proveedor.getRuc());
            ps.setString(3, proveedor.getCorreo());
            ps.setString(4, proveedor.getTelefono());
            ps.setString(5, proveedor.getDireccion());
            resultado = ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al agregar proveedor: " + e);
        }
        Conexion.cerrarConexion();
        return resultado;
    }

    @Override
    public int update(Proveedor proveedor) {
        int resultado = 0;
        String sql = "UPDATE proveedor SET nombre=?, ruc=?, correo=?, telefono=?, direccion=? WHERE id=?";
        try {
            PreparedStatement ps = Conexion.Conectar().prepareStatement(sql);
            ps.setString(1, proveedor.getNombre());
            ps.setString(2, proveedor.getRuc());
            ps.setString(3, proveedor.getCorreo());
            ps.setString(4, proveedor.getTelefono());
            ps.setString(5, proveedor.getDireccion());
            ps.setInt(6, proveedor.getId());
            resultado = ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar proveedor: " + e);
        }
        Conexion.cerrarConexion();
        return resultado;
    }

    @Override
    public int delete(int id) {
        int resultado = 0;
        String sql = "DELETE FROM proveedor WHERE id = ?";
        try {
            PreparedStatement ps = Conexion.Conectar().prepareStatement(sql);
            ps.setInt(1, id);
            resultado = ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar proveedor: " + e);
        }
        Conexion.cerrarConexion();
        return resultado;
    }
}
