/*Un Modelo DAO (Data Access Object) es un patrón de diseño utilizado en el desarrollo de aplicaciones de software para separar la lógica de acceso a datos de la lógica de negocio. 
  un Modelo DAO es una clase o conjunto de clases que se utilizan para interactuar con la capa de persistencia de datos, como una base de datos, archivos o servicios web.
  La principal función de un Modelo DAO en Java es proporcionar una interfaz entre la lógica de negocio de la aplicación y 
  los datos almacenados en una fuente de datos. 
  Esto permite que el resto de la aplicación no necesite conocer los detalles específicos de cómo se accede a los datos, 
  lo que facilita la modificación de la capa de persistencia sin afectar otras partes de la aplicación.
*/

/*
  En el paquete "modeloDAO" concentra a las clases que implementan el patrón DAO (Data Access Object). 
  Estas clases se utilizan para interactuar con la capa de persistencia de datos.
*/
package modeloDAO;

import config.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import modelo.Producto;

public class ProductoDAO implements InterfazProductoDAO {

    @Override
    public List<Producto> getProductos() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM producto;";
        try {
            PreparedStatement ps = Conexion.Conectar().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Producto producto = new Producto();
                producto.setId(rs.getInt("id"));
                producto.setNombre(rs.getString("nombre"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setUnidades(rs.getInt("unidades"));
                producto.setCosto(rs.getDouble("costo"));
                producto.setPrecio(rs.getDouble("Precio"));
                producto.setCategoria(rs.getString("categoria"));
                productos.add(producto);
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e);
        }
        
        //Conexion.cerrarConexion();
        
        return productos;
    }
    
     public List<Producto> getProductosConStock() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM producto where unidades > 0;";
        try {
            PreparedStatement ps = Conexion.Conectar().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Producto producto = new Producto();
                producto.setId(rs.getInt("id"));
                producto.setNombre(rs.getString("nombre"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setUnidades(rs.getInt("unidades"));
                producto.setCosto(rs.getDouble("costo"));
                producto.setPrecio(rs.getDouble("Precio"));
                producto.setCategoria(rs.getString("categoria"));
                productos.add(producto);
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e);
        }
        
        //Conexion.cerrarConexion();
        
        return productos;
    }

    @Override
    public Producto getId(int id) {
        String sql = "SELECT * FROM producto WHERE id = ?;";
        Producto producto = new Producto();
        try {
            PreparedStatement ps = Conexion.Conectar().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                producto.setId(rs.getInt(1));
                producto.setNombre(rs.getString(2));
                producto.setDescripcion(rs.getString(3));
                producto.setUnidades(rs.getInt(4));
                producto.setCosto(rs.getDouble(5));
                producto.setPrecio(rs.getDouble(6));
                producto.setCategoria(rs.getString(7));
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e);
        }
        
       // Conexion.cerrarConexion();
        
