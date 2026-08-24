package classes.genericos;

public class EmailPaciente {
    private String email;
    private int idEmail; 
    private Paciente Paciente;
 
    
    public void setidEmail (int idEmail) {
        this.idEmail = idEmail;
    }

    public int getidEmail() {
        return idEmail;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return String.valueOf(email);
    }

    public Paciente getPaciente() {
        return Paciente;
    }

    public void setPaciente(Paciente Paciente) {
        this.Paciente = Paciente;
    }

}