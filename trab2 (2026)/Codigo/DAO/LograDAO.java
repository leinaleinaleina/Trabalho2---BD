package DAO;

import classes.Logra;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LograDAO {
    private final String url = "jdbc:mysql://localhost:3306/mydb";
    
    private final String usuario = "root";
    private final String senha = "root";

    public int cadastrarLogradouro(Logra logradouro) {
    int idLogradouro = -1;

    // Verifica se o logradouro já existe
    String sqlSelect = "SELECT idLogradouro FROM Logradouro WHERE Logradouro = ?";

    try (Connection con = DriverManager.getConnection(url, usuario, senha);
         PreparedStatement stmt = con.prepareStatement(sqlSelect)) {

        stmt.setString(1, logradouro.getLogra());

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            idLogradouro = rs.getInt("idLogradouro");
        } else {
            // Se não existir, insere o logradouro novo
            String sqlInsert = "INSERT INTO Logradouro (Logradouro, Tipo_Logradouro_idTipo_Logradouro) VALUES (?, ?)";
            try (PreparedStatement stmtInsert = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
                stmtInsert.setString(1, logradouro.getLogra());
                stmtInsert.setInt(2, logradouro.getTipologra().getidTipologra());

                stmtInsert.executeUpdate();
                ResultSet generatedKeys = stmtInsert.getGeneratedKeys();
                if (generatedKeys.next()) {
                    idLogradouro = generatedKeys.getInt(1); 
                }
            }
        }
    } catch (SQLException e) {
        System.out.println("Erro ao buscar ou cadastrar logradouro: " + e.getMessage());
    }

    return idLogradouro;
}

}