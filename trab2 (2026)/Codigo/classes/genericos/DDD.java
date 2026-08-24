package classes.genericos;

public class DDD{
    public int DDD;
    private int idDDD;

    public void setidDDD (int idDDD) {
        this.idDDD = idDDD;
    }

    public int getidDDD() {
        return idDDD;
    }


    public void setDDD (int DDD) {
        this.DDD = DDD;
    }

    public int getDDD () {
        return DDD;
    }

    @Override
    public String toString() {
        return String.valueOf(DDD);
    }
}