package Controller;

import DAO.*;
import classes.genericos.*;

public class DDDIController {

    private final DDDIDAO dddiDAO;

    public DDDIController() {
        this.dddiDAO = new DDDIDAO();
    }

    public void cadastrarDDI(int numeroDDI) {
        
        DDDI novoDDI = new DDDI();
        novoDDI.setDDDI(numeroDDI); 
        
        dddiDAO.cadastrarDDDI(novoDDI);
        
        System.out.println("DDI (+" + numeroDDI + ") cadastrado no sistema");
    }
}