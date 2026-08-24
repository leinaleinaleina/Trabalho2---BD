package DAO;

import classes.genericos.*;
import classes.casodeuso.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ClienteDAO {
    private final String url = "jdbc:mysql://localhost:3306/mydb";
    
    private final String usuario = "root";
    private final String senha = "root";

    public int cadastrarCliente(Paciente paciente) {
    int idpaciente = -1;

    String sqlSelect = "SELECT idPaciente FROM Paciente WHERE Documento = ?";

    try (Connection con = DriverManager.getConnection(url, usuario, senha);
            PreparedStatement stmt = con.prepareStatement(sqlSelect)) {
            stmt.setString(1, paciente.getdocumento_Paciente());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                idpaciente= rs.getInt("idPaciente");

            } else {
                String sqlInsert = "INSERT INTO Cliente (Nome_Paciente, DataNascimento, Documento, EstadoCivil_idEstadoCivil, Sexo_idSexo, Endereco_idEndereco, Numero, Complemento) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement stmtInsert = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
                        stmtInsert.setString(1, paciente.getnome_Paciente());
                        stmtInsert.setString(2, paciente.getData_nascimento());
                        stmtInsert.setString(3, paciente.getdocumento_Paciente());
                        stmtInsert.setInt(4, paciente.getSexo().getIdSexo());
                        stmtInsert.setInt(5, paciente.getEstadocivil().getIdEstadoCivil());
                        stmtInsert.setInt(6, paciente.getEndereco().getidEndereco()); 
                        stmtInsert.setString(7, paciente.getNumero());
                        stmtInsert.setString(8, paciente.getcomp_Paciente());
                        
                        stmtInsert.executeUpdate();

                        
                    ResultSet generatedKeys = stmtInsert.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        idpaciente = generatedKeys.getInt(1); 
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar ou cadastrar Cliente: " + e.getMessage());
        }

        return idpaciente;
    }


//  

public void atualizarEnderecoCliente(int idPaciente, int idEndereco) {
    String sql = "UPDATE Cliente SET Endereco_idEndereco = ? WHERE idCliente = ?";
    
    try (Connection con = DriverManager.getConnection(url, usuario, senha);
         PreparedStatement stmt = con.prepareStatement(sql)) {

        stmt.setInt(1, idEndereco);
        stmt.setInt(2, idPaciente);

        int linhasAfetadas = stmt.executeUpdate();
        if (linhasAfetadas > 0) {
            System.out.println("Endereco ja existe no sistema: " + idEndereco);
        } 
    } catch (SQLException e) {
        System.out.println("Erro ao atualizar endereco do cliente: " + e.getMessage());
    }
}


public Paciente buscarClientePorID (int ID) {
    Paciente paciente = null;
    
    String sql = "SELECT " +
                 "c.idCliente, c.Nome_cliente, c.CPF, c.Numero, c.Complemento, " +
                 "e.idEndereco, e.CEP, " +
                 "b.Bairro, " +
                 "l.Logradouro, " +
                 "tl.Tipo_logradouro, " +
                 "ci.Cidade, " +
                 "u.NomeUF, u.SiglaUF " +
                 "FROM Cliente c " +
                 "JOIN Endereco e ON c.Endereco_idEndereco = e.idEndereco " +
                 "JOIN Bairro b ON e.Bairro_idBairro = b.idBairro " +
                 "JOIN Logradouro l ON e.Logradouro_idLogradouro = l.idLogradouro " +
                 "JOIN Tipo_logradouro tl ON l.Tipo_Logradouro_idTipo_Logradouro = tl.idTipo_Logradouro " +
                 "JOIN Cidade ci ON e.Cidade_idCidade = ci.idCidade " +
                 "JOIN UF u ON ci.UF_idUF = u.idUF " +
                 "WHERE c.idCliente = ?";

    try (Connection con = DriverManager.getConnection(url, usuario, senha);
         PreparedStatement stmt = con.prepareStatement(sql)) {

        stmt.setInt(1, ID);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {


            cliente = new Cliente();
            Endereco endereco = new Endereco();
            Bairro bairro = new Bairro();
            Cidade cidade = new Cidade();
            UF uf = new UF();
            Logra logra = new Logra();
            TipoLogra tipoLogra = new TipoLogra();

            cliente.setidCliente(rs.getInt("idCliente"));
            cliente.setnome_cliente(rs.getString("Nome_cliente"));
            cliente.setCPF_cliente(rs.getString("CPF"));
            cliente.setnumero_cliente(rs.getInt("Numero"));
            cliente.setcomp_cliente(rs.getString("Complemento"));

            endereco.setidEndereco(rs.getInt("idEndereco"));
            endereco.setCEP(rs.getString("CEP"));
            bairro.setBairro(rs.getString("Bairro"));
            endereco.setBairro(bairro);
            cidade.setCidade(rs.getString("Cidade"));
            uf.setUF(rs.getString("NomeUF"));
            uf.setSiglaUF(rs.getString("SiglaUF")); // Adicionando a sigla
            cidade.setUF(uf);
            endereco.setCidade(cidade);
            tipoLogra.setTipoLogra(rs.getString("Tipo_logradouro"));
            logra.setLogra(rs.getString("Logradouro"));
            logra.setTipologra(tipoLogra);
            endereco.setLogra(logra);
            cliente.setEndereco(endereco);

        } else {
            System.out.println("DEBUG: rs.next() retornou false. Nenhum resultado na consulta.");
        }

    } catch (SQLException e) {
        System.out.println("ERRO GRAVE ao buscar cliente por ID: " + e.getMessage());
    }

    return cliente;
}

public Cliente buscarClientePorCPF (String CPF) {
    Cliente cliente = null;
    String sql = "SELECT " +
                 "c.idCliente, c.Nome_cliente, c.CPF, c.Numero, c.Complemento, " +
                 "e.idEndereco, e.CEP, " +
                 "b.Bairro, " +
                 "l.Logradouro, " +
                 "tl.Tipo_logradouro, " +
                 "ci.Cidade, " +
                 "u.NomeUF, u.SiglaUF " +
                 "FROM Cliente c " +
                 "JOIN Endereco e ON c.Endereco_idEndereco = e.idEndereco " +
                 "JOIN Bairro b ON e.Bairro_idBairro = b.idBairro " +
                 "JOIN Logradouro l ON e.Logradouro_idLogradouro = l.idLogradouro " +
                 "JOIN Tipo_logradouro tl ON l.Tipo_Logradouro_idTipo_Logradouro = tl.idTipo_Logradouro " +
                 "JOIN Cidade ci ON e.Cidade_idCidade = ci.idCidade " +
                 "JOIN UF u ON ci.UF_idUF = u.idUF " +
                 "WHERE c.CPF = ?";

    try (Connection con = DriverManager.getConnection(url, usuario, senha);
         PreparedStatement stmt = con.prepareStatement(sql)) {

        stmt.setString(1, CPF);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            cliente = new Cliente();
            Endereco endereco = new Endereco();
            Bairro bairro = new Bairro();
            Cidade cidade = new Cidade();
            UF uf = new UF();
            Logra logra = new Logra();
            TipoLogra tipoLogra = new TipoLogra();

            cliente.setidCliente(rs.getInt("idCliente"));
            cliente.setnome_cliente(rs.getString("Nome_cliente"));
            cliente.setCPF_cliente(rs.getString("CPF"));
            cliente.setnumero_cliente(rs.getInt("Numero"));
            cliente.setcomp_cliente(rs.getString("Complemento"));

            endereco.setidEndereco(rs.getInt("idEndereco"));
            endereco.setCEP(rs.getString("CEP"));
            bairro.setBairro(rs.getString("Bairro"));
            endereco.setBairro(bairro);
            cidade.setCidade(rs.getString("Cidade"));
            uf.setUF(rs.getString("NomeUF"));
            uf.setSiglaUF(rs.getString("SiglaUF")); // Adicionando a sigla
            cidade.setUF(uf);
            endereco.setCidade(cidade);
            tipoLogra.setTipoLogra(rs.getString("Tipo_logradouro"));
            logra.setLogra(rs.getString("Logradouro"));
            logra.setTipologra(tipoLogra);
            endereco.setLogra(logra);
            cliente.setEndereco(endereco);
        }
    } catch (SQLException e) {
        System.out.println("Erro ao buscar cliente por CPF: " + e.getMessage());
    }
    return cliente;
}

}