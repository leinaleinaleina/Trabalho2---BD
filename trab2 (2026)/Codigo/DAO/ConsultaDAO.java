package DAO;

import classes.casodeuso.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConsultaDAO {
    private final String url = "jdbc:mysql://localhost:3306/mydb";
    private final String usuario = "root";
    private final String senha = "root";

    public void cadastrarConsulta (Consulta consulta) {
        String sql = "INSERT INTO Consulta (DataConsulta, Diagnostico_CID, Medico_CRM, Paciente_idPaciente, Paciente_EstadoCivil_idEstadoCivil, Paciente_Sexo_idSexo) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = DriverManager.getConnection(url, usuario, senha);
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, consulta.getData_consulta());
            stmt.setString(2, consulta.getMedico().getCRM());
            stmt.setString(3, consulta.getDiagnostico().getCID());
            stmt.setInt(4, consulta.getPaciente().getidPaciente());
            stmt.setInt(5, consulta.getPaciente().getEstadocivil().getIdEstadoCivil());
            stmt.setInt(6, consulta.getPaciente().getSexo().getIdSexo());
            stmt.executeUpdate();
            System.out.println("DEBUG: Registro de consulta criado no banco.");
        } catch (SQLException e) {
            System.out.println("Erro ao salvar consulta: " + e.getMessage());
        }
    }
    
    public List<Consulta> buscarConsultaPorContaID(int idConta) {
    List<Consulta> investimentos = new ArrayList<>();
    
    String sql = "SELECT c.*, m.Nome_Medico, m.Area, d.NomeCID, d.DescricaoCID FROM Consulta c " +
             "JOIN Medico m ON c.Medico_CRM = m.CRM " +
             "JOIN Diagnostico d ON c.Diagnostico_CID = d.CID " +
             "WHERE c.Paciente_idPaciente = ? " +
             "ORDER BY c.DataConsulta DESC"; // Ordena das consultas mais recentes para as mais antigas

    try (Connection con = DriverManager.getConnection(url, usuario, senha);
         PreparedStatement stmt = con.prepareStatement(sql)) {

        stmt.setInt(1, idConta);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Consulta consulta = new Consulta();
            consulta.setNro_consulta (rs.getInt("NroConsulta"));
            consulta.setData_consulta (rs.getString("DataConsulta"));
            consulta.getDiagnostico().setCID(rs.getString("Diagnostico_CID"));
            consulta.getMedico().setCRM(rs.getString("Medico_CRM"));

            investimentos.add(consulta);
        }
    } catch (SQLException e) {
        System.out.println("Erro ao buscar investimentos por conta: " + e.getMessage());
    }
    return investimentos;
}
}