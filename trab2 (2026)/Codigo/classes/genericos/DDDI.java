package classes.genericos;

public class DDDI {
    public int DDDI;
    private int idDDDI;

    public void setidDDDI (int idDDDI) {
        this.idDDDI = idDDDI;
    }

    public int getidDDDI() {
        return idDDDI;
    }

    public void setDDDI (int DDDI) {
        this.DDDI = DDDI;
    }

    public int getDDDI () {
        return DDDI;
    }

     @Override
    public String toString() {
        return String.valueOf(DDDI);
    }
}