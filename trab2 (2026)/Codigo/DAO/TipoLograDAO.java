package DAO;

import classes.TipoLogra;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TipoLograDAO {
    private final String url = "jdbc:mysql://localhost:3306/mydb";
    
    private final String usuario = "root";
    private final String senha = "root";

    public int cadastrarTipoLogra (TipoLogra tipo) {
        int idTipoLogra = -1;

    String sqlSelect = "SELECT idTipo_Logradouro FROM Tipo_logradouro WHERE Tipo_logradouro = ?";

    try (Connection con = DriverManager.getConnection(url, usuario, senha);
         PreparedStatement stmt = con.prepareStatement(sqlSelect)) {

        stmt.setString(1, tipo.getTipoLogra());

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            idTipoLogra = rs.getInt("idTipo_Logradouro");
        } else {
            // Se não existir, insere o bairro novo
            String sqlInsert = "INSERT INTO Tipo_logradouro (Tipo_logradouro) VALUES (?)";
            try (PreparedStatement stmtInsert = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
                stmtInsert.setString(1, tipo.getTipoLogra());

                stmtInsert.executeUpdate();
                ResultSet generatedKeys = stmtInsert.getGeneratedKeys();
                if (generatedKeys.next()) {
                    idTipoLogra = generatedKeys.getInt(1); 
                }
            }
        }
    } catch (SQLException e) {
        System.out.println("Erro ao buscar ou cadastrar bairro: " + e.getMessage());
    }

    return idTipoLogra;
}

}