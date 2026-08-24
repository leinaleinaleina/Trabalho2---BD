// Substitua o conteúdo de InvestimentoController.java por este
package Controller;

import DAO.Conta_BancariaDAO;
import DAO.InvestimentoDAO;
import classes.Investimento;
import java.util.List;

public class InvestimentoController {

    private final InvestimentoDAO investimentoDAO;
    private final Conta_BancariaDAO contaDAO;

    public InvestimentoController() {
        this.investimentoDAO = new InvestimentoDAO();
        this.contaDAO = new Conta_BancariaDAO();
    }

    public void cadastrar_investimento(String data_inicio, String data_venci, double valorInvestimento, int idTipo, int idPapel, int idConta) {
        
        contaDAO.atualizarSaldosParaInvestimento(idConta, valorInvestimento);

        
        Investimento novoInvestimento = new Investimento();
        novoInvestimento.setData_inicio(data_inicio);
        novoInvestimento.setData_vencimento(data_venci);
        novoInvestimento.setValorinvestimento(valorInvestimento);
        
        
        novoInvestimento.getTipo().setIdtipoinvestimento(idTipo);
        novoInvestimento.getPapel().setIdPapel(idPapel);
        novoInvestimento.getConta().setidConta(idConta);

        
        investimentoDAO.cadastrarInvestimento(novoInvestimento);
        
        System.out.println("\n==> Investimento de R$ " + valorInvestimento + " cadastrado com sucesso! <==");
    }

    
    public void listarInvestimentosDaConta(int idConta) {
        System.out.println("\n--- Exibindo Investimentos da Conta ID: " + idConta + " ---");
        List<Investimento> investimentos = investimentoDAO.buscarInvestimentosPorContaID(idConta);

        if (investimentos.isEmpty()) {
            System.out.println("Nenhum investimento encontrado para esta conta.");
        } else {
            double total = 0;
            for (Investimento inv : investimentos) {
                System.out.printf("  - Papel: %-20s | Tipo: %-15s | Valor: R$ %.2f | Vencimento: %s%n",
                                  inv.getPapel().getNomepapel(),
                                  inv.getTipo().getTipo_investimento(),
                                  inv.getValorinvestimento(),
                                  inv.getData_vencimento());
                total += inv.getValorinvestimento();
            }
            System.out.printf("--- Total Investido na Conta: R$ %.2f ---%n", total);
        }
    }

    
}