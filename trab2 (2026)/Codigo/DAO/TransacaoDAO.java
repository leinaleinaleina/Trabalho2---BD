package DAO;

import classes.Transacao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransacaoDAO {
    private final String url = "jdbc:mysql://localhost:3306/mydb";
    private final String usuario = "root";
    private final String senha = "root";

    public void cadastrarTransacao(Transacao transacao, Connection con) throws SQLException {
        String sql = "INSERT INTO Transacao_bancaria (Data_transacao, Valor_transacao, observacoes, Tipo_transacao_idTipo_transacao, Conta_Bancaria_Cliente_idConta_bancaria_cliente) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, transacao.getdataTransacao());
            stmt.setDouble(2, transacao.getvalorTransacao());
            stmt.setString(3, transacao.getObs());
            stmt.setInt(4, transacao.getTipo().getIdTipo());
            stmt.setInt(5, transacao.getConta().getidConta());
            stmt.executeUpdate();
        }
    }

    public List<Transacao> buscarTransacoesPorPeriodo(int idConta, String dataInicio, String dataFim) {
        List<Transacao> transacoes = new ArrayList<>();
        String sql = "SELECT t.*, tp.Tipo_transacao, tp.Tipo_renda FROM Transacao_bancaria t " +
                     "JOIN Tipo_transacao tp ON t.Tipo_transacao_idTipo_transacao = tp.idTipo_transacao " +
                     "WHERE t.Conta_Bancaria_Cliente_idConta_bancaria_cliente = ? AND t.Data_transacao BETWEEN ? AND ? " +
                     "ORDER BY t.Data_transacao";
        try (Connection con = DriverManager.getConnection(url, usuario, senha);
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, idConta);
            stmt.setString(2, dataInicio);
            stmt.setString(3, dataFim);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Transacao t = new Transacao();
                t.setcodtransacao(rs.getInt("Cod_transacao"));
                t.setdataTransacao(rs.getString("Data_transacao"));
                t.setvalorTransacao(rs.getDouble("Valor_transacao"));
                t.getTipo().setIdTipo(rs.getInt("Tipo_transacao_idTipo_transacao"));
                t.getTipo().setNomeTipo(rs.getString("Tipo_transacao"));
                t.getTipo().setRenda(rs.getString("Tipo_renda"));
                transacoes.add(t);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar transações por período: " + e.getMessage());
        }
        return transacoes;
    }
}