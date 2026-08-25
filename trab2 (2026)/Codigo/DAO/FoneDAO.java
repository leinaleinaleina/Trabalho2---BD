package DAO;

import classes.genericos.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList; 
import java.util.List;  

public class FoneDAO {
    private final String url = "jdbc:mysql://localhost:3306/mydb";
    
    private final String usuario = "root";
    private final String senha = "root";
    

public int cadastrarTelefone (TelefonePaciente fone) {
    int idFone = -1;

    String sqlSelect = "SELECT idFone FROM Fone WHERE Fone = ?";

    try (Connection con = DriverManager.getConnection(url, usuario, senha);
            PreparedStatement stmt = con.prepareStatement(sqlSelect)) {

        stmt.setString(1, fone.getTelefone());
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            idFone = rs.getInt("idFone");
        } else {
        
            String sqlInsert = "INSERT INTO Fone (Fone, DDD_idDDD, DDI_idDDI, Paciente_idPaciente) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmtInsert = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
                stmtInsert.setString(1, fone.getTelefone());
                stmtInsert.setInt (2 , fone.getDDD().getidDDD());
                stmtInsert.setInt(3, fone.getDDDI().getidDDDI());
                stmtInsert.setInt (4 , fone.getPaciente().getidPaciente());

                stmtInsert.executeUpdate();
                ResultSet generatedKeys = stmtInsert.getGeneratedKeys();
                if (generatedKeys.next()) {
                    idFone = generatedKeys.getInt(1);
                }
            }
        }
    } catch (SQLException e) {
        System.out.println("Erro ao buscar ou cadastrar Telefone de cliente: " + e.getMessage());
    }
    return idFone;
}




public List<TelefonePaciente> buscarFone (int idPaciente) {
    List<TelefonePaciente> telefonesEncontrados = new ArrayList<>();
    
String sql = "SELECT f.idFone, f.Fone, " +
             "d.idDDD, d.DDD, " +
             "di.idDDI, di.DDI " +
             "FROM Fone f " +
             "JOIN DDD d ON f.DDD_idDDD = d.idDDD " +
             "JOIN DDI di ON f.DDI_idDDI = di.idDDI " +
             "WHERE f.Paciente_idPaciente = ?";

    try (Connection con = DriverManager.getConnection(url, usuario, senha);
         PreparedStatement stmt = con.prepareStatement(sql)) {

        stmt.setInt(1, idPaciente);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
         DDDI dddi = new DDDI();
        dddi.setDDDI(rs.getInt("idDDI"));
        dddi.setDDDI(rs.getInt("DDI"));

        DDD ddd = new DDD();
        ddd.setDDD(rs.getInt("idDDD"));
        ddd.setDDD(rs.getInt("DDD"));

        TelefonePaciente fone = new TelefonePaciente();
        fone.setidTelefone(rs.getInt("idFone"));
        fone.setTelefone(rs.getString("Fone"));
        fone.setDDD(ddd);
        fone.setDDDI(dddi);

        telefonesEncontrados.add(fone);
        }

    } catch (SQLException e) {
        System.out.println("Erro ao buscar telefones do cliente: " + e.getMessage());
    }
    
    return telefonesEncontrados;
}
}
