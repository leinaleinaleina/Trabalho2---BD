package Controller;

import DAO.*;
import classes.*; 
import java.util.List;         


public class ClienteController {

    private final ClienteDAO cli;

    public ClienteController () {
        this.cli = new ClienteDAO();
    }

   // Arquivo: ClienteController.java

public void cadastrar_cliente (String nome, String CPF, int Numero, String Complemento, Endereco endereco){

    Cliente cliente = new Cliente();
    cliente.setnome_cliente(nome);
    cliente.setCPF_cliente(CPF);
    cliente.setnumero_cliente(Numero);
    cliente.setcomp_cliente(Complemento);

    
    UFDAO ufDAO = new UFDAO();
    int idUFD = ufDAO.cadastrarUF(endereco.getCidade().getUF());
    endereco.getCidade().getUF().setidUF(idUFD);

    
    CidadeDAO cidadeDAO = new CidadeDAO();
    int idCidade = cidadeDAO.cadastrarCidade(endereco.getCidade());
    endereco.getCidade().setidCidade(idCidade);
    
    
    BairroDAO bairroDAO = new BairroDAO();
    int idBairro = bairroDAO.cadastrarBairro(endereco.getBairro());
    endereco.getBairro().setidBairro(idBairro);

    
    TipoLograDAO tipoDAO = new TipoLograDAO(); 
    int idTipo = tipoDAO.cadastrarTipoLogra(endereco.getLogra().getTipologra());
    endereco.getLogra().getTipologra().setidTipologra(idTipo);

    
    LograDAO logradouroDAO = new LograDAO();
    int idLogradouro = logradouroDAO.cadastrarLogradouro(endereco.getLogra());
    endereco.getLogra().setidLogra(idLogradouro);
    
    
    EnderecoDAO enderecoDAO = new EnderecoDAO();
    int idEndereco = enderecoDAO.cadastrarEndereco(endereco);

    
    Endereco e = new Endereco();
    e.setidEndereco(idEndereco);
    cliente.setEndereco(e);

    
    ClienteDAO clienteDAO = new ClienteDAO();
    clienteDAO.cadastrarCliente(cliente);
    
    System.out.println("\n==> Cliente cadastrado com SUCESSO! <==");
}

    public void buscarClientePorCPF(String CPF) {

        Cliente cliente = cli.buscarClientePorCPF(CPF);

        if (cliente != null) {
            System.out.println("Cliente encontrado!");
            System.out.println("Nome: " + cliente.getnome_cliente());
            System.out.println("CPF: " + cliente.getCPF_cliente());

            System.out.println("Dados de Endereco");
            System.out.println("id Endereco: " + cliente.getEndereco().getidEndereco());
            System.out.println("CEP: " + cliente.getEndereco().getCEP());
            System.out.println("Bairro: " + cliente.getEndereco().getBairro());
            System.out.println("Logradouro: " + cliente.getEndereco().getLogra().getLogra());
            System.out.println("Tipo Logradouro: " + cliente.getEndereco().getLogra().getTipologra());
            System.out.println("Cidade: " + cliente.getEndereco().getCidade().getCidade());
            System.out.println("UF: " + cliente.getEndereco().getCidade().getUF().getUF());
            System.out.println("--- Telefones ---");
        TelefoneClienteDAO telefoneDAO = new TelefoneClienteDAO();
        List<TelefoneCliente> telefones = telefoneDAO.buscarTelefonesPorClienteID(cliente.getidCliente());

        if (telefones.isEmpty()) {
            System.out.println("Nenhum telefone cadastrado para este cliente.");
        } else {
            
            for (TelefoneCliente fone : telefones) {
                
                System.out.println("  +" + fone.getDDD().getDDDI().getDDDI() + " (" + fone.getDDD().getDDD() + ") " + fone.getTelefone());
            }
        }
        
         System.out.println("--- Emails ---");
        EmailDAO emailDAO = new EmailDAO(); // Cria uma instância do DAO de email
        List<Email> emails = emailDAO.buscarEmailsPorClienteID(cliente.getidCliente()); // Busca a lista de emails

        if (emails.isEmpty()) {
            System.out.println("Nenhum email cadastrado para este cliente.");
        } else {
            // Loop para imprimir cada email da lista
            for (Email email : emails) {
                System.out.println("  - " + email.getEmail());
            }
        }
        
        System.out.println("--- Contas Bancárias ---");
            Conta_BancariaDAO contaDAO = new Conta_BancariaDAO();
            List<Conta_Bancaria> contas = contaDAO.buscarContasPorClienteID(cliente.getidCliente());

            if (contas.isEmpty()) {
                System.out.println("Nenhuma conta bancária cadastrada para este cliente.");
            } else {
                // <<< PRINTF ATUALIZADO PARA EXIBIR OS NOVOS DADOS >>>
                System.out.printf("%-20s | %-10s | %-10s | %-15s | %s%n", "Banco", "Agência", "Conta", "Tipo", "Saldo Disponível (R$)");
                System.out.println(String.format("%90s", "").replace(' ', '-')); // Linha divisória
                
                for (Conta_Bancaria conta : contas) {
                    // Lógica para lidar com contas que talvez não tenham banco/agência definidos
                    String nomeBanco = (conta.getAgencia() != null && conta.getAgencia().getBanco() != null) ? conta.getAgencia().getBanco().getnomeBanco() : "N/A";
                    String numAgencia = (conta.getAgencia() != null && conta.getAgencia().getCodAgencia() != 0) ? String.valueOf(conta.getAgencia().getCodAgencia()) : "N/A";

                    System.out.printf("%-20s | %-10s | %-10s | %-15s | %.2f%n",
                                      nomeBanco,
                                      numAgencia,
                                      conta.getnumero_conta(),
                                      conta.getTipoconta(),
                                      conta.getSaldo());
                }
            }
        

    } else {
        System.out.println("Nenhum cliente encontrado com o CPF: " + CPF);
    }
    }

