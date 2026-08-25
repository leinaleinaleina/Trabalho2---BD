package Controller;

import DAO.DDDDAO;
import classes.genericos.*;

public class DDDController {

    private final DDDDAO dddDAO; 

    public DDDController() {
        this.dddDAO = new DDDDAO();
    }

    public int cadastrar_DDD(int dddNum, DDDI dddiObj) {
        
        
        DDD dddParaSalvar = new DDD();
        dddParaSalvar.setDDD(dddNum);
        
       
        int idGerado = dddDAO.cadastrarDDDD(dddParaSalvar);
        
       
        return idGerado;
    }
}