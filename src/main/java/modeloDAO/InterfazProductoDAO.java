/*
  En el paquete "modeloDAO" concentra a las clases que implementan el patrón DAO (Data Access Object). 
  Estas clases se utilizan para interactuar con la capa de persistencia de datos.
*/

/*
  En el paquete "modeloDAO" concentra a las clases que implementan el patrón DAO (Data Access Object). 
  Estas clases se utilizan para interactuar con la capa de persistencia de datos.
*/
package modeloDAO;

import java.util.List;
import modelo.Producto;

public interface InterfazProductoDAO {
    public List<Producto> getProductos();
    public Producto getId(int id);
    public int add(Producto producto);
    public int update(Producto producto);
    public int delete(int id);
}
