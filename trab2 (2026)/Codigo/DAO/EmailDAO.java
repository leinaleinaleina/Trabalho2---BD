// Em src/DAO/EmailDAO.java
package DAO;

import classes.Email;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmailDAO {
    private final String url = "jdbc:mysql://localhost:3306/mydb";
    private final String usuario = "root";
    private final String senha = "root";

    public void cadastrarEmail(Email email, int idCliente) {
        String sql = "INSERT INTO Email_cliente (Email, Cliente_idCliente) VALUES (?, ?)";

        try (Connection con = DriverManager.getConnection(url, usuario, senha);
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, email.getEmail());
            stmt.setInt(2, idCliente);

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar email: " + e.getMessage());
        }
    }

    public List<Email> buscarEmailsPorClienteID(int idCliente) {
        List<Email> emailsEncontrados = new ArrayList<>();
        String sql = "SELECT idEmail, Email FROM Email_cliente WHERE Cliente_idCliente = ?";

        try (Connection con = DriverManager.getConnection(url, usuario, senha);
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Email email = new Email();
                email.setidEmail(rs.getInt("idEmail"));
                email.setEmail(rs.getString("Email"));
                emailsEncontrados.add(email);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar emails do cliente: " + e.getMessage());
        }
        return emailsEncontrados;
    }
}