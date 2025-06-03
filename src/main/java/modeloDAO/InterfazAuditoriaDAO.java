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
import modelo.Auditoria;

public interface InterfazAuditoriaDAO {
    
    public List<Auditoria> getRegistros();
    
    public void agregarRegistro(Auditoria auditoria);
}
