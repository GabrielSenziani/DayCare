package daycare.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexao {

    private static final String URL = "jdbc:sqlite:daycare.db";

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver JDBC do SQLite não encontrado: " + e.getMessage(), e);
        }
    }

    public static Connection getConexao() throws SQLException {
        Connection conn = DriverManager.getConnection(URL);
        try {
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("PRAGMA foreign_keys = ON");
            }
            return conn;
        } catch (SQLException e) {
            try {
                conn.close();
            } catch (SQLException ex) {
                e.addSuppressed(ex);
            }
            throw e;
        }
    }

    public static void criarTabelas() throws SQLException {
        String sqlCliente = "CREATE TABLE IF NOT EXISTS cliente (id INTEGER PRIMARY KEY AUTOINCREMENT, cpf TEXT NOT NULL UNIQUE, nome TEXT NOT NULL, telefone TEXT, email TEXT, endereco TEXT)";
        String sqlAnimal = "CREATE TABLE IF NOT EXISTS animal (id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT NOT NULL, especie TEXT NOT NULL, raca TEXT, data_nascimento TEXT, cliente_id INTEGER NOT NULL, FOREIGN KEY (cliente_id) REFERENCES cliente(id))";
        try (Connection conn = Conexao.getConexao();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sqlCliente);
            stmt.execute(sqlAnimal);
        }
    }
}
