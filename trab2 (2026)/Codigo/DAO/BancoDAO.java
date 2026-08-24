package DAO;

import classes.Banco;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BancoDAO {
    private final String url = "jdbc:mysql://localhost:3306/mydb";
    
    private final String usuario = "root";
    private final String senha = "root";

    public Banco buscarBancoPorNome(String nomeBanco) {
    String sql = "SELECT * FROM Banco WHERE Nome_banco = ?";
    Banco banco = null;

    try (Connection con = DriverManager.getConnection(url, usuario, senha);
         PreparedStatement stmt = con.prepareStatement(sql)) {
        
        stmt.setString(1, nomeBanco);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            banco = new Banco();
            banco.setcodBanco(rs.getInt("Codigo_banco"));
            banco.setnomeBanco(rs.getString("Nome_banco"));
            banco.setCNPJ(rs.getString("CNPJ_banco"));
            banco.setTipobanco(rs.getString("Tipo_banco"));
        }
    } catch (SQLException e) {
        System.out.println("Erro ao buscar banco por nome: " + e.getMessage());
    }
    return banco;
}

}