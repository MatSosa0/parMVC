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
import java.util.List;
import modelo.Usuario;

public class UsuarioDAO implements InterfazUsuarioDAO{

    @Override
    public List<Usuario> getUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuario;";
        try {
            PreparedStatement ps = Conexion.Conectar().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setContrasenia(rs.getString("contrasenia"));
                usuario.setAdministrador(rs.getInt("administrador"));
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e);
        }
        
        Conexion.cerrarConexion();
        
        return usuarios;
    }
    
    @Override
    public Usuario getUsuario(String nombre) {
        String sql = "SELECT * FROM usuario WHERE nombre = ?;";
        Usuario usuario = new Usuario();
        try {
            PreparedStatement ps = Conexion.Conectar().prepareStatement(sql);
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                usuario.setNombre(rs.getString(2));
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e);
        }
        
        Conexion.cerrarConexion();
        
        return usuario;
    }
    
    @Override
    public Usuario getUsuario(String nombre, String contrasenia){
        String sql = "SELECT * FROM usuario WHERE nombre = ? AND contrasenia = ?;";
        Usuario usuario = new Usuario();
        try {
            PreparedStatement ps = Conexion.Conectar().prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, contrasenia);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                usuario.setId(rs.getInt(1));
                usuario.setNombre(rs.getString(2));
                usuario.setContrasenia(rs.getString(3));
                usuario.setAdministrador(rs.getInt(4));
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e);
        }
        
        Conexion.cerrarConexion();
        
        return usuario;
    }
    
        @Override
    public Usuario getId(int id) {
        String sql = "SELECT * FROM usuario WHERE id = ?;";
        Usuario usuario = new Usuario();
        try {
            PreparedStatement ps = Conexion.Conectar().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                usuario.setId(rs.getInt(1));
                usuario.setNombre(rs.getString(2));
                usuario.setContrasenia(rs.getString(3));
                usuario.setAdministrador(rs.getInt(4));
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e);
        }
        
        Conexion.cerrarConexion();
        
        return usuario;
    }

    @Override
    public int add(Usuario usuario) {
        int resultado = 0;
        String sql = "INSERT INTO usuario(nombre, contrasenia, administrador) VALUES (?, ?, ?);";
        try {
            PreparedStatement ps = Conexion.Conectar().prepareStatement(sql);
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getContrasenia());
            ps.setInt(3, usuario.getAdministrador());
            resultado = ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al agregar en la base de datos" + e);
        }
        
        Conexion.cerrarConexion();
        
        return resultado;
    }

    @Override
    public int update(Usuario usuario) {
        int resultado = 0;
        String sql = "UPDATE usuario SET nombre = ?, contrasenia = ?, administrador = ? WHERE id = ?;";
        try {
            PreparedStatement ps = Conexion.Conectar().prepareStatement(sql);
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getContrasenia());
            ps.setInt(3, usuario.getAdministrador());
            ps.setInt(4, usuario.getId());
            resultado = ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al agregar en la base de datos" + e);
        }
        
        Conexion.cerrarConexion();
        
        return resultado;
    }

    @Override
    public int delete(int id) {
        int resultado = 0;
        String sql = "DELETE FROM usuario WHERE id = "+ id;
        try {
            PreparedStatement ps = Conexion.Conectar().prepareStatement(sql);
            resultado = ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Ha ocurrido un error durante el borrado" + e);
        }
        
        Conexion.cerrarConexion();
        
        return resultado;
    }
}
