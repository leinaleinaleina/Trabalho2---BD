package classes.genericos;

public class Logra{
    public String logradouro;
    private TipoLogra tipo_logradouro;
    private int idLogra;

    public Logra () {
        this.tipo_logradouro = new TipoLogra();
    }


    public void setidLogra (int idLogra) {
        this.idLogra = idLogra;
    }

    public int getidLogra() {
        return idLogra;
    }

    public void setLogra (String logradouro) {
        this.logradouro = logradouro;
    }

    public String getLogra() {
        return logradouro;
    }

    public void setTipologra (TipoLogra tipo_logradouro) {
        this.tipo_logradouro = tipo_logradouro;
    }

    public TipoLogra getTipologra () {
        return tipo_logradouro;
    }

    @Override
    public String toString() {
        return String.valueOf(logradouro);
    }
}