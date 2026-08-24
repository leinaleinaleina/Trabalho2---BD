// Em src/Controller/EmailController.java
package Controller;

import DAO.EmailDAO;
import classes.Email;

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
        Email novoEmail = new Email();
        novoEmail.setEmail(enderecoEmail);
        
        emailDAO.cadastrarEmail(novoEmail, idCliente);
        System.out.println("Email '" + enderecoEmail + "' cadastrado com sucesso para o cliente ID: " + idCliente);
    }
}