package Controller;

import DAO.Conta_BancariaDAO;
import classes.Cliente;
import classes.Conta_Bancaria;

public class Conta_BancariaController {

    private final Conta_BancariaDAO CONTA;

    public Conta_BancariaController () {
        this.CONTA = new Conta_BancariaDAO();
    }


    public void cadastrar_conta_bancaria(int numero_conta, String tipo_conta, String data, int idDoCliente,  int numAgencia) {
    Conta_Bancaria conta = new Conta_Bancaria();

   conta.setnumeroconta(numero_conta);
    conta.setTipoconta(tipo_conta);
    conta.setData_abertura(data);
    conta.setSaldo(0.00);
    conta.setTotal_investido(0.00);

    
    Cliente clienteAssociado = new Cliente();
    clienteAssociado.setidCliente(idDoCliente);
    conta.setCliente(clienteAssociado);

    conta.getAgencia().setCodAgencia(numAgencia);

    CONTA.cadastrarConta(conta);
    System.out.println("Conta cadastrada com sucesso para o cliente ID: " + idDoCliente);
}



     public void descontarSaldo(int idConta, double valor) {
        if (valor <= 0) {
            System.out.println("Valor de desconto inválido!");
            return;
        }

        CONTA.descontarSaldo(idConta, valor);
    }

}