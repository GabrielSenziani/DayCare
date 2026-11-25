package daycare.dao;

import daycare.model.Animal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AnimalDAO {

    public void inserir(Animal animal) throws SQLException {
        String sql = "INSERT INTO animal (nome, especie, raca, data_nascimento, cliente_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, animal.getNome());
            stmt.setString(2, animal.getEspecie());
            stmt.setString(3, animal.getRaca());
            stmt.setString(4, animal.getDataNascimento().toString());
            stmt.setInt(5, animal.getClienteId());
            stmt.executeUpdate();
            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    animal.setId(keys.getInt(1));
                }
            }
        }
    }

    public List<Animal> listarTodos() throws SQLException {
        String sql = "SELECT id, nome, especie, raca, data_nascimento, cliente_id FROM animal ORDER BY nome";
        List<Animal> animais = new ArrayList<>();
        try (Connection conn = Conexao.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                animais.add(mapearAnimal(rs));
            }
        }
        return animais;
    }

    public List<Animal> listarPorCliente(int clienteId) throws SQLException {
        String sql = "SELECT id, nome, especie, raca, data_nascimento, cliente_id FROM animal WHERE cliente_id = ? ORDER BY nome";
        List<Animal> animais = new ArrayList<>();
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, clienteId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    animais.add(mapearAnimal(rs));
                }
            }
        }
        return animais;
    }

    public Animal buscarPorId(int id) throws SQLException {
        String sql = "SELECT id, nome, especie, raca, data_nascimento, cliente_id FROM animal WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearAnimal(rs);
                }
            }
        }
        return null;
    }

    public void atualizar(Animal animal) throws SQLException {
        String sql = "UPDATE animal SET nome = ?, especie = ?, raca = ?, data_nascimento = ?, cliente_id = ? WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, animal.getNome());
            stmt.setString(2, animal.getEspecie());
            stmt.setString(3, animal.getRaca());
            stmt.setString(4, animal.getDataNascimento().toString());
            stmt.setInt(5, animal.getClienteId());
            stmt.setInt(6, animal.getId());
            stmt.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM animal WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private Animal mapearAnimal(ResultSet rs) throws SQLException {
        Animal a = new Animal();
        a.setId(rs.getInt("id"));
        a.setNome(rs.getString("nome"));
        a.setEspecie(rs.getString("especie"));
        a.setRaca(rs.getString("raca"));
        a.setDataNascimento(LocalDate.parse(rs.getString("data_nascimento")));
        a.setClienteId(rs.getInt("cliente_id"));
        return a;
    }
}
