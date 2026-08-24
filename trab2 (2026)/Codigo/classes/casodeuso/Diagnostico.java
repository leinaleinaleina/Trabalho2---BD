package classes.casodeuso;

public class Diagnostico {
    private String CID;
    private String nome_CID;
    private String descricao;

    public String getCID() {
        return CID;
    }

    public void setCID(String CID) {
        this.CID = CID;
    }

    public String getNome_CID() {
        return nome_CID;
    }

    public void setNome_CID(String nome_CID) {
        this.nome_CID = nome_CID;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}