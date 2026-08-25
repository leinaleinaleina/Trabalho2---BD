package Controller;

import DAO.RelatorioAlgebraDAO;
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
        System.out.println("\n========= PRONTUÁRIO DO PACIENTE =========");
        System.out.println("1. Buscar Paciente");
        System.out.println("2. a) Produto Cartesiano (Paciente x Médico)");
        System.out.println("3. b) Listar todos os Emails (UNION)");
        System.out.println("4. c) Exames não feitos em Jul/2026");
        System.out.println("0. Sair do Sistema");
        System.out.println("===================================================");
        System.out.print("Digite a opção que deseja consultar: ");
    }

    private void processarOpcao(int opcao) {
        RelatorioAlgebraDAO algebraDAO = new RelatorioAlgebraDAO();
        
        switch (opcao) {
            case 1 -> buscarPaciente();
            case 2 -> algebraDAO.executarProdutoCartesiano();
            case 3 -> algebraDAO.executarUnionEmails();
            case 4 -> algebraDAO.executarDiferencaExames();
            case 0 -> System.out.println("Encerrando o sistema...");
            default -> System.out.println("Opção inválida! Tente novamente.");
        }
    }

    private void buscarPaciente() {
        PacienteController pacienteController = new PacienteController();
        System.out.print("Digite o ID ou Documento do paciente: ");
        String busca = scanner.nextLine();
        try {
            int id = Integer.parseInt(busca);
            pacienteController.buscarPacientePorID(id);
        } catch (NumberFormatException e) {
            pacienteController.buscarPacientePorDocumento(busca);
        }
    }
}