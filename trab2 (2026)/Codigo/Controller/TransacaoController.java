package Controller;

import DAO.Conta_BancariaDAO;
import DAO.InvestimentoDAO;
import DAO.Tipo_transacaoDAO;
import DAO.TransacaoDAO;
import classes.Investimento;
import classes.Transacao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class TransacaoController {

    private final String url = "jdbc:mysql://localhost:3306/mydb";
    private final String usuario = "root";
    private final String senha = "root";

    public TransacaoController() {
        // Construtor vazio
    }

    public void cadastrar_transacao(String data, int idConta, double valor, int idTipo, String obs) {
        Connection con = null;
        try {
            con = DriverManager.getConnection(url, usuario, senha);
            con.setAutoCommit(false); // Inicia o controle manual da transação

            Conta_BancariaDAO contaDAO = new Conta_BancariaDAO();
            TransacaoDAO transacaoDAO = new TransacaoDAO();
            Tipo_transacaoDAO tipoDAO = new Tipo_transacaoDAO();

            String tipoRenda = tipoDAO.getTipoRenda(idTipo, con);
            if (tipoRenda == null) {
                throw new SQLException("Tipo de transação com ID " + idTipo + " não encontrado.");
            }
            
            double valorParaUpdate = valor;
            if (tipoRenda.equalsIgnoreCase("débito")) {
                valorParaUpdate = -valor;
            }

            boolean saldoAtualizado = contaDAO.atualizarSaldo(idConta, valorParaUpdate, con);
            if (!saldoAtualizado) {
                throw new SQLException("Não foi possível atualizar o saldo. Conta com ID " + idConta + " não encontrada ou saldo insuficiente.");
            }
            
            Transacao t = new Transacao();
            t.setdataTransacao(data);
            t.getConta().setidConta(idConta);
            t.setvalorTransacao(valor);
            t.getTipo().setIdTipo(idTipo);
            t.setObs(obs);
            transacaoDAO.cadastrarTransacao(t, con);
            
            con.commit(); 
            System.out.println("SUCESSO: Transação e atualização de saldo realizados!");

        } catch (SQLException e) {
            System.out.println("ERRO na transação: " + e.getMessage());
            try {
                if (con != null) {
                    System.out.println("Desfazendo operações (rollback)...");
                    con.rollback(); 
                }
            } catch (SQLException ex) {
                System.out.println("ERRO CRÍTICO ao tentar fazer rollback: " + ex.getMessage());
            }
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void gerarExtratoPorPeriodo(int idConta, String dataInicio, String dataFim) {
        TransacaoDAO transacaoDAO = new TransacaoDAO();
        InvestimentoDAO investimentoDAO = new InvestimentoDAO();

        List<Transacao> transacoes = transacaoDAO.buscarTransacoesPorPeriodo(idConta, dataInicio, dataFim);
        List<Investimento> investimentos = investimentoDAO.buscarInvestimentosPorPeriodo(idConta, dataInicio, dataFim);

        System.out.println("\n==================================================================================");
        System.out.printf(" EXTRATO DA CONTA - PERÍODO: %s a %s%n", dataInicio, dataFim);
        System.out.println("==================================================================================");

        System.out.println("\n--- Transações do Período ---");
        System.out.printf("%-12s | %-5s | %-5s | %-25s | %s%n", "Data", "Cod", "Tipo", "Nome do Tipo", "Valor (R$)");
        System.out.println("----------------------------------------------------------------------------------");
        if (transacoes.isEmpty()) {
            System.out.println("Nenhuma transação encontrada para o período.");
        } else {
            for (Transacao t : transacoes) {
                char sinal = t.getTipo().getRenda().equalsIgnoreCase("entrada") ? '+' : '-';
                System.out.printf("%-12s | %-5d | %-5d | %-25s | %c %.2f%n",
                                  t.getdataTransacao(),
                                  t.getcodtransacao(),
                                  t.getTipo().getIdTipo(),
                                  t.getTipo().getNomeTipo(),
                                  sinal,
                                  t.getvalorTransacao());
            }
        }

        System.out.println("\n--- Investimentos do Período ---");
        System.out.printf("%-12s | %-15s | %-20s | %-12s | %s%n", "Data Invest.", "Tipo Investimento", "Papel (Título)", "Data Venc.", "Valor Aplicado (R$)");
        System.out.println("----------------------------------------------------------------------------------");
        if (investimentos.isEmpty()) {
            System.out.println("Nenhum investimento encontrado para o período.");
        } else {
            for (Investimento inv : investimentos) {
                System.out.printf("%-12s | %-15s | %-20s | %-12s | %.2f%n",
                                  inv.getData_inicio(),
                                  inv.getTipo().getTipo_investimento(),
                                  inv.getPapel().getNomepapel(),
                                  inv.getData_vencimento(),
                                  inv.getValorinvestimento());
            }
        }
        System.out.println("==================================================================================");
    }
}