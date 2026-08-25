package DAO;

import classes.casodeuso.*;
import classes.genericos.Medico;
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
    
String sql = "SELECT c.NroConsulta, c.DataConsulta, " +
             "m.CRM, m.Nome_Medico, m.Area, " +
             "d.CID, d.NomeCID, d.DescricaoCID " +
             "FROM Consulta c " +
             "JOIN Medico m ON c.Medico_CRM = m.CRM " +
             "JOIN Diagnostico d ON c.Diagnostico_CID = d.CID " +
             "WHERE c.Paciente_idPaciente = ?";

    try (Connection con = DriverManager.getConnection(url, usuario, senha);
         PreparedStatement stmt = con.prepareStatement(sql)) {

        stmt.setInt(1, idConta);
        ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Consulta consulta = new Consulta();

                consulta.setNro_consulta(rs.getInt("NroConsulta"));
                consulta.setData_consulta(rs.getString("DataConsulta"));

                Medico medico = new Medico();
                medico.setCRM(rs.getString("CRM"));
                medico.setNome_medico(rs.getString("Nome_Medico"));
                medico.setArea(rs.getString("Area"));
                consulta.setMedico(medico);

                Diagnostico diagnostico = new Diagnostico();
                diagnostico.setCID(rs.getString("CID"));
                diagnostico.setNome_CID(rs.getString("NomeCID"));
                diagnostico.setDescricao(rs.getString("DescricaoCID"));
                consulta.setDiagnostico(diagnostico);

                investimentos.add(consulta);
            }
    } catch (SQLException e) {
        System.out.println("Erro ao buscar investimentos por conta: " + e.getMessage());
    }
    return investimentos;
}


public List<Consulta> buscarConsultasPorPacienteID(int idPaciente) {
        List<Consulta> consultasEncontradas = new ArrayList<>();

String sql = "SELECT c.NroConsulta, c.DataConsulta, " +
             "m.CRM, m.Nome_Medico, m.Area, " +
             "d.CID, d.NomeCID, d.DescricaoCID " +
             "FROM Consulta c " +
             "JOIN Medico m ON c.Medico_CRM = m.CRM " +
             "JOIN Diagnostico d ON c.Diagnostico_CID = d.CID " +
             "WHERE c.Paciente_idPaciente = ?" +
             "ORDER BY c.DataConsulta DESC"; 

        try (Connection con = DriverManager.getConnection(url, usuario, senha);
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, idPaciente);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Consulta consulta = new Consulta();

                consulta.setNro_consulta(rs.getInt("NroConsulta"));
                consulta.setData_consulta(rs.getString("DataConsulta"));

                Medico medico = new Medico();
                medico.setCRM(rs.getString("CRM"));
                medico.setNome_medico(rs.getString("Nome_Medico"));
                medico.setArea(rs.getString("Area"));
                consulta.setMedico(medico);

                Diagnostico diagnostico = new Diagnostico();
                diagnostico.setCID(rs.getString("CID"));
                diagnostico.setNome_CID(rs.getString("NomeCID"));
                diagnostico.setDescricao(rs.getString("DescricaoCID"));
                consulta.setDiagnostico(diagnostico);

                consultasEncontradas.add(consulta);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar consultas do paciente: " + e.getMessage());
        }

        return consultasEncontradas;
    }
}