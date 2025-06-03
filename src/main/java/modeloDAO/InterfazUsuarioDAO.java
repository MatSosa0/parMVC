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
import modelo.Usuario;

public interface InterfazUsuarioDAO {
    public List<Usuario> getUsuarios();
    public Usuario getId(int id);
    public Usuario getUsuario(String usuario);
    public Usuario getUsuario(String usuario, String contrasenia);
    public int add(Usuario usuario);
    public int update(Usuario usuario);
    public int delete(int id);
}
