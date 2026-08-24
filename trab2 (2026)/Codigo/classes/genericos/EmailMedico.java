package classes.genericos;

public class EmailMedico {
    private String email;
    private int idEmail; 
    private Medico medico;
 
    
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

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

}