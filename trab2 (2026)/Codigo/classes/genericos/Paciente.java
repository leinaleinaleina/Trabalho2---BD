package classes.genericos;

public class Paciente{
    public String nome_paciente;
    private String documento;
    private String complemento;
    private String data_nascimento;
    private Sexo sexo;
    private EstadoCivil estadocivil;
    private String numero;
    private int idPaciente;
    @SuppressWarnings("FieldMayBeFinal")
    private Endereco endereco;

    public Paciente () {
        this.endereco = new Endereco();
        this.sexo = new Sexo();
        this.estadocivil = new EstadoCivil(); 
    }

    public void setidPaciente (int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public int getidPaciente() {
        return idPaciente;
    }

    public void setnome_Paciente (String nome_paciente) {
        this.nome_paciente = nome_paciente;
    }

    public String getnome_Paciente () {
        return nome_paciente;
    }


    public void setdocumento_Paciente (String documento) {
        this.documento = documento;
    }

    public String getdocumento_Paciente () {
        return documento;
    }
    

    public void setcomp_Paciente (String complemento) {
        this.complemento = complemento;
    }

    public String getcomp_Paciente () {
        return complemento;
    }

    public void setEndereco (Endereco endereco) {
        this.endereco = endereco;
    }

    public Endereco getEndereco () {
        return endereco;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public EstadoCivil getEstadocivil() {
        return estadocivil;
    }

    public void setEstadocivil(EstadoCivil estadocivil) {
        this.estadocivil = estadocivil;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getData_nascimento() {
        return data_nascimento;
    }

    public void setData_nascimento(String data_nascimento) {
        this.data_nascimento = data_nascimento;
    }

}