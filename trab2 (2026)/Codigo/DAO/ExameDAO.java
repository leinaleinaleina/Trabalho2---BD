package DAO;

import classes.casodeuso.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExameDAO {
    private final String url = "jdbc:mysql://localhost:3306/mydb";

    private final String usuario = "root";
    private final String senha = "root";

    public int cadastrarExame(Conta_Bancaria conta) {
    int idConta = -1;

    String sqlSelect = "SELECT idConta_bancaria_cliente FROM conta_bancaria_cliente WHERE Numero_conta = ?";

    try (Connection con = DriverManager.getConnection(url, usuario, senha);
         PreparedStatement stmt = con.prepareStatement(sqlSelect)) {

        stmt.setInt(1, conta.getnumero_conta());
        @SuppressWarnings("unused")
        ResultSet rs = stmt.executeQuery();


            // CORREÇÃO 2: Corrigido "Total investido" para "Total_investido"
           String sqlInsert = "INSERT INTO conta_bancaria_cliente (Numero_conta, Tipo_conta, Data_abertura, Saldo, Total_investido, Cliente_idCliente, Agencia_banco_Numero_agencia_banco) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmtInsert = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
                stmtInsert.setInt(1, conta.getnumero_conta());
                stmtInsert.setString(2, conta.getTipoconta());
                stmtInsert.setString(3, conta.getData_abertura());
                stmtInsert.setDouble(4, conta.getSaldo());
                stmtInsert.setDouble(5, conta.getTotal_investido());
                stmtInsert.setInt(6, conta.getCliente().getidCliente());
                 stmtInsert.setInt(7, conta.getAgencia().getCodAgencia());

                stmtInsert.executeUpdate();
                ResultSet generatedKeys = stmtInsert.getGeneratedKeys();
                if (generatedKeys.next()) {
                    idConta = generatedKeys.getInt(1);
                }
            }
    } catch (SQLException e) {
        // Adicionado printStackTrace para mais detalhes do erro
        System.out.println("Erro ao buscar ou cadastrar conta: " + e.getMessage());
    }
    return idConta;
}
    
    
    public Agencia buscarAgenciaPorNumeroEBancoID(int numeroAgencia, int idBanco) {
    String sql = "SELECT * FROM Agencia_banco WHERE Numero_agencia_banco = ? AND Banco_Codigo_banco = ?";
    Agencia agencia = null;
    
    try (Connection con = DriverManager.getConnection(url, usuario, senha);
         PreparedStatement stmt = con.prepareStatement(sql)) {

        stmt.setInt(1, numeroAgencia);
        stmt.setInt(2, idBanco);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            agencia = new Agencia();
            agencia.setCodAgencia(rs.getInt("Numero_agencia_banco"));
            // Você pode popular outros dados da agência aqui se necessário
        }
        }    catch (SQLException e) {
        System.out.println("Erro ao buscar agência: " + e.getMessage());
        }
        return agencia;
    }
}
