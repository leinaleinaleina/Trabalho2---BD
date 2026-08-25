package Controller;

import DAO.*;
import classes.casodeuso.*;
import classes.genericos.*; 
import java.util.List;         

public class PacienteController {

    private final PacienteDAO pac;

    public PacienteController () {
        this.pac = new PacienteDAO();
    }

    public void buscarPacientePorDocumento(String documento) {

        Paciente paciente = pac.buscarClientePorDoc(documento);

        if (paciente != null) {
            System.out.println("=======================================================");
            System.out.println("Paciente " + paciente.getidPaciente());
            
            System.out.println("Nome: " + paciente.getnome_Paciente());
            System.out.println("Documento: " + paciente.getdocumento_Paciente());
            System.out.println("Data de Nascimento: " + paciente.getData_nascimento());
            System.out.println("Sexo: " + paciente.getSexo().getSexo());
            System.out.println("Estado Civil: " + paciente.getEstadocivil().getEstadoCivil());

            System.out.println("-------------------------------------------\n");
            System.out.println("Endereço");
            System.out.println("CEP: " + paciente.getEndereco().getCEP());
            // Voltando para getLogra() e getTipologra()
            System.out.println("Logradouro: " + paciente.getEndereco().getLogra().getTipologra().getTipoLogra() + " " + paciente.getEndereco().getLogra().getLogra());
            System.out.println("Número: " + paciente.getNumero()); 
            System.out.println("Complemento: " + paciente.getcomp_Paciente()); 
            System.out.println("Bairro: " + paciente.getEndereco().getBairro().getBairro());
            System.out.println("Cidade/UF: " + paciente.getEndereco().getCidade().getCidade() + " - " + paciente.getEndereco().getCidade().getUF().getSiglaUF());
            
            System.out.println("-------------------------------------------");
            System.out.println("Telefones");
            FoneDAO telefoneDAO = new FoneDAO();
            List<TelefonePaciente> telefones = telefoneDAO.buscarFone(paciente.getidPaciente());

            if (telefones.isEmpty()) {
                System.out.println("Nenhum telefone cadastrado para este paciente.");
            } else {
                for (TelefonePaciente fone : telefones) {
                    // Voltando para getDDDI()
                    System.out.println("  +" + fone.getDDDI().getDDDI() + " (" + fone.getDDD().getDDD() + ") " + fone.getTelefone());
                }
            }
            
            System.out.println("-------------------------------------------");
            System.out.println("Emails");
            EmailDAO emailDAO = new EmailDAO(); 
            List<EmailPaciente> emails = emailDAO.buscarEmailsPorPacienteID(paciente.getidPaciente()); 

            if (emails.isEmpty()) {
                System.out.println("Nenhum email cadastrado para este paciente.");
            } else {
                for (EmailPaciente email : emails) {
                    System.out.println("  - " + email.getEmail());
                }
            }
            
            System.out.println("-------------------------------------------");
            System.out.println("Histórico de Consultas ");
            ConsultaDAO consultaDAO = new ConsultaDAO();

            List<Consulta> consultas = consultaDAO.buscarConsultaPorContaID(paciente.getidPaciente());

            if (consultas.isEmpty()) {
                System.out.println("Nenhuma consulta registrada para este paciente.");
            } else {
                System.out.printf("%-10s | %-12s | %-20s | %-20s | %s%n", "Nro", "Data", "Médico", "Especialidade", "Diagnóstico (CID)");
                System.out.println(String.format("%90s", "").replace(' ', '-')); 
                
                for (Consulta consulta : consultas) {
                    // Voltando para getNome_medico()
                    String medico = (consulta.getMedico() != null) ? consulta.getMedico().getNome_medico() : "N/D";
                    String area = (consulta.getMedico() != null) ? consulta.getMedico().getArea() : "N/D";
                    String cid = (consulta.getDiagnostico() != null) ? consulta.getDiagnostico().getCID() : "N/D";

                    System.out.printf("%-10d | %-12s | %-20s | %-20s | %s%n",
                                      consulta.getNro_consulta(),
                                      consulta.getData_consulta(),
                                      medico,
                                      area,
                                      cid);
                }
            }
            
            System.out.println("-------------------------------------------");
            System.out.println("Histórico de Exames");
            ExameDAO exameDAO = new ExameDAO();
            List<Exame> exames = exameDAO.buscarExamesPorPacienteID(paciente.getidPaciente());

            if (exames.isEmpty()) {
                System.out.println("Nenhum exame registrado para este paciente.");
            } else {
                System.out.printf("%-10s | %-12s | %-20s | %-15s | %s%n", "Nro", "Data", "Tipo de Exame", "Resultado", "Observação");
                System.out.println(String.format("%90s", "").replace(' ', '-')); 
                
                for (Exame exame : exames) {
                    // Voltando para getTipo_exame()
                    String tipoExame = (exame.getTipo_exame() != null) ? exame.getTipo_exame().getTipoExame() : "N/D";
                    String resultado = (exame.getResultado() != null) ? exame.getResultado().getResultado() : "Pendente";

                    System.out.printf("%-10d | %-12s | %-20s | %-15s | %s%n",
                                      exame.getNro_exame(),
                                      exame.getData_exame(),
                                      tipoExame,
                                      resultado,
                                      exame.getObservacao());
                }
            }
            System.out.println("-------------------------------------------");

        } else {
            System.out.println("Nenhum paciente encontrado com o Documento: " + documento);
        }
    }
   
    public void buscarPacientePorID(int id) {

        Paciente paciente = pac.buscarPacientePorID(id);
        
        if (paciente != null) {
            buscarPacientePorDocumento(paciente.getdocumento_Paciente());
            
        } else {
            System.out.println("Nenhum paciente encontrado com o ID: " + id);
        }
    }
}