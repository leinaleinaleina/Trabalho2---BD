// Crie este novo arquivo: src/DAO/Tipo_transacaoDAO.java
package DAO;

import classes.Tipo_transacao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Tipo_transacaoDAO {

    
    public String getTipoRenda(int idTipo, Connection con) throws SQLException {
        String sql = "SELECT Tipo_renda FROM Tipo_transacao WHERE idTipo_transacao = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, idTipo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("Tipo_renda");
            }
        }
        return null; // Retorna null se não encontrar
    }

    public Tipo_transacao buscarPorID(int idTipo, Connection con) throws SQLException {
    String sql = "SELECT * FROM Tipo_transacao WHERE idTipo_transacao = ?";
    Tipo_transacao tipo = null;
    try (PreparedStatement stmt = con.prepareStatement(sql)) {
        stmt.setInt(1, idTipo);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            tipo = new Tipo_transacao();
            tipo.setIdTipo(rs.getInt("idTipo_transacao"));
            tipo.setNomeTipo(rs.getString("Tipo_transacao"));
            tipo.setRenda(rs.getString("Tipo_renda"));
        }
    }
    return tipo;
}
}