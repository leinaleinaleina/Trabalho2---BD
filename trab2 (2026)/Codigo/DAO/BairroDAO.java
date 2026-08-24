package DAO;

import classes.genericos.Bairro;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BairroDAO {
    private final String url = "jdbc:mysql://localhost:3306/mydb";
    
    private final String usuario = "root";
    private final String senha = "root";

    public int cadastrarBairro(Bairro bairro) {
    int idBairro = -1;

    // Verifica se o bairro já existe
    String sqlSelect = "SELECT idBairro FROM Bairro WHERE Bairro = ?";

    try (Connection con = DriverManager.getConnection(url, usuario, senha);
         PreparedStatement stmt = con.prepareStatement(sqlSelect)) {

        stmt.setString(1, bairro.getBairro());

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            idBairro = rs.getInt("idBairro");
        } else {
            // Se não existir, insere o bairro novo
            String sqlInsert = "INSERT INTO Bairro (Bairro) VALUES (?)";
            try (PreparedStatement stmtInsert = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
                stmtInsert.setString(1, bairro.getBairro());

                stmtInsert.executeUpdate();
                ResultSet generatedKeys = stmtInsert.getGeneratedKeys();
                if (generatedKeys.next()) {
                    idBairro = generatedKeys.getInt(1); 
                }
            }
        }
    } catch (SQLException e) {
        System.out.println("Erro ao buscar ou cadastrar bairro: " + e.getMessage());
    }

    return idBairro;
}

}