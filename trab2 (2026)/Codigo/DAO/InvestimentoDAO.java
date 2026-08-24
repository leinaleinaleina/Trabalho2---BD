package DAO;

import classes.Investimento;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InvestimentoDAO {
    private final String url = "jdbc:mysql://localhost:3306/mydb";
    private final String usuario = "root";
    private final String senha = "root";

    public void cadastrarInvestimento(Investimento investimento) {
        String sql = "INSERT INTO investimentos_cliente (Data_investimento, Data_vencimento, Valor, Tipo_investimento_idTipo_investimento, Papel_investimento_idPapel_investimento, Conta_Bancaria_Cliente_idConta_bancaria_cliente) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = DriverManager.getConnection(url, usuario, senha);
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, investimento.getData_inicio());
            stmt.setString(2, investimento.getData_vencimento());
            stmt.setDouble(3, investimento.getValorinvestimento());
            stmt.setInt(4, investimento.getTipo().getIdtipoinvestimento());
            stmt.setInt(5, investimento.getPapel().getIdPapel());
            stmt.setInt(6, investimento.getConta().getidConta());
            stmt.executeUpdate();
            System.out.println("DEBUG: Registro de investimento criado no banco.");
        } catch (SQLException e) {
            System.out.println("Erro ao salvar investimento: " + e.getMessage());
        }
    }

    public List<Investimento> buscarInvestimentosPorPeriodo(int idConta, String dataInicio, String dataFim) {
        List<Investimento> investimentos = new ArrayList<>();
        String sql = "SELECT i.*, ti.Tipo_investimento, pi.NomePapel_investimento FROM investimentos_cliente i " +
                     "JOIN Tipo_investimento ti ON i.Tipo_investimento_idTipo_investimento = ti.idTipo_investimento " +
                     "JOIN Papel_investimento pi ON i.Papel_investimento_idPapel_investimento = pi.idPapel_investimento " +
                     "WHERE i.Conta_Bancaria_Cliente_idConta_bancaria_cliente = ? AND i.Data_investimento BETWEEN ? AND ? " +
                     "ORDER BY i.Data_investimento";
        try (Connection con = DriverManager.getConnection(url, usuario, senha);
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, idConta);
            stmt.setString(2, dataInicio);
            stmt.setString(3, dataFim);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Investimento inv = new Investimento();
                inv.setIdinvestimento(rs.getInt("idInvestimento"));
                inv.setData_inicio(rs.getString("Data_investimento"));
                inv.setData_vencimento(rs.getString("Data_vencimento"));
                inv.setValorinvestimento(rs.getDouble("Valor"));
                inv.getTipo().setTipo_investimento(rs.getString("Tipo_investimento"));
                inv.getPapel().setNomepapel(rs.getString("NomePapel_investimento"));
                investimentos.add(inv);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar investimentos por período: " + e.getMessage());
        }
        return investimentos;
    }
    
    public List<Investimento> buscarInvestimentosPorContaID(int idConta) {
    List<Investimento> investimentos = new ArrayList<>();
    
    String sql = "SELECT i.*, ti.Tipo_investimento, pi.NomePapel_investimento FROM investimentos_cliente i " +
                 "JOIN Tipo_investimento ti ON i.Tipo_investimento_idTipo_investimento = ti.idTipo_investimento " +
                 "JOIN Papel_investimento pi ON i.Papel_investimento_idPapel_investimento = pi.idPapel_investimento " +
                 "WHERE i.Conta_Bancaria_Cliente_idConta_bancaria_cliente = ? " +
                 "ORDER BY i.Data_investimento DESC"; // Ordena dos mais recentes para os mais antigos

    try (Connection con = DriverManager.getConnection(url, usuario, senha);
         PreparedStatement stmt = con.prepareStatement(sql)) {

        stmt.setInt(1, idConta);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Investimento inv = new Investimento();
            inv.setIdinvestimento(rs.getInt("idInvestimento"));
            inv.setData_inicio(rs.getString("Data_investimento"));
            inv.setData_vencimento(rs.getString("Data_vencimento"));
            inv.setValorinvestimento(rs.getDouble("Valor"));
            
            // Popula os objetos aninhados com os dados dos JOINs
            inv.getTipo().setTipo_investimento(rs.getString("Tipo_investimento"));
            inv.getPapel().setNomepapel(rs.getString("NomePapel_investimento"));

            investimentos.add(inv);
        }
    } catch (SQLException e) {
        System.out.println("Erro ao buscar investimentos por conta: " + e.getMessage());
    }
    return investimentos;
}
}