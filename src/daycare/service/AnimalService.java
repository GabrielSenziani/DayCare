package daycare.service;

import daycare.dao.ClienteDAO;
import daycare.dao.AnimalDAO;
import daycare.model.Animal;
import daycare.model.Cliente;
import java.sql.SQLException;
import java.util.List;

public class AnimalService {

    private final AnimalDAO animalDAO = new AnimalDAO();
    private final ClienteDAO clienteDAO = new ClienteDAO();

    public void cadastrar(Animal animal) throws SQLException {
        validarClienteResponsavel(animal.getClienteId());
        animalDAO.inserir(animal);
    }

    public void atualizar(Animal animal) throws SQLException {
        validarClienteResponsavel(animal.getClienteId());
        animalDAO.atualizar(animal);
    }

    public void excluir(int id) throws SQLException {
        animalDAO.excluir(id);
    }

    public Animal buscarPorId(int id) throws SQLException {
        return animalDAO.buscarPorId(id);
    }

    public List<Animal> listarTodos() throws SQLException {
        return animalDAO.listarTodos();
    }

    public List<Animal> listarPorCliente(int clienteId) throws SQLException {
        return animalDAO.listarPorCliente(clienteId);
    }

    private void validarClienteResponsavel(int clienteId) throws SQLException {
        Cliente cliente = clienteDAO.buscarPorId(clienteId);
        if (cliente == null) {
            throw new BusinessException("Cliente responsável não encontrado. Informe um cliente válido.");
        }
    }
}
