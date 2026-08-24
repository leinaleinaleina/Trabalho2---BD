package DAO;

import classes.UF;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UFDAO {
    private final String url = "jdbc:mysql://localhost:3306/mydb";

    private final String usuario = "root";
    private final String senha = "root";

    // Em UFDAO.java

public int cadastrarUF(UF uf) {
    int idUF = -1;

    String sqlSelect = "SELECT idUF FROM uf WHERE SiglaUF = ?";

    try (Connection con = DriverManager.getConnection(url, usuario, senha);
            PreparedStatement stmt = con.prepareStatement(sqlSelect)) {

        
        stmt.setString(1, uf.getSiglaUF()); 

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            idUF = rs.getInt("idUF");

        } else {
            String sqlInsert = "INSERT INTO uf (NomeUF, SiglaUF) VALUES (?, ?)";
            try (PreparedStatement stmtInsert = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
                stmtInsert.setString(1, uf.getUF());
                stmtInsert.setString(2 , uf.getSiglaUF());

                stmtInsert.executeUpdate();
                ResultSet generatedKeys = stmtInsert.getGeneratedKeys();
                if (generatedKeys.next()) {
                    idUF = generatedKeys.getInt(1);
                }
            }
        }
    } catch (SQLException e) {
        System.out.println("Erro ao buscar ou cadastrar UF: " + e.getMessage());
    }

    return idUF;
}
}