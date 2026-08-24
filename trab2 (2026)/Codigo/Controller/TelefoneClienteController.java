
package Controller;

import DAO.TelefoneClienteDAO;
import classes.Cliente;
import classes.DDD;
import classes.TelefoneCliente;

public class TelefoneClienteController {

    private final TelefoneClienteDAO telefoneDAO;

    public TelefoneClienteController() {
        this.telefoneDAO = new TelefoneClienteDAO();
    }
    public void cadastrar_fone_cliente(String numero, int idDoDDD, int idDoCliente) {
        
        TelefoneCliente novoTelefone = new TelefoneCliente();
        novoTelefone.setTelefone(numero);

        DDD ddd = new DDD();
        ddd.setidDDD(idDoDDD);
        novoTelefone.setDDD(ddd);
 
        Cliente cliente = new Cliente();
        cliente.setidCliente(idDoCliente);
        novoTelefone.setCliente(cliente); 

        
        telefoneDAO.cadastrarTelefone(novoTelefone);
        System.out.println("Telefone cadastrado para o cliente ID " + idDoCliente);
    }
}