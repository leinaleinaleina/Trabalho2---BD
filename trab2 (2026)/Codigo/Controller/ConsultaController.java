package Controller;

import DAO.*;
import classes.casodeuso.*;
import classes.genericos.*;
import java.util.List;

public class ConsultaController {

    private final ConsultaDAO consultaDAO;
    private final PacienteDAO pacienteDAO;

    public ConsultaController() {
        this.consultaDAO = new ConsultaDAO();
        this.pacienteDAO = new PacienteDAO();
    }

    public void cadastrar_consulta(String dataConsulta, String cid, String crm, int idPaciente) {
        
        Paciente paciente = pacienteDAO.buscarPacientePorID(idPaciente);
        
        if (paciente == null) {
            System.out.println("Erro: Paciente ID " + idPaciente + " não encontrado no sistema.");
            return;
        }
        
        Consulta novaConsulta = new Consulta();
        novaConsulta.setData_consulta(dataConsulta);
        
        Diagnostico diagnostico = new Diagnostico();
        diagnostico.setCID(cid);
        novaConsulta.setDiagnostico(diagnostico);
        
        Medico medico = new Medico();
        medico.setCRM(crm);
        novaConsulta.setMedico(medico);
        
        novaConsulta.setPaciente(paciente);
        
        consultaDAO.cadastrarConsulta(novaConsulta);
        
        System.out.println("\n==> Consulta na data " + dataConsulta + " cadastrada com sucesso! <==");
    }

    public void listarConsultasDoPaciente(int idPaciente) {
        System.out.println("-------------------------------------------------");
        System.out.println("Consultas do Paciente ID: " + idPaciente );

        List<Consulta> consultas = consultaDAO.buscarConsultasPorPacienteID(idPaciente);

        if (consultas.isEmpty()) {
            System.out.println("Nenhuma consulta encontrada para este paciente.");
        } else {
            System.out.printf("%-10s | %-12s | %-15s | %s%n", "Nro", "Data", "CRM Médico", "CID Diagnóstico");
            System.out.println(String.format("%70s", "").replace(' ', '-')); 
            
            for (Consulta cons : consultas) {
                String cidDiag = (cons.getDiagnostico() != null) ? cons.getDiagnostico().getCID() : "Nao houve diagnostico.";
                String crmMed =  (cons.getMedico() != null) ? cons.getMedico().getCRM() : "Atendido por enfermeira ou estagiario";
                
                System.out.printf("%-10d | %-12s | %-15s | %s%n",
                                  cons.getNro_consulta(),
                                  cons.getData_consulta(),
                                  crmMed,
                                  cidDiag);
            }
        }
    }
}