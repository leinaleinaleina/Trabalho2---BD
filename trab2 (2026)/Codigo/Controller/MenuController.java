package Controller;

import DAO.*;
import classes.*;
import java.util.List;
import java.util.Scanner;

public class MenuController {

    private final Scanner scanner;

    public MenuController() {
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        while (true) {
            exibirMenuPrincipal();
            try {
                int opcao = Integer.parseInt(scanner.nextLine());
                processarOpcao(opcao);
                if (opcao == 0) {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("ERRO: Por favor, digite um número válido.");
            }
        }
    }

    private void exibirMenuPrincipal() {
        System.out.println("\n========= SISTEMA BANCÁRIO =========");
        System.out.println("--- GESTÃO DE CLIENTES ---");
        System.out.println("1. Cadastrar Novo Cliente");
        System.out.println("2. Buscar Cliente (Relatório Completo)");
        System.out.println("3. Adicionar Telefone a Cliente");
        System.out.println("4. Adicionar Email a Cliente");
        System.out.println("5. Cadastrar Conta Bancária");
        System.out.println("6. Realizar Transação (Depósito/Saque)");
        System.out.println("7. Cadastrar Investimento");
        System.out.println("8. Gerar Extrato por Período");
        System.out.println("0. Sair");
        System.out.println("\n===================================");
        System.out.print("Escolha uma opção: ");
        
    }

    private void processarOpcao(int opcao) {
        switch (opcao) {
            case 1 -> cadastrarCliente();
            case 2 -> buscarCliente();
            case 3 -> adicionarTelefone();
            case 4 -> adicionarEmail();
            case 5 -> cadastrarContaBancaria();
            case 6 -> realizarTransacao();
            case 7 -> cadastrarInvestimento();
            case 8 -> gerarExtrato();
            case 0 -> {
                try (scanner) {
                    System.out.println("Encerrando o sistema. Até logo!");
                }
            }

            default -> System.out.println("Opção inválida! Tente novamente.");
        }
    }

    private void buscarCliente() {
        PacienteController clienteController = new PacienteController();
        System.out.println("\n--- Buscar Paciente ---");
        System.out.print("Digite o ID ou Documento do paciente: ");
        String busca = scanner.nextLine();
        try {
            int id = Integer.parseInt(busca);
            clienteController.buscarClientePorID(id);
        } catch (NumberFormatException e) {
            clienteController.buscarClientePorCPF(busca);
        }
    }

    private void adicionarTelefone() {
        System.out.println("\n--- Adicionar Telefone ---");
        System.out.print("Digite o ID do Cliente: ");
        int idCliente = Integer.parseInt(scanner.nextLine());
        System.out.print("Digite o DDDI (ex: 55 para Brasil): ");
        int dddiNum = Integer.parseInt(scanner.nextLine());
        System.out.print("Digite o DDD: ");
        int dddNum = Integer.parseInt(scanner.nextLine());
        System.out.print("Digite o número do telefone: ");
        String foneNum = scanner.nextLine();

        DDDController dddController = new DDDController();
        DDDI dddi = new DDDI();
        dddi.setDDDI(dddiNum);
        int idDDD = dddController.cadastrar_DDD(dddNum, dddi);

        if (idDDD != -1) {
            TelefoneClienteController foneController = new TelefoneClienteController();
            foneController.cadastrar_fone_cliente(foneNum, idDDD, idCliente);
        } else {
            System.out.println("ERRO: Não foi possível cadastrar o DDD, o telefone não pode ser adicionado.");
        }
    }

    private void adicionarEmail() {
        System.out.println("\n--- Adicionar Email ---");
        System.out.print("Digite o ID do Cliente: ");
        int idCliente = Integer.parseInt(scanner.nextLine());
        System.out.print("Digite o novo email: ");
        String email = scanner.nextLine();
        EmailController emailController = new EmailController();
        emailController.cadastrarEmailParaCliente(email, idCliente);
    }
    
    private void cadastrarContaBancaria() {
        System.out.println("\n--- Cadastrar Conta Bancária ---");
        System.out.print("Digite o ID do Cliente titular: ");
        int idCliente = Integer.parseInt(scanner.nextLine());
        System.out.print("Digite o número da agência: ");
        int numAgencia = Integer.parseInt(scanner.nextLine());
        System.out.print("Digite o número da nova conta: ");
        int numConta = Integer.parseInt( scanner.nextLine());
        System.out.print("Digite o tipo da conta (Corrente, Poupança...): ");
        String tipoConta = scanner.nextLine();
        System.out.print("Digite a data de abertura (AAAA-MM-DD): ");
        String dataAbertura = scanner.nextLine();

        Conta_BancariaController contaController = new Conta_BancariaController();
        contaController.cadastrar_conta_bancaria(numConta, tipoConta, dataAbertura, idCliente, numAgencia);
    }

    private void realizarTransacao() {
        System.out.println("\n--- 6. Realizar Transação ---");
        try {
            System.out.print("Digite o ID do Cliente para ver suas contas: ");
            int idCliente = Integer.parseInt(scanner.nextLine());

            Conta_BancariaDAO contaDAO = new Conta_BancariaDAO();
            List<Conta_Bancaria> contas = contaDAO.buscarContasPorClienteID(idCliente);

            if (contas.isEmpty()) {
                System.out.println("Este cliente não possui contas bancárias.");
                return;
            }

            System.out.println("Contas encontradas para o cliente ID " + idCliente + ":");
            for (int i = 0; i < contas.size(); i++) {
                Conta_Bancaria conta = contas.get(i);
                System.out.printf("  %d. Conta: %-8s | Tipo: %-10s | Saldo: R$ %.2f%n", 
                                  (i + 1), conta.getnumero_conta(), conta.getTipoconta(), conta.getSaldo());
            }

            System.out.print("Escolha o número da conta para a transação: ");
            int escolha = Integer.parseInt(scanner.nextLine());

            if (escolha < 1 || escolha > contas.size()) {
                System.out.println("Escolha inválida.");
                return;
            }
            Conta_Bancaria contaEscolhida = contas.get(escolha - 1);
            int idConta = contaEscolhida.getidConta();
            System.out.println("Conta selecionada: " + contaEscolhida.getnumero_conta());

            System.out.print("Digite o ID do Tipo de Transação: ");
            int idTipo = Integer.parseInt(scanner.nextLine());
            System.out.print("Digite o valor da transação: ");
            double valor = Double.parseDouble(scanner.nextLine());
            System.out.print("Digite uma observação: ");
            String obs = scanner.nextLine();
            System.out.print("Digite a data da transação (AAAA-MM-DD): ");
            String data = scanner.nextLine();

            TransacaoController transacaoController = new TransacaoController();
            transacaoController.cadastrar_transacao(data, idConta, valor, idTipo, obs);

        } catch (NumberFormatException e) {
            System.out.println("ERRO: ID ou valor inválido. Por favor, digite apenas números.");
        }
    }
    
   private void cadastrarInvestimento() {
    System.out.println("\n--- 7. Cadastrar Investimento ---");
    try {
        
        System.out.print("Primeiro, digite o ID ou CPF do cliente: ");
        @SuppressWarnings("unused")
        String busca = scanner.nextLine();
        @SuppressWarnings("unused")
        ClienteController clienteController = new ClienteController();
        
        System.out.print("Digite o ID do Cliente: ");
        int idCliente = Integer.parseInt(scanner.nextLine());


        
        Conta_BancariaDAO contaDAO = new Conta_BancariaDAO();
        List<Conta_Bancaria> contas = contaDAO.buscarContasPorClienteID(idCliente);

        if (contas.isEmpty()) {
            System.out.println("Este cliente não possui contas bancárias para investir.");
            return;
        }

        System.out.println("Contas encontradas para o cliente ID " + idCliente + ":");
        for (int i = 0; i < contas.size(); i++) {
            Conta_Bancaria conta = contas.get(i);
            System.out.printf("  %d. Conta: %-8s | Tipo: %-10s | Saldo: R$ %.2f%n", 
                              (i + 1), 
                              conta.getnumero_conta(), 
                              conta.getTipoconta(), 
                              conta.getSaldo());
        }

        // --- ETAPA 3: Usuário escolhe a conta ---
        System.out.print("Escolha o número da conta na qual deseja investir: ");
        int escolha = Integer.parseInt(scanner.nextLine());

        if (escolha < 1 || escolha > contas.size()) {
            System.out.println("Escolha inválida.");
            return;
        }
        Conta_Bancaria contaEscolhida = contas.get(escolha - 1);
        int idConta = contaEscolhida.getidConta();
        System.out.println("Conta selecionada: " + contaEscolhida.getnumero_conta());


        
        System.out.print("Digite o ID do Tipo de Investimento: ");
        int idTipo = Integer.parseInt(scanner.nextLine());
        System.out.print("Digite o ID do Papel de Investimento: ");
        int idPapel = Integer.parseInt(scanner.nextLine());
        System.out.print("Digite o valor do investimento: ");
        double valor = Double.parseDouble(scanner.nextLine());
        
        if(valor > contaEscolhida.getSaldo()){
            System.out.println("ERRO: Saldo insuficiente para realizar este investimento.");
            return;
        }

        System.out.print("Digite a data de início (AAAA-MM-DD): ");
        String dataInicio = scanner.nextLine();
        System.out.print("Digite a data de vencimento (AAAA-MM-DD): ");
        String dataVenc = scanner.nextLine();

        
        InvestimentoController investimentoController = new InvestimentoController();
        investimentoController.cadastrar_investimento(dataInicio, dataVenc, valor, idTipo, idPapel, idConta);

    } catch (NumberFormatException e) {
        System.out.println("ERRO: ID ou valor inválido. Por favor, digite apenas números.");
    }
}
    
    private void gerarExtrato() {
        System.out.println("\n--- Gerar Extrato por Período ---");
        System.out.print("Digite o ID da Conta Bancária: ");
        int idConta = Integer.parseInt(scanner.nextLine());
        System.out.print("Digite a data de início (AAAA-MM-DD): ");
        String dataInicio = scanner.nextLine();
        System.out.print("Digite a data final (AAAA-MM-DD): ");
        String dataFim = scanner.nextLine();
        
        TransacaoController transacaoController = new TransacaoController();
        transacaoController.gerarExtratoPorPeriodo(idConta, dataInicio, dataFim);
    }
    
    private void cadastrarCliente() {
        System.out.println("\n--- Cadastrar Novo Cliente ---");

        System.out.print("Nome do Cliente: ");
        String nome = scanner.nextLine();
        System.out.print("CPF do Cliente: ");
        String cpf = scanner.nextLine();
        System.out.print("Número do Endereço: ");
        int numero = Integer.parseInt(scanner.nextLine());
        System.out.print("Complemento (se houver): ");
        String complemento = scanner.nextLine();

        // Dados do Endereço
        System.out.println("\n--- Dados do Endereço ---");
        System.out.print("CEP: ");
        String cep = scanner.nextLine();
        System.out.print("Logradouro: ");
        String nomeLogradouro = scanner.nextLine();
        System.out.print("Tipo de Logradouro (Rua, Avenida, Praça): ");
        String tipoLogradouro = scanner.nextLine();
        System.out.print("Bairro: ");
        String nomeBairro = scanner.nextLine();
        System.out.print("Cidade: ");
        String nomeCidade = scanner.nextLine();
        System.out.print("Sigla UF: ");
        String siglaUF = scanner.nextLine();

        
        UF uf = new UF();
        uf.setSiglaUF(siglaUF);

        Cidade cidade = new Cidade();
        cidade.setCidade(nomeCidade);
        cidade.setUF(uf);

        Bairro bairro = new Bairro();
        bairro.setBairro(nomeBairro);

        TipoLogra tipoLogra = new TipoLogra();
        tipoLogra.setTipoLogra(tipoLogradouro);

        Logra logra = new Logra();
        logra.setLogra(nomeLogradouro);
        logra.setTipologra(tipoLogra);

        Endereco endereco = new Endereco();
        endereco.setCEP(cep);
        endereco.setLogra(logra);
        endereco.setBairro(bairro);
        endereco.setCidade(cidade);

        
        ClienteController clienteController = new ClienteController();
        clienteController.cadastrar_cliente(nome, cpf, numero, complemento, endereco);
    }
    }
