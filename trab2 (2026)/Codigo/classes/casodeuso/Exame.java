package classes.casodeuso;
import classes.genericos.*;

public class Exame {
    private int nro_exame;
    private String data_exame;
    private String observacao;
    private Paciente paciente;
    private TipoExame tipo_exame;
    private Resultado resultado;

    public Exame() {
        this.paciente = new Paciente();
        this.resultado = new Resultado();
        this.tipo_exame = new TipoExame();
    }

    public int getNro_exame() {
        return nro_exame;
    }

    public void setNro_exame(int nro_exame) {
        this.nro_exame = nro_exame;
    }

    public String getData_exame() {
        return data_exame;
    }

    public void setData_exame(String data_exame) {
        this.data_exame = data_exame;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public TipoExame getTipo_exame() {
        return tipo_exame;
    }

    public void setTipo_exame(TipoExame tipo_exame) {
        this.tipo_exame = tipo_exame;
    }

    public Resultado getResultado() {
        return resultado;
    }

    public void setResultado(Resultado resultado) {
        this.resultado = resultado;
    }
    
}