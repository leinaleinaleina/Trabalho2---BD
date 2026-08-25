
package Controller;

import DAO.FoneDAO;
import classes.genericos.*;

public class TelefoneClienteController {

    
    
    private final FoneDAO telefoneDAO;

    public TelefoneClienteController() {
        this.telefoneDAO = new FoneDAO();
    }
    public void cadastrar_fone_cliente(String numero, int idDoDDD, int idPaciente) {
        
        TelefonePaciente novoTelefone = new TelefonePaciente();
        novoTelefone.setTelefone(numero);

        DDD ddd = new DDD();
        ddd.setidDDD(idDoDDD);
        novoTelefone.setDDD(ddd);
 
        Paciente paciente = new Paciente();
        paciente.setidPaciente(idPaciente);
        novoTelefone.setPaciente (paciente); 

        
        telefoneDAO.cadastrarTelefone(novoTelefone);
        System.out.println("Telefone cadastrado para o paciente ID " + idPaciente);
    }
}