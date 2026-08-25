package Controller;

import DAO.*;
import classes.casodeuso.*;
import classes.genericos.*;
import java.util.List;

public class ExameController {

    private final ExameDAO exameDAO;
    private final PacienteDAO pacienteDAO;

    public ExameController() {
        this.exameDAO = new ExameDAO();
        this.pacienteDAO = new PacienteDAO();
    }

    public void cadastrar_exame(String data, int idPaciente, int idTipoExame, int idResultado, String obs) {
        
        Paciente paciente = pacienteDAO.buscarPacientePorID(idPaciente);

        if (paciente == null) {
            System.out.println("Erro: Paciente ID " + idPaciente + " não encontrado no sistema.");
            return;
        }


        Exame novoExame = new Exame();
        novoExame.setData_exame(data); 
        novoExame.setObservacao(obs);

        TipoExame tipo = new TipoExame();
        tipo.setIdTipoExame(idTipoExame);
        novoExame.setTipo_exame(tipo);

        Resultado resultado = new Resultado();
        resultado.setIdResultado(idResultado);
        novoExame.setResultado(resultado);


        novoExame.setPaciente(paciente);


        exameDAO.cadastrarExame(novoExame);

        System.out.println("\nExame na data " + data + "cadastrado.");
    }
    

    public void listarExamesDoPaciente(int idPaciente) {
        System.out.println("-------------------------------------------------");
        System.out.println("Exames do Paciente ID: " + idPaciente );


        List<Exame> exames = exameDAO.buscarExamesPorPacienteID(idPaciente);

        if (exames.isEmpty()) {
            System.out.println("Nenhum exame encontrado para este paciente.");
        } else {
            // Cabeçalho da tabela formatado
            System.out.printf("%-10s | %-12s | %-25s | %-15s | %s%n", "Nro", "Data", "Tipo de Exame", "Resultado", "Observação");
            System.out.println(String.format("%90s", "").replace(' ', '-')); 
            
            for (Exame ex : exames) {
                
                String tipoExame = (ex.getTipo_exame() != null) ? ex.getTipo_exame().getTipoExame() : "Geral";
                String resultado = (ex.getResultado() != null) ? ex.getResultado().getResultado() : "Pendente";
                
                System.out.printf("%-10d | %-12s | %-25s | %-15s | %s%n",
                                  ex.getNro_exame(),
                                  ex.getData_exame(),
                                  tipoExame,
                                  resultado,
                                  ex.getObservacao());
            }
        }
    }
}