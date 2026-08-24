package Controller;

import DAO.EnderecoDAO;
import classes.Endereco;

public class EnderecoController {

    private final EnderecoDAO endereco;

    public EnderecoController () {
        this.endereco = new EnderecoDAO ();
    }

    public void cadastrar_endereco (String CEP, String Bairro, String Logradouro,String Tipo, String Cidade, String UF){

        Endereco e = new Endereco(); 
        e.setCEP(CEP);
        e.getLogra().setLogra(Logradouro);
        e.getLogra().getTipologra().setTipoLogra(Tipo);
        e.getBairro().setBairro(Bairro);
        e.getCidade().setCidade(Cidade);
        e.getCidade().getUF().setUF(UF);

        endereco.cadastrarEndereco(e);
        
    }

}