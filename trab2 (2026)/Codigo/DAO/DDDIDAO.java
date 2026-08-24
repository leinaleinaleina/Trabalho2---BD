package DAO;

import classes.DDDI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DDDIDAO {
    private final String url = "jdbc:mysql://localhost:3306/mydb";
    
    private final String usuario = "root";
    private final String senha = "root";

    public int cadastrarDDDI (DDDI dddi) {
       int idDDDI = -1;

        // Verifica se o logradouro já existe
        String sqlSelect = "SELECT idDDDI FROM DDDI WHERE DDDI = ?";

        try (Connection con = DriverManager.getConnection(url, usuario, senha);
                PreparedStatement stmt = con.prepareStatement(sqlSelect)) {

            stmt.setInt(1, dddi.getDDDI());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                idDDDI = rs.getInt("idDDDI");

            } else {
                String sqlInsert = "INSERT INTO DDDI (DDDI) VALUES (?)";
                try (PreparedStatement stmtInsert = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
                    stmtInsert.setInt(1, dddi.getDDDI());

                    stmtInsert.executeUpdate();
                    ResultSet generatedKeys = stmtInsert.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        idDDDI = generatedKeys.getInt(1); 
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar ou cadastrar DDDDI: " + e.getMessage());
        }

        return idDDDI;
    }
}    