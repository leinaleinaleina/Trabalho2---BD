package DAO;

import classes.TelefoneAgencia;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TelefoneAgenciaDAO {
    private final String url = "jdbc:mysql://localhost:3306/mydb";
    
    private final String usuario = "root";
    private final String senha = "root";

    public void cadastrarTelefone (TelefoneAgencia fone) {
        //INSERT, ignorando o ID porque a tabela usa AUTO_INCREMENT
        String sql = "INSERT INTO Telefone (dddi, ddd ,telefone) VALUES (?, ?, ?)";
        //tenta conexão com o banco 
        try (Connection con = DriverManager.getConnection(url,usuario,senha);
        PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt (1, fone.getDDDI().getDDDI());
            stmt.setInt (2, fone.getDDD().getDDD());
            stmt.setString (3, fone.getTelefone());

            //executa no banco
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println ("Erro ao salvar telefone: " + e.getMessage());
        }

    }

}
