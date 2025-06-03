/*
  En el paquete "modelo" se encuentran las clases que representan las entidades del dominio de la aplicación. 
*/
package modelo;

public class Usuario {
    
    private Integer id;
    private String nombre;
    private String contrasenia;
    private Integer administrador;

    public Usuario(String nombre, String contrasenia, Integer administrador) {
        this.nombre = nombre;
        this.contrasenia = contrasenia;
        this.administrador = administrador;
    }
    
    public Usuario(Integer id, String nombre, String contrasenia, Integer administrador) {
        this.id = id;
        this.nombre = nombre;
        this.contrasenia = contrasenia;
        this.administrador = administrador;
    }

    public Usuario() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public Integer getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Integer administrador) {
        this.administrador = administrador;
    }
    
}
