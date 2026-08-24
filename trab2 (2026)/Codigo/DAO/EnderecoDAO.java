package DAO;

import classes.genericos.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EnderecoDAO {
    private final String url = "jdbc:mysql://localhost:3306/mydb";
    
    private final String usuario = "root";
    private final String senha = "root";

    public int cadastrarEndereco (Endereco endereco) {

    int idEndereco = -1;

    String sqlSelect = "SELECT idEndereco FROM Endereco WHERE CEP = ? AND Bairro_idBairro = ? AND Cidade_idCidade = ? AND Cidade_UF_idUF = ? AND Logradouro_idLogradouro = ?";

    try (Connection con = DriverManager.getConnection(url, usuario, senha);
         PreparedStatement stmtSelect = con.prepareStatement(sqlSelect)) {

        stmtSelect.setString(1, endereco.getCEP());
        stmtSelect.setInt(2, endereco.getBairro().getidBairro());
        stmtSelect.setInt(3, endereco.getCidade().getidCidade());
        stmtSelect.setInt(4, endereco.getCidade().getUF().getidUF());
        stmtSelect.setInt(5, endereco.getLogra().getidLogra());

        ResultSet rs = stmtSelect.executeQuery();
        if (rs.next()) {
            idEndereco = rs.getInt("idEndereco");
            System.out.println("Endereço já existe. Reutilizando id: " + idEndereco);
            return idEndereco;
        }

    } catch (SQLException e) {
        System.out.println("Erro ao buscar endereço: " + e.getMessage());
    }

       String sql = "INSERT INTO Endereco (CEP, Bairro_idBairro, Cidade_idCidade, Cidade_UF_idUF, Logradouro_idLogradouro) VALUES (?, ?, ?, ?, ?)";

        //tenta conexão com o banco 
        try (Connection con = DriverManager.getConnection(url,usuario,senha);
        PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))  {

            stmt.setString(1, endereco.getCEP());
            stmt.setInt(2, endereco.getBairro().getidBairro());
            stmt.setInt(3, endereco.getCidade().getidCidade());
            stmt.setInt(4, endereco.getCidade().getUF().getidUF());
            stmt.setInt(5, endereco.getLogra().getidLogra());

            stmt.executeUpdate();

            ResultSet rsInsert = stmt.getGeneratedKeys();
            if (rsInsert.next()) {
                idEndereco = rsInsert.getInt(1);
            }

        } catch (SQLException e) {
        System.out.println("Erro ao inserir endereço: " + e.getMessage());
    }

    return idEndereco;

    }
}    