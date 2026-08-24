package DAO;

import classes.genericos.*;
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

    public void cadastrarEmailPaciente(EmailPaciente email, int idPaciente) {
        String sql = "INSERT INTO EmailPaciente (EmailP, Paciente_idPaciente) VALUES (?, ?)";

        try (Connection con = DriverManager.getConnection(url, usuario, senha);
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, email.getEmail());
            stmt.setInt(2, idPaciente);

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar email: " + e.getMessage());
        }
    }

    public List<EmailPaciente> buscarEmailsPorPacienteID(int idPaciente) {
        List<EmailPaciente> emailsEncontrados = new ArrayList<>();
        String sql = "SELECT idEmailP, Email FROM EmailPaciente WHERE Paciente_idPaciente = ?";

        try (Connection con = DriverManager.getConnection(url, usuario, senha);
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, idPaciente);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                EmailPaciente email = new EmailPaciente();
                email.setidEmail(rs.getInt("idEmailP"));
                email.setEmail(rs.getString("EmailPaciente"));
                emailsEncontrados.add(email);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar emails do paciente: " + e.getMessage());
        }
        return emailsEncontrados;
    }

    public List<EmailMedico> buscarEmailsPorMedicoID (String CRM) {
        List<EmailMedico> emailsEncontrados = new ArrayList<>();
        String sql = "SELECT idEmailM, EmailMedico FROM EmailMedico WHERE Medico_CRM = ?";

        try (Connection con = DriverManager.getConnection(url, usuario, senha);
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, CRM);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                EmailMedico email = new EmailMedico();
                email.setidEmail(rs.getInt("idEmailM"));
                email.setEmail(rs.getString("EmailMedico"));
                emailsEncontrados.add(email);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar emails do medico: " + e.getMessage());
        }
        return emailsEncontrados;
    }
}