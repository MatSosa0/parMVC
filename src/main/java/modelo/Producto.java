/*
  En el paquete "modelo" se encuentran las clases que representan las entidades del dominio de la aplicación. 
*/
package modelo;

public class Producto {
    private Integer id;
    private String nombre;
    private String descripcion;
    private Integer unidades;
    private Double costo;
    private Double precio;
    private String categoria;

    public Producto() {
    }

    public Producto(String nombre, String descripcion, Integer unidades, Double costo, Double precio, String categoria) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.unidades = unidades;
        this.costo = costo;
        this.precio = precio;
        this.categoria = categoria;
    }
    
    public Producto(Integer id, String nombre, String descripcion, Integer unidades, Double costo, Double precio, String categoria) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.unidades = unidades;
        this.costo = costo;
        this.precio = precio;
        this.categoria = categoria;
    }

    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Integer getUnidades() {
        return unidades;
    }

    public Double getCosto() {
        return costo;
    }

    public Double getPrecio() {
        return precio;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setUnidades(Integer unidades) {
        this.unidades = unidades;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    
}
