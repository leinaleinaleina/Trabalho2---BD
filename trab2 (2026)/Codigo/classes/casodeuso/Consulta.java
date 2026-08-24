package classes.casodeuso;
import classes.genericos.*;


public class Consulta {
    private int nro_consulta;
    private String data_consulta;
    private Medico medico;
    private Paciente paciente;
    private Diagnostico diagnostico;

    public Consulta() {
        this.paciente = new Paciente();
        this.medico = new Medico();
        this.diagnostico = new Diagnostico();
    }

    public int getNro_consulta() {
        return nro_consulta;
    }

    public void setNro_consulta(int nro_consulta) {
        this.nro_consulta = nro_consulta;
    }

    public String getData_consulta() {
        return data_consulta;
    }

    public void setData_consulta(String data_consulta) {
        this.data_consulta = data_consulta;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Diagnostico getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(Diagnostico diagnostico) {
        this.diagnostico = diagnostico;
    }
    

}