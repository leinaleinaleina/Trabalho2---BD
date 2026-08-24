// Em src/DAO/DDDDAO.java
package DAO;

import classes.genericos.DDD;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DDDDAO {
    private final String url = "jdbc:mysql://localhost:3306/mydb";
    private final String usuario = "root";
    private final String senha = "root";

    // <<< NOME DO MÉTODO CORRIGIDO AQUI >>>
    public int cadastrarDDDD (DDD ddd) {
      int idDDD = -1;

        String sqlSelect = "SELECT idDDD FROM DDD WHERE DDD = ?";

        try (Connection con = DriverManager.getConnection(url, usuario, senha);
                PreparedStatement stmt = con.prepareStatement(sqlSelect)) {

            stmt.setInt(1, ddd.getDDD());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                idDDD = rs.getInt("idDDD");
            } else {
                String sqlInsert = "INSERT INTO DDD (DDD) VALUES (?)";
                try (PreparedStatement stmtInsert = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
                    stmtInsert.setInt(1, ddd.getDDD());

                    stmtInsert.executeUpdate();
                    ResultSet generatedKeys = stmtInsert.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        idDDD = generatedKeys.getInt(1); 
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar ou cadastrar DDD: " + e.getMessage());
        }
        return idDDD;
    }
}