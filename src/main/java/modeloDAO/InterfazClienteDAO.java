package modeloDAO;

import java.util.List;
import modelo.Cliente;

public interface InterfazClienteDAO {
    public List<Cliente> getClientes();
    public Cliente getId(int id);
    public int add(Cliente cliente);
    public int update(Cliente cliente);
    public int delete(int id);
}
