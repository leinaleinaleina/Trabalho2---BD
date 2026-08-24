package DAO;

import classes.DDD;
import classes.DDDI;
import classes.TelefoneCliente;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList; 
import java.util.List;  

public class TelefoneClienteDAO {
    private final String url = "jdbc:mysql://localhost:3306/mydb";
    
    private final String usuario = "root";
    private final String senha = "root";
    

  // Em TelefoneClienteDAO.java
public int cadastrarTelefone (TelefoneCliente fone) {
    int idFone = -1;

    // A busca por telefone existente está OK.
    String sqlSelect = "SELECT idTelefone FROM telefone_cliente WHERE Nro_telefone = ?";

    try (Connection con = DriverManager.getConnection(url, usuario, senha);
            PreparedStatement stmt = con.prepareStatement(sqlSelect)) {

        stmt.setString(1, fone.getTelefone());
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            idFone = rs.getInt("idTelefone");
        } else {
        
            String sqlInsert = "INSERT INTO Telefone_cliente (Nro_telefone, DDD_idDDD, Cliente_idCliente) VALUES (?, ?, ?)";
            try (PreparedStatement stmtInsert = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
                stmtInsert.setString(1, fone.getTelefone());
                stmtInsert.setInt (2 , fone.getDDD().getidDDD());
                stmtInsert.setInt (3 , fone.getCliente().getidCliente());

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




public List<TelefoneCliente> buscarTelefonesPorClienteID(int idDoCliente) {
    List<TelefoneCliente> telefonesEncontrados = new ArrayList<>();
    
    String sql = "SELECT tc.Nro_telefone, d.DDD, di.DDDI FROM Telefone_cliente tc " +
                 "JOIN DDD d ON tc.DDD_idDDD = d.idDDD " +
                 "JOIN DDDI di ON d.DDDI_idDDDI = di.idDDDI " + 
                 "WHERE tc.Cliente_idCliente = ?";

    try (Connection con = DriverManager.getConnection(url, usuario, senha);
         PreparedStatement stmt = con.prepareStatement(sql)) {

        stmt.setInt(1, idDoCliente);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            TelefoneCliente telefone = new TelefoneCliente();
            DDD ddd = new DDD();
            DDDI dddi = new DDDI(); 
            
            dddi.setDDDI(rs.getInt("DDDI")); 
            
            ddd.setDDD(rs.getInt("DDD")); 
            ddd.setDDDI(dddi); 
            
            telefone.setTelefone(rs.getString("Nro_telefone"));
            telefone.setDDD(ddd); 
            
            telefonesEncontrados.add(telefone);
        }

    } catch (SQLException e) {
        System.out.println("Erro ao buscar telefones do cliente: " + e.getMessage());
    }
    
    return telefonesEncontrados;
}
}
