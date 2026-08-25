package DAO;

import classes.genericos.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PacienteDAO {
    private final String url = "jdbc:mysql://localhost:3306/mydb";
    
    private final String usuario = "root";
    private final String senha = "root";


//  

public void atualizarEnderecoCliente(int idPaciente, int idEndereco) {
    String sql = "UPDATE Paciente SET Endereco_idEndereco = ? WHERE idPaciente = ?";
    
    try (Connection con = DriverManager.getConnection(url, usuario, senha);
         PreparedStatement stmt = con.prepareStatement(sql)) {

        stmt.setInt(1, idEndereco);
        stmt.setInt(2, idPaciente);

        int linhasAfetadas = stmt.executeUpdate();
        if (linhasAfetadas > 0) {
            System.out.println("Endereco ja existe no sistema: " + idEndereco);
        } 
    } catch (SQLException e) {
        System.out.println("Erro ao atualizar endereco do paciente: " + e.getMessage());
    }
}


public Paciente buscarPacientePorID (int ID) {
    Paciente paciente = null;
    
    String sql = "SELECT " +
                 "c.idPaciente, c.Nome_Paciente, c.DataNascimento, c.Documento, c.Numero, c.Complemento, " +
                 "s.idSexo, s.Sexo, " + 
                 "ec.idEstadoCivil, ec.EstadoCivil, " +
                 "e.idEndereço, e.CEP, " +
                 "b.Bairro, " +
                 "l.Logradouro, " +
                 "tl.TipoLogradouro, " +
                 "ci.Cidade, " +
                 "u.UF, u.SiglaUF " +
                 "FROM Paciente c " +
                 "JOIN Sexo s ON c.Sexo_idSexo = s.idSexo " +
                 "JOIN EstadoCivil ec ON c.EstadoCivil_idEstadoCivil = ec.idEstadoCivil " +
                 "JOIN Endereço e ON c.Endereço_idEndereço = e.idEndereço " +
                 "JOIN Bairro b ON e.Bairro_idBairro = b.idBairro " +
                 "JOIN Logradouro l ON e.Logradouro_idLogradouro = l.idLogradouro " +
                 "JOIN TipoLogradouro tl ON l.TipoLogradouro_idTipoLogradouro = tl.idTipoLogradouro " +
                 "JOIN Cidade ci ON e.Cidade_idCidade = ci.idCidade " +
                 "JOIN UF u ON ci.UF_idUF = u.idUF " +
                 "WHERE c.idPaciente = ?";

    try (Connection con = DriverManager.getConnection(url, usuario, senha);
         PreparedStatement stmt = con.prepareStatement(sql)) {

        stmt.setInt(1, ID);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            paciente = new Paciente();
            Sexo sexo = new Sexo();
            EstadoCivil estadoCivil = new EstadoCivil();
            Endereco endereco = new Endereco();
            Bairro bairro = new Bairro();
            Cidade cidade = new Cidade();
            UF uf = new UF();
            Logra logra = new Logra();
            TipoLogra tipoLogra = new TipoLogra();

            paciente.setidPaciente(rs.getInt("idPaciente"));
            paciente.setnome_Paciente(rs.getString("Nome_Paciente"));
            paciente.setdocumento_Paciente (rs.getString("Documento"));
            paciente.setData_nascimento(rs.getString("DataNascimento"));
            paciente.setNumero(rs.getString("Numero"));
            paciente.setcomp_Paciente(rs.getString("Complemento"));
            sexo.setSexo(rs.getString ("Sexo"));
            estadoCivil.setEstadoCivil(rs.getString ("EstadoCivil"));
            paciente.setSexo(sexo);
            paciente.setEstadocivil(estadoCivil);

            endereco.setidEndereco(rs.getInt("idEndereço")); // Puxando com 'ç'
            endereco.setCEP(rs.getString("CEP"));
            bairro.setBairro(rs.getString("Bairro"));
            endereco.setBairro(bairro);
            cidade.setCidade(rs.getString("Cidade"));
            uf.setUF(rs.getString("UF"));
            uf.setSiglaUF(rs.getString("SiglaUF")); 
            cidade.setUF(uf);
            endereco.setCidade(cidade);
            tipoLogra.setTipoLogra(rs.getString("TipoLogradouro"));
            logra.setLogra(rs.getString("Logradouro"));
            logra.setTipologra(tipoLogra);
            endereco.setLogra(logra);
            paciente.setEndereco(endereco);

        } else {
            System.out.println("DEBUG: Nenhum paciente encontrado com este ID.");
        }

    } catch (SQLException e) {
        System.out.println("ERRO ao buscar paciente por ID: " + e.getMessage());
    }

    return paciente;
}

