package modeloDAO;

import config.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Cliente;

public class ClienteDAO implements InterfazClienteDAO {

    @Override
    public List<Cliente> getClientes() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM cliente";
        try {
            PreparedStatement ps = Conexion.Conectar().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Cliente c = new Cliente();
                c.setId(rs.getInt("id"));
                c.setNombre(rs.getString("nombre"));
                c.setCedula(rs.getString("cedula"));
                c.setCorreo(rs.getString("correo"));
                c.setTelefono(rs.getString("telefono"));
                c.setDireccion(rs.getString("direccion"));
                lista.add(c);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar clientes: " + e);
        }

        Conexion.cerrarConexion();
        return lista;
    }

    @Override
    public Cliente getId(int id) {
        Cliente c = new Cliente();
        String sql = "SELECT * FROM cliente WHERE id = ?";
        try {
            PreparedStatement ps = Conexion.Conectar().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                c.setId(rs.getInt("id"));
                c.setNombre(rs.getString("nombre"));
                c.setCedula(rs.getString("cedula"));
                c.setCorreo(rs.getString("correo"));
                c.setTelefono(rs.getString("telefono"));
                c.setDireccion(rs.getString("direccion"));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener cliente por ID: " + e);
        }

        Conexion.cerrarConexion();
        return c;
    }

    @Override
    public int add(Cliente cliente) {
        int resultado = 0;
        String sql = "INSERT INTO cliente (nombre, cedula, correo, telefono, direccion) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = Conexion.Conectar().prepareStatement(sql);
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getCedula());
            ps.setString(3, cliente.getCorreo());
            ps.setString(4, cliente.getTelefono());
            ps.setString(5, cliente.getDireccion());
            resultado = ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al agregar cliente: " + e);
        }

        Conexion.cerrarConexion();
        return resultado;
    }

    @Override
    public int update(Cliente cliente) {
        int resultado = 0;
        String sql = "UPDATE cliente SET nombre = ?, cedula = ?, correo = ?, telefono = ?, direccion = ? WHERE id = ?";
        try {
            PreparedStatement ps = Conexion.Conectar().prepareStatement(sql);
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getCedula());
            ps.setString(3, cliente.getCorreo());
            ps.setString(4, cliente.getTelefono());
            ps.setString(5, cliente.getDireccion());
            ps.setInt(6, cliente.getId());
            resultado = ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar cliente: " + e);
        }

        Conexion.cerrarConexion();
        return resultado;
    }

    @Override
    public int delete(int id) {
        int resultado = 0;
        String sql = "DELETE FROM cliente WHERE id = ?";
        try {
            PreparedStatement ps = Conexion.Conectar().prepareStatement(sql);
            ps.setInt(1, id);
            resultado = ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar cliente: " + e);
        }

        Conexion.cerrarConexion();
        return resultado;
    }
}