        return producto;
    }

    @Override
    public int add(Producto producto) {
        int resultado = 0;
        String sql = "INSERT INTO producto(nombre, descripcion, unidades, costo, precio, categoria) VALUES (?, ?, ?, ?, ?, ?);";
        try {
            PreparedStatement ps = Conexion.Conectar().prepareStatement(sql);
            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getDescripcion());
            ps.setInt(3, producto.getUnidades());
            ps.setDouble(4, producto.getCosto());
            ps.setDouble(5, producto.getPrecio());
            ps.setString(6, producto.getCategoria());
            resultado = ps.executeUpdate();
        } catch (Exception e) {
            System.err.println("Error al agregar en la base de datos" + e);
        }
        
        //Conexion.cerrarConexion();
        
        return resultado;
    }

    @Override
    public int update(Producto producto) {
        int resultado = 0;
        String sql = "UPDATE producto SET nombre = ?, descripcion = ?, unidades = ?, costo = ?, precio = ?, categoria = ? WHERE id = ?;";
        try {
            PreparedStatement ps = Conexion.Conectar().prepareStatement(sql);
            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getDescripcion());
            ps.setInt(3, producto.getUnidades());
            ps.setDouble(4, producto.getCosto());
            ps.setDouble(5, producto.getPrecio());
            ps.setString(6, producto.getCategoria());
            ps.setInt(7, producto.getId());
            resultado = ps.executeUpdate();
        } catch (Exception e) {
            System.err.println("Error al agregar en la base de datos" + e);
        }
        
        //Conexion.cerrarConexion();
        
        return resultado;
    }

    @Override
    public int delete(int id) {
        int resultado = 0;
        String sql = "DELETE FROM producto WHERE id = "+id;
        try {
            PreparedStatement ps = Conexion.Conectar().prepareStatement(sql);
            resultado = ps.executeUpdate();
        } catch (Exception e) {
            System.err.println("Ha ocurrido un error durante el borrado" + e);
        }
        
        //Conexion.cerrarConexion();
        
        return resultado;
    }
    
    public Producto getProductoPorId(int id) {
    Producto producto = null;
    String sql = "SELECT * FROM producto WHERE id = ?";
    try {
        PreparedStatement ps = Conexion.Conectar().prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            producto = new Producto();
            producto.setId(rs.getInt("id"));
            producto.setNombre(rs.getString("nombre"));
            producto.setDescripcion(rs.getString("descripcion"));
            producto.setUnidades(rs.getInt("unidades"));
            producto.setCosto(rs.getDouble("costo"));
            producto.setPrecio(rs.getDouble("precio"));
            producto.setCategoria(rs.getString("categoria"));
        }
    } catch (SQLException e) {
        System.err.println("Error al obtener producto por ID: " + e);
    } finally {
        //Conexion.cerrarConexion();
    }
    return producto;
}

    public int actualizarStock(int idProducto, int nuevoStock) {
    int resultado = 0;
    String sql = "UPDATE producto SET unidades = ? WHERE id = ?";
    try {
        PreparedStatement ps = Conexion.Conectar().prepareStatement(sql);
        ps.setInt(1, nuevoStock);
        ps.setInt(2, idProducto);
        resultado = ps.executeUpdate();
    } catch (SQLException e) {
        System.err.println("Error al actualizar stock del producto: " + e);
    } finally {
        //Conexion.cerrarConexion();
    }
    return resultado;
}

    public List<Map<String, Object>> getInventario() {
        List<Map<String, Object>> inventario = new ArrayList<>();
        String sql = "SELECT id, nombre, descripcion, categoria, unidades, precio, (unidades * precio) AS total_stock FROM producto";

        try {
            PreparedStatement ps = Conexion.Conectar().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Map<String, Object> fila = new HashMap<>();
                fila.put("id", rs.getInt("id"));
                fila.put("nombre", rs.getString("nombre"));
                fila.put("descripcion", rs.getString("descripcion"));
                fila.put("categoria", rs.getString("categoria"));
                fila.put("unidades", rs.getInt("unidades"));
                fila.put("precio", rs.getDouble("precio"));
                fila.put("total_stock", rs.getDouble("total_stock"));
                inventario.add(fila);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener inventario: " + e.getMessage());
        }

        //Conexion.cerrarConexion();
        return inventario;
    }
    
    public List<Map<String, Object>> getProductosMasVendidos() {
        List<Map<String, Object>> productos = new ArrayList<>();
        String sql = "SELECT p.id, p.nombre, SUM(dv.cantidad) AS total_vendido " +
                     "FROM producto p " +
                     "JOIN detalle_venta dv ON p.id = dv.producto_id " +
                     "GROUP BY p.id, p.nombre " +
                     "ORDER BY total_vendido DESC";

        try {
            PreparedStatement ps = Conexion.Conectar().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Map<String, Object> fila = new HashMap<>();
                fila.put("id", rs.getInt("id"));
                fila.put("nombre", rs.getString("nombre"));
                fila.put("total_vendido", rs.getInt("total_vendido"));
                productos.add(fila);
            }

        } catch (Exception e) {
            System.err.println("Error en getProductosMasVendidos: " + e.getMessage());
        }

        //Conexion.cerrarConexion();
        return productos;
    }
    
    public List<Map<String, Object>> getUtilidadesPorProducto() {
        List<Map<String, Object>> lista = new ArrayList<>();
        String sql = "SELECT p.id, p.nombre, " +
                     "SUM(dv.cantidad) AS total_vendido, " +
                     "SUM(dv.cantidad * p.costo) AS costo_total, " +
                     "SUM(dv.total_articulo) AS ingreso_total, " +
                     "SUM(dv.total_articulo) - SUM(dv.cantidad * p.costo) AS utilidad " +
                     "FROM producto p " +
                     "JOIN detalle_venta dv ON p.id = dv.producto_id " +
                     "GROUP BY p.id, p.nombre " +
                     "ORDER BY utilidad DESC";

        try {
            PreparedStatement ps = Conexion.Conectar().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Map<String, Object> fila = new HashMap<>();
                fila.put("id", rs.getInt("id"));
                fila.put("nombre", rs.getString("nombre"));
                fila.put("total_vendido", rs.getInt("total_vendido"));
                fila.put("costo_total", rs.getDouble("costo_total"));
                fila.put("ingreso_total", rs.getDouble("ingreso_total"));
                fila.put("utilidad", rs.getDouble("utilidad"));
                lista.add(fila);
            }

        } catch (Exception e) {
            System.err.println("Error en getUtilidadesPorProducto: " + e.getMessage());
        }

        //Conexion.cerrarConexion();
        return lista;
    }
}
