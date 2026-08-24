package Controller;

import DAO.AgenciaDAO;
import DAO.BancoDAO;
import classes.Agencia;
import classes.Banco;
import java.util.Scanner;

public class BancoAgenciaController {

    private final BancoDAO bancoDAO;
    private final AgenciaDAO agenciaDAO;

    public BancoAgenciaController() {
        this.bancoDAO = new BancoDAO();
        this.agenciaDAO = new AgenciaDAO();
    }

    
public Agencia encontrarAgenciaInterativamente(Scanner scanner) {
    Banco bancoEncontrado = null;
    Agencia agenciaEncontrada = null;

    // Etapa 1: Usuário escolhe o Banco
    while (bancoEncontrado == null) {
        System.out.print("Digite o nome do banco (ex: Banco do Brasil): ");
        String nomeBanco = scanner.nextLine();
        bancoEncontrado = bancoDAO.buscarBancoPorNome(nomeBanco);

        if (bancoEncontrado == null) {
            System.out.println("Banco não encontrado. Tente novamente.");
        }
    }

    while (agenciaEncontrada == null) {
        System.out.print("Digite o número da agência do '" + bancoEncontrado.getnomeBanco() + "': ");
        try {
            int numAgencia = Integer.parseInt(scanner.nextLine());
            agenciaEncontrada = agenciaDAO.buscarAgenciaPorNumeroEBancoID(numAgencia, bancoEncontrado.getcodBanco());

            if (agenciaEncontrada == null) {
                System.out.println("Agência " + numAgencia + " não encontrada para este banco. Tente novamente.");
            }
        } catch (NumberFormatException e) {
            System.out.println("ERRO: Por favor, digite apenas números para a agência.");
        }
    }

    System.out.println("OK! Agência " + agenciaEncontrada.getCodAgencia() + " selecionada. Banco: " + bancoEncontrado.getnomeBanco() + " (Cód: " + bancoEncontrado.getcodBanco() + ")");

    return agenciaEncontrada;
}
}