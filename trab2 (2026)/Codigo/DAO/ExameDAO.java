package DAO;

import classes.casodeuso.*;
import classes.genericos.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ExameDAO {
    private final String url = "jdbc:mysql://localhost:3306/mydb";

    private final String usuario = "root";
    private final String senha = "root";

   public int cadastrarExame(Exame exame) {
    int idExameGerado = -1; 

    String sqlInsert = "INSERT INTO Exame (DataExame, Observacao, TipoExame_idTipoExame, " +
                       "Resultado_idResultado, Paciente_idPaciente, " +
                       "Paciente_EstadoCivil_idEstadoCivil, Paciente_Sexo_idSexo) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?)";

    try (Connection con = DriverManager.getConnection(url, usuario, senha);
         PreparedStatement stmtInsert = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
        
        stmtInsert.setString(1, exame.getData_exame());
        stmtInsert.setString(2, exame.getObservacao());
        

        stmtInsert.setInt(3, exame.getTipo_exame().getIdTipoExame()); 
        stmtInsert.setInt(4, exame.getResultado().getIdResultado()); 
        
        stmtInsert.setInt(5, exame.getPaciente().getidPaciente());
        stmtInsert.setInt(6, exame.getPaciente().getEstadocivil().getIdEstadoCivil());
        stmtInsert.setInt(7, exame.getPaciente().getSexo().getIdSexo());
        
        stmtInsert.executeUpdate();
        
        ResultSet generatedKeys = stmtInsert.getGeneratedKeys();
        if (generatedKeys.next()) {
            idExameGerado = generatedKeys.getInt(1);
        }
        
    } catch (SQLException e) {
        System.out.println("Erro ao cadastrar exame: " + e.getMessage());
    }
    
    return idExameGerado;
}
    
    
    public Exame buscarExamePorID(int nroExame) {
    
    String sql = "SELECT * FROM Exame WHERE NroExame = ?";
    Exame exameEncontrado = null; 

    try (Connection con = DriverManager.getConnection(url, usuario, senha);
         PreparedStatement stmt = con.prepareStatement(sql)) {

        stmt.setInt(1, nroExame);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            exameEncontrado = new Exame();
            

            exameEncontrado.setNro_exame(rs.getInt("NroExame"));
            exameEncontrado.setData_exame(rs.getString("DataExame")); 
            exameEncontrado.setObservacao(rs.getString("Observacao"));
            
            TipoExame tipo = new TipoExame();
            tipo.setIdTipoExame(rs.getInt("TipoExame_idTipoExame"));
            exameEncontrado.setTipo_exame(tipo);
            
            Resultado resultado = new Resultado();
            resultado.setIdResultado(rs.getInt("Resultado_idResultado"));
            exameEncontrado.setResultado(resultado);
            
            Paciente paciente = new Paciente();
            paciente.setidPaciente(rs.getInt("Paciente_idPaciente"));
            exameEncontrado.setPaciente(paciente);
            
        }
    } catch (SQLException e) {
        System.out.println("Erro ao buscar exame: " + e.getMessage());
    }
    
    return exameEncontrado;
}


public List<Exame> buscarExamesPorPacienteID(int idPaciente) {
        List<Exame> examesEncontrados = new ArrayList<>();
        
        String sql = "SELECT e.NroExame, e.DataExame, e.Observacao, " +
                     "t.idTipoExame, t.TipoExame AS NomeTipo, " +
                     "r.idResultado, r.Resultado AS NomeResultado " +
                     "FROM Exame e " +
                     "JOIN TipoExame t ON e.TipoExame_idTipoExame = t.idTipoExame " +
                     "JOIN Resultado r ON e.Resultado_idResultado = r.idResultado " +
                     "WHERE e.Paciente_idPaciente = ?";

        try (Connection con = DriverManager.getConnection(url, usuario, senha);
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, idPaciente);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Exame exame = new Exame();
                
                exame.setNro_exame(rs.getInt("NroExame"));
                exame.setData_exame(rs.getString("DataExame")); 
                exame.setObservacao(rs.getString("Observacao"));

                TipoExame tipo = new TipoExame();
                tipo.setIdTipoExame(rs.getInt("idTipoExame"));
                tipo.setTipoExame(rs.getString("NomeTipo")); 
                exame.setTipo_exame(tipo);

                Resultado resultado = new Resultado();
                resultado.setIdResultado(rs.getInt("idResultado"));
                resultado.setResultado(rs.getString("NomeResultado"));
                exame.setResultado(resultado);

                examesEncontrados.add(exame);
            }
            
        } catch (SQLException e) {
            System.out.println("Erro ao buscar exames do paciente: " + e.getMessage());
        }

        return examesEncontrados;
    }
}
