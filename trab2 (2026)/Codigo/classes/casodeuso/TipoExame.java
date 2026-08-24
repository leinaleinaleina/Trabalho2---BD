package classes.casodeuso;

public class TipoExame {
    private int idTipoExame;
    private String tipo_exame;
    private String Descricao;

    
    public int getIdTipoExame() {
        return idTipoExame;
    }
    public void setIdTipoExame(int idTipoExame) {
        this.idTipoExame = idTipoExame;
    }
    public String getTipoExame() {
        return tipo_exame;
    }
    public void setTipoExame(String tipo_exame) {
        this.tipo_exame = tipo_exame;
    }
    public String getDescricao() {
        return Descricao;
    }
    public void setDescricao(String descricao) {
        this.Descricao = descricao;
    }
}