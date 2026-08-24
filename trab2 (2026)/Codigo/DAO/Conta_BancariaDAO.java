package DAO;

import classes.Agencia;
import classes.Banco;
import classes.Conta_Bancaria;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Conta_BancariaDAO {
    private final String url = "jdbc:mysql://localhost:3306/mydb";

    private final String usuario = "root";
    private final String senha = "root";

    // Em Conta_BancariaDAO.java
public int cadastrarConta(Conta_Bancaria conta) {
    int idConta = -1;

    // CORREÇÃO 1: Adicionado idConta_bancaria_cliente ao SELECT
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

     public void descontarSaldo(int idConta, double valorDescontar) {
    String selectSQL = "SELECT Saldo FROM Conta_Bancaria_Cliente WHERE idConta_bancaria_cliente = ?";
    String updateSQL = "UPDATE Conta_Bancaria_Cliente SET Saldo = ? WHERE idConta_bancaria_cliente = ?";

    try (Connection con = DriverManager.getConnection(url, usuario, senha);
         PreparedStatement selectStmt = con.prepareStatement(selectSQL);
         PreparedStatement updateStmt = con.prepareStatement(updateSQL)) {

        //Consulta o saldo atual
        selectStmt.setInt(1, idConta);
        ResultSet rs = selectStmt.executeQuery();

        if (rs.next()) {
            double saldoAtual = rs.getDouble("Saldo");
            double novoSaldo = saldoAtual - valorDescontar;

            //Atualiza o saldo no banco
            updateStmt.setDouble(1, novoSaldo);
            updateStmt.setInt(2, idConta);
            updateStmt.executeUpdate();

            System.out.println("Saldo anterior: " + saldoAtual);
            System.out.println("Valor descontado: " + valorDescontar);
            System.out.println("Novo saldo: " + novoSaldo);
        } else {
            System.out.println("Conta com ID " + idConta + " não encontrada.");
        }

    } catch (SQLException e) {
        System.out.println("Erro ao atualizar saldo: " + e.getMessage());
    }
}

public List<Conta_Bancaria> buscarContasPorClienteID(int idDoCliente) {
    List<Conta_Bancaria> contasEncontradas = new ArrayList<>();
    
    // <<< QUERY CORRIGIDA ABAIXO >>>
    String sql = "SELECT * FROM conta_bancaria_cliente cbc " +
                 "LEFT JOIN Agencia_banco ab ON cbc.Agencia_banco_Numero_agencia_banco = ab.Numero_agencia_banco " + 
                 "LEFT JOIN Banco b ON ab.Banco_Codigo_banco = b.Codigo_banco " +
                 "WHERE cbc.Cliente_idCliente = ?";

    try (Connection con = DriverManager.getConnection(url, usuario, senha);
         PreparedStatement stmt = con.prepareStatement(sql)) {

        stmt.setInt(1, idDoCliente);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Conta_Bancaria conta = new Conta_Bancaria();
            Agencia agencia = new Agencia();
            Banco banco = new Banco();

            // Populando dados da CONTA
            conta.setidConta(rs.getInt("idConta_bancaria_cliente"));
            conta.setnumeroconta(rs.getInt("Numero_Conta"));
            conta.setTipoconta(rs.getString("Tipo_conta"));
            conta.setSaldo(rs.getDouble("Saldo"));
            conta.setTotal_investido(rs.getDouble("Total_investido"));

            // Populando dados de BANCO e AGÊNCIA se existirem
            if (rs.getObject("Codigo_banco") != null) {
                banco.setcodBanco(rs.getInt("Codigo_banco"));
                banco.setnomeBanco(rs.getString("Nome_banco"));
                agencia.setBanco(banco);
            }
            if (rs.getObject("Numero_agencia_banco") != null) {
                agencia.setCodAgencia(rs.getInt("Numero_agencia_banco"));
            }
            conta.setAgencia(agencia);
            
            contasEncontradas.add(conta);
        }
    } catch (SQLException e) {
        System.out.println("Erro ao buscar contas bancárias do cliente: " + e.getMessage());
    }
    
    return contasEncontradas;
}

public void atualizarSaldosParaInvestimento(int idConta, double valorInvestimento) {
    // <<< QUERY CORRIGIDA COM IFNULL PARA GARANTIR A SOMA >>>
    String sql = "UPDATE conta_bancaria_cliente SET Saldo = Saldo - ?, Total_investido = IFNULL(Total_investido, 0) + ? WHERE idConta_bancaria_cliente = ?";

    try (Connection con = DriverManager.getConnection(url, usuario, senha);
         PreparedStatement stmt = con.prepareStatement(sql)) {

        stmt.setDouble(1, valorInvestimento);
        stmt.setDouble(2, valorInvestimento);
        stmt.setInt(3, idConta);

        int linhasAfetadas = stmt.executeUpdate();
        if (linhasAfetadas > 0) {
            System.out.println("DEBUG: Saldos da conta " + idConta + " atualizados com sucesso para investimento.");
        } else {
            System.out.println("AVISO: Nenhum saldo atualizado. A conta com ID " + idConta + " não foi encontrada.");
        }

    } catch (SQLException e) {
        System.out.println("Erro ao atualizar saldos para investimento: " + e.getMessage());
    }
}


public boolean atualizarSaldo(int idConta, double valor, Connection con) throws SQLException {
    String sql = "UPDATE conta_bancaria_cliente SET Saldo = Saldo + ? WHERE idConta_bancaria_cliente = ?";
    
    try (PreparedStatement stmt = con.prepareStatement(sql)) {
        stmt.setDouble(1, valor);
        stmt.setInt(2, idConta);

        int linhasAfetadas = stmt.executeUpdate();
        
        return linhasAfetadas > 0;
    }
}


}
