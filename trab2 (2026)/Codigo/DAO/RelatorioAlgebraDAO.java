package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import classes.casodeuso.TipoExame;
import classes.genericos.*;
public class RelatorioAlgebraDAO {
    
    private final String url = "jdbc:mysql://localhost:3306/mydb";
    private final String usuario = "root";
    private final String senha = "root";

    // a) (Paciente x Médico)
    public void executarProdutoCartesiano() {
        String sql = "SELECT * FROM Paciente CROSS JOIN Medico";
        
        try (Connection con = DriverManager.getConnection(url, usuario, senha);
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.printf("%-20s | %-20s | %-15s%n", "Nome Paciente", "Nome Médico", "Área Médico");
            System.out.println("---------------------------------------------------------------");
            
            while (rs.next()) {
                String nomePaciente = rs.getString("Nome_Paciente");
                String nomeMedico = rs.getString("Nome_Medico");
                String areaMedico = rs.getString("Area");
                
                System.out.printf("%-20s | %-20s | %-15s%n", nomePaciente, nomeMedico, areaMedico);
            }
        } catch (SQLException e) {
            System.out.println("Erro no Produto Cartesiano: " + e.getMessage());
        }
    }

    // b) UNION (Emails de Pacientes e Médicos)
    public void executarUnionEmails() {
        String sql = "SELECT EmailPaciente AS Email FROM EmailPaciente " +
                     "UNION " +
                     "SELECT EmailMedico AS Email FROM EmailMedico";
                     
        try (Connection con = DriverManager.getConnection(url, usuario, senha);
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                System.out.println("- " + rs.getString("Email"));
            }
        } catch (SQLException e) {
            System.out.println("Erro no UNION: " + e.getMessage());
        }
    }

    // c) (Exames não solicitados em Jul/2026)
    public void executarDiferencaExames() {
    String sql = "SELECT idTipoExame AS ID, TipoExame AS Exame, Descricao " +
                    "FROM TipoExame " +
                    "EXCEPT " +
                    "SELECT t.idTipoExame, t.TipoExame, t.Descricao " +
                    "FROM TipoExame t " +
                    "JOIN Exame e ON t.idTipoExame = e.TipoExame_idTipoExame " +
                    "WHERE e.DataExame LIKE '%/07/%'";

        try (Connection con = DriverManager.getConnection(url, usuario, senha);
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            boolean encontrou = false;
            
            while (rs.next()) {
                encontrou = true;
                System.out.println("ID: " + rs.getInt("ID") + 
                                   " | Exame: " + rs.getString("Exame") + 
                                   " | Descrição: " + rs.getString("Descricao"));
            }
            
            if (!encontrou) {
                System.out.println("Todos os exames foram solicitados neste mês.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar exames não solicitados em jul/2026: " + e.getMessage());
        }
    }
}