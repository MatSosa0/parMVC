package modeloDAO;

import java.util.List;
import modelo.Proveedor;

public interface InterfazProveedorDAO {
    public List<Proveedor> getProveedores();
    public Proveedor getId(int id);
    public int add(Proveedor proveedor);
    public int update(Proveedor proveedor);
    public int delete(int id);
}
