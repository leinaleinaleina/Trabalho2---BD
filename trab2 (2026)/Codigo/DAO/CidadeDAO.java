package DAO;

import classes.Cidade;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class CidadeDAO {
    private final String url = "jdbc:mysql://localhost:3306/mydb";
    
    private final String usuario = "root";
    private final String senha = "root";

// Em CidadeDAO.java

public int cadastrarCidade(Cidade cidade) {
    int idCidade = -1;

    String sqlSelect = "SELECT idCidade FROM Cidade WHERE Cidade = ? AND UF_idUF = ?";

    try (Connection con = DriverManager.getConnection(url, usuario, senha);
         PreparedStatement stmt = con.prepareStatement(sqlSelect)) {

        stmt.setString(1, cidade.getCidade());
        stmt.setInt(2, cidade.getUF().getidUF()); 

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            idCidade = rs.getInt("idCidade");
            
        } else {
           
            String sqlInsert = "INSERT INTO Cidade (Cidade, UF_idUF) VALUES (?, ?)"; 
            try (PreparedStatement stmtInsert = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
                stmtInsert.setString(1, cidade.getCidade());
                stmtInsert.setInt(2, cidade.getUF().getidUF()); 

                stmtInsert.executeUpdate();
                ResultSet generatedKeys = stmtInsert.getGeneratedKeys();
                if (generatedKeys.next()) {
                    idCidade = generatedKeys.getInt(1);
                }
            }
        }
    } catch (SQLException e) {
        System.out.println("Erro ao buscar ou cadastrar cidade: " + e.getMessage());
    
    }

    return idCidade;
}
}