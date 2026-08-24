package DAO;

import classes.Agencia;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AgenciaDAO {
    private final String url = "jdbc:mysql://localhost:3306/mydb";

    private final String usuario = "root";
    private final String senha = "root";

    public Agencia buscarAgenciaPorNumeroEBancoID(int numeroAgencia, int idBanco) {
    String sql = "SELECT * FROM Agencia_banco WHERE Numero_agencia_banco = ? AND Banco_Codigo_banco = ?";
    Agencia agencia = null;
    
    try (Connection con = DriverManager.getConnection(url, usuario, senha);
         PreparedStatement stmt = con.prepareStatement(sql)) {

        stmt.setInt(1, numeroAgencia);
        stmt.setInt(2, idBanco);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            agencia = new Agencia();
            agencia.setCodAgencia(rs.getInt("Numero_agencia_banco"));
            // Você pode popular outros dados da agência aqui se necessário
        }
    } catch (SQLException e) {
        System.out.println("Erro ao buscar agência: " + e.getMessage());
    }
    return agencia;
}
}
