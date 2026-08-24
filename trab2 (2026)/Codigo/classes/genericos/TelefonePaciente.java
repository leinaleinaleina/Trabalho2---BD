package classes.genericos;

public class TelefonePaciente {
    public String telefone;
    private  DDD ddd;
    private DDDI dddi;
    private int idTelefone;
    private Paciente Paciente;

    public void setidTelefone (int idTelefone) {
        this.idTelefone = idTelefone;
    }

    public int getidTelefone() {
        return idTelefone;
    }

    public TelefonePaciente () {
        this.ddd = new DDD();
        this.dddi = new DDDI();
    }

    public void setTelefone (String telefone) {
        this.telefone = telefone;
    }

    public String getTelefone () {
        return telefone;
    }

     public void setDDD(DDD ddd) {
        this.ddd = ddd;
    }
    public DDD getDDD() {
        return ddd;
    }

    public DDDI getDDDI() {
        return dddi;
    }

    public void setDDDI (DDDI dddi) {
        this.dddi = dddi;
    }

    @Override
    public String toString() {
        return String.valueOf(telefone);
    }

    public void setPaciente (Paciente Paciente) {
        this.Paciente = Paciente;
    }

    public Paciente getPaciente() {
        return Paciente;
    }

}