package daycare.service;

import daycare.dao.ClienteDAO;
import daycare.dao.AnimalDAO;
import daycare.model.Cliente;
import java.sql.SQLException;
import java.util.List;

public class ClienteService {

    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final AnimalDAO animalDAO = new AnimalDAO();

    public void cadastrar(Cliente cliente) throws SQLException {
        validarCpfObrigatorio(cliente);
        validarCpfUnico(cliente.getCpf(), cliente.getId());
        clienteDAO.inserir(cliente);
    }

    public void atualizar(Cliente cliente) throws SQLException {
        validarCpfObrigatorio(cliente);
        validarCpfUnico(cliente.getCpf(), cliente.getId());
        clienteDAO.atualizar(cliente);
    }

    public void excluir(int id) throws SQLException {
        List<daycare.model.Animal> animais = animalDAO.listarPorCliente(id);
        if (!animais.isEmpty()) {
            throw new BusinessException("Não é possível excluir o cliente: existem animais vinculados a ele.");
        }
        clienteDAO.excluir(id);
    }

    public Cliente buscarPorId(int id) throws SQLException {
        return clienteDAO.buscarPorId(id);
    }

    public Cliente buscarPorCpf(String cpf) throws SQLException {
        return clienteDAO.buscarPorCpf(cpf);
    }

    public List<Cliente> listarTodos() throws SQLException {
        return clienteDAO.listarTodos();
    }

    private void validarCpfObrigatorio(Cliente cliente) {
        if (cliente.getCpf() == null || cliente.getCpf().trim().isEmpty()) {
            throw new BusinessException("CPF é obrigatório.");
        }
    }

    private void validarCpfUnico(String cpf, int idExcluido) throws SQLException {
        Cliente existente = clienteDAO.buscarPorCpf(cpf);
        if (existente != null && existente.getId() != idExcluido) {
            throw new BusinessException("CPF já cadastrado para outro cliente.");
        }
    }
}
