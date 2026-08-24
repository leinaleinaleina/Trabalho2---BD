package classes.genericos;

public class Medico {

    private String CRM;
    private String nome_medico;
    private String area;
    
    public String getCRM() {
        return CRM;
    }
    public void setCRM(String cRM) {
        CRM = cRM;
    }
    public String getNome_medico() {
        return nome_medico;
    }
    public void setNome_medico(String nome_medico) {
        this.nome_medico = nome_medico;
    }
    public String getArea() {
        return area;
    }
    public void setArea(String area) {
        this.area = area;
    }
 
}