public Paciente buscarClientePorDoc (String Documento) {
    Paciente paciente = null;
    
    String sql = "SELECT " +
                 "c.idPaciente, c.Nome_Paciente, c.DataNascimento, c.Documento, c.Numero, c.Complemento, " +
                 "s.idSexo, s.Sexo, " +
                 "ec.idEstadoCivil, ec.EstadoCivil, " +
                 "e.idEndereço, e.CEP, " +
                 "b.Bairro, " +
                 "l.Logradouro, " +
                 "tl.TipoLogradouro, " +
                 "ci.Cidade, " +
                 "u.UF, u.SiglaUF " +
                 "FROM Paciente c " +
                 "JOIN Sexo s ON c.Sexo_idSexo = s.idSexo " +
                 "JOIN EstadoCivil ec ON c.EstadoCivil_idEstadoCivil = ec.idEstadoCivil " +
                 "JOIN Endereço e ON c.Endereço_idEndereço = e.idEndereço " +
                 "JOIN Bairro b ON e.Bairro_idBairro = b.idBairro " +
                 "JOIN Logradouro l ON e.Logradouro_idLogradouro = l.idLogradouro " +
                 "JOIN TipoLogradouro tl ON l.TipoLogradouro_idTipoLogradouro = tl.idTipoLogradouro " +
                 "JOIN Cidade ci ON e.Cidade_idCidade = ci.idCidade " +
                 "JOIN UF u ON ci.UF_idUF = u.idUF " +
                 "WHERE c.Documento = ?";

    try (Connection con = DriverManager.getConnection(url, usuario, senha);
         PreparedStatement stmt = con.prepareStatement(sql)) {

        stmt.setString(1, Documento);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            paciente = new Paciente();
            Sexo sexo = new Sexo();
            EstadoCivil estadoCivil = new EstadoCivil();
            Endereco endereco = new Endereco();
            Bairro bairro = new Bairro();
            Cidade cidade = new Cidade();
            UF uf = new UF();
            Logra logra = new Logra();
            TipoLogra tipoLogra = new TipoLogra();

            paciente.setidPaciente(rs.getInt("idPaciente"));
            paciente.setnome_Paciente(rs.getString("Nome_Paciente"));
            paciente.setdocumento_Paciente (rs.getString("Documento"));
            paciente.setData_nascimento(rs.getString("DataNascimento"));
            paciente.setNumero(rs.getString("Numero"));
            paciente.setcomp_Paciente(rs.getString("Complemento"));
            sexo.setSexo(rs.getString ("Sexo"));
            estadoCivil.setEstadoCivil(rs.getString ("EstadoCivil"));
            paciente.setSexo(sexo);
            paciente.setEstadocivil(estadoCivil);

            endereco.setidEndereco(rs.getInt("idEndereço")); // Puxando com 'ç'
            endereco.setCEP(rs.getString("CEP"));
            bairro.setBairro(rs.getString("Bairro"));
            endereco.setBairro(bairro);
            cidade.setCidade(rs.getString("Cidade"));
            uf.setUF(rs.getString("UF"));
            uf.setSiglaUF(rs.getString("SiglaUF"));
            cidade.setUF(uf);
            endereco.setCidade(cidade);
            tipoLogra.setTipoLogra(rs.getString("TipoLogradouro"));
            logra.setLogra(rs.getString("Logradouro"));
            logra.setTipologra(tipoLogra);
            endereco.setLogra(logra);
            paciente.setEndereco(endereco);
        }
    } catch (SQLException e) {
        System.out.println("Erro ao buscar paciente por Documento: " + e.getMessage());
    }
    return paciente;
}

}