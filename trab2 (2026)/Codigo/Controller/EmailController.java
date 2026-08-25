// Em src/Controller/EmailController.java
package Controller;

import DAO.EmailDAO;
import classes.genericos.*;

public class EmailController {
    private final EmailDAO emailDAO;

    public EmailController() {
        this.emailDAO = new EmailDAO();
    }

    public void cadastrarEmailParaCliente(String enderecoEmail, int idCliente) {
        if (idCliente <= 0 || enderecoEmail == null || enderecoEmail.trim().isEmpty()) {
            System.out.println("ID do cliente ou endereço de email inválido.");
            return;
        }
        EmailPaciente novoEmail = new EmailPaciente();
        novoEmail.setEmail(enderecoEmail);
        
        emailDAO.cadastrarEmailPaciente(novoEmail, idCliente);
        System.out.println("Email '" + enderecoEmail + "' cadastrado com sucesso para o cliente ID: " + idCliente);
    }
}