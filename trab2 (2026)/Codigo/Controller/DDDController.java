package Controller;

import DAO.DDDDAO;
import DAO.DDDIDAO;
import classes.genericos.*;

public class DDDController {

    private final DDDDAO dddDAO; 

    public DDDController() {
        this.dddDAO = new DDDDAO();
    }

    public int cadastrar_DDD(int dddNum, DDDI dddiObj) {
        
        
        DDDIDAO dddiDAO = new DDDIDAO();
        int idDoDDDI = dddiDAO.cadastrarDDDI(dddiObj);

        
        DDD dddParaSalvar = new DDD();
        dddParaSalvar.setDDD(dddNum);
        
        
        dddiObj.setidDDDI(idDoDDDI);
        dddParaSalvar.setDDD(dddiObj);
        
       
        int idGerado = dddDAO.cadastrarDDDD(dddParaSalvar);
        
       
        return idGerado;
    }
}