    public void buscarClientePorID(int ID) {

    Cliente cliente = cli.buscarClientePorID(ID);
    
    if (cliente != null) {
       System.out.println("=======================================================");
            
            // --- DADOS PESSOAIS ---
            System.out.println("\n--- Dados Pessoais ---");
            System.out.println("ID: " + cliente.getidCliente());
            System.out.println("Nome: " + cliente.getnome_cliente());
            System.out.println("CPF: " + cliente.getCPF_cliente());

            // --- ENDEREÇO ---
            System.out.println("\n--- Endereço ---");
            System.out.println("Logradouro: " + cliente.getEndereco().getLogra().getTipologra().getTipoLogra() + " " + cliente.getEndereco().getLogra().getLogra());
            System.out.println("Número: " + cliente.getnumero_cliente());
            System.out.println("Complemento: " + cliente.getcomp_cliente());
            System.out.println("Bairro: " + cliente.getEndereco().getBairro().getBairro());
            System.out.println("Cidade: " + cliente.getEndereco().getCidade().getCidade() + "/" + cliente.getEndereco().getCidade().getUF().getSiglaUF());
            System.out.println("CEP: " + cliente.getEndereco().getCEP());

            // --- CONTATOS ---
            System.out.println("\n--- Contatos ---");
            TelefoneClienteDAO telefoneDAO = new TelefoneClienteDAO();
            List<TelefoneCliente> telefones = telefoneDAO.buscarTelefonesPorClienteID(cliente.getidCliente());
            if (telefones.isEmpty()) {
                System.out.println("Telefones: Nenhum cadastrado.");
            } else {
                System.out.print("Telefones: ");
                for (TelefoneCliente fone : telefones) {
                    System.out.print("+" + fone.getDDD().getDDDI().getDDDI() + " (" + fone.getDDD().getDDD() + ") " + fone.getTelefone() + "   ");
                }
                System.out.println();
            }

            EmailDAO emailDAO = new EmailDAO();
            List<Email> emails = emailDAO.buscarEmailsPorClienteID(cliente.getidCliente());
            if (emails.isEmpty()) {
                System.out.println("Emails: Nenhum cadastrado.");
            } else {
                System.out.print("Emails: ");
                for (Email email : emails) {
                    System.out.print(email.getEmail() + "   ");
                }
                System.out.println();
            }

            // --- CONTAS BANCÁRIAS ---
            System.out.println("\n--- Contas Bancárias ---");
            Conta_BancariaDAO contaDAO = new Conta_BancariaDAO();
            List<Conta_Bancaria> contas = contaDAO.buscarContasPorClienteID(cliente.getidCliente());
            if (contas.isEmpty()) {
                System.out.println("Nenhuma conta bancária cadastrada para este cliente.");
            } else {
                System.out.printf("%-20s | %-10s | %-10s | %-15s | %-15s | %s%n", "Banco", "Agência", "Conta", "Tipo", "Saldo (R$)", "Total Investido (R$)");
                System.out.println(String.format("%110s", "").replace(' ', '-'));
                for (Conta_Bancaria conta : contas) {
                    String nomeBanco = (conta.getAgencia() != null && conta.getAgencia().getBanco() != null) ? conta.getAgencia().getBanco().getnomeBanco() : "N/A";
                    String numAgencia = (conta.getAgencia() != null && conta.getAgencia().getCodAgencia() != 0) ? String.valueOf(conta.getAgencia().getCodAgencia()) : "N/A";

                    System.out.printf("%-20s | %-10s | %-10s | %-15s | %-15.2f | %.2f%n",
                                      nomeBanco,
                                      numAgencia,
                                      conta.getnumero_conta(),
                                      conta.getTipoconta(),
                                      conta.getSaldo(),
                                      conta.getTotal_investido());
                }
            }
            System.out.println("=======================================================");


    } else {
        System.out.println("Nenhum cliente encontrado com o ID: " + ID);
    }
    }

}
