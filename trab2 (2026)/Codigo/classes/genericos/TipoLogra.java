package classes.genericos;

public class TipoLogra {
    private String tipo_logra;
    private int idTipologra;

    public void setidTipologra (int idTipologra) {
        this.idTipologra = idTipologra;
    }

    public int getidTipologra() {
        return idTipologra;
    }


    public void setTipoLogra(String tipo_logra) {
        this.tipo_logra = tipo_logra;
    }

    public String getTipoLogra() {
        return tipo_logra;
    }

    @Override
    public String toString() {
        return String.valueOf(tipo_logra);
    }
}