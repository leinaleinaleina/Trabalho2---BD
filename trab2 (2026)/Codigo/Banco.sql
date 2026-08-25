-- Criação do Banco de Dados
CREATE DATABASE IF NOT EXISTS mydb;
USE mydb;


CREATE TABLE UF (
    idUF INT AUTO_INCREMENT PRIMARY KEY,
    UF VARCHAR(45),
    SiglaUF VARCHAR(4)
);

CREATE TABLE Bairro (
    idBairro INT  AUTO_INCREMENT PRIMARY KEY,
    Bairro VARCHAR(45)
);

CREATE TABLE TipoLogradouro (
    idTipoLogradouro INT AUTO_INCREMENT KEY,
    TipoLogradouro VARCHAR(45)
);

CREATE TABLE EstadoCivil (
    idEstadoCivil INT AUTO_INCREMENTPRIMARY KEY,
    EstadoCivil VARCHAR(45)
);

CREATE TABLE Sexo (
    idSexo INT AUTO_INCREMENT PRIMARY KEY,
    Sexo VARCHAR(45)
);

CREATE TABLE DDI (
    idDDI INT AUTO_INCREMENT PRIMARY KEY,
    DDI VARCHAR(45)
);

CREATE TABLE DDD (
    idDDD INT AUTO_INCREM ENTPRIMARY KEY,
    DDD VARCHAR(45)
);

CREATE TABLE Medico (
    CRM VARCHAR(45) AUTO_INCREMENT PRIMARY KEY,
    Nome_Medico VARCHAR(45),
    Area VARCHAR(45)
);

CREATE TABLE Diagnostico (
    CID VARCHAR(24) AUTO_INCREMENT PRIMARY KEY,
    NomeCID VARCHAR(45),
    DescricaoCID VARCHAR(45)
);

CREATE TABLE TipoExame (
    idTipoExame INT AUTO_INCREMENT PRIMARY KEY,
    TipoExame VARCHAR(45),
    Descricao VARCHAR(150)
);

CREATE TABLE Resultado (
    idResultado INT AUTO_INCREMENT PRIMARY KEY,
    Resultado VARCHAR(45)
);


-- Tabelas de dependencia 1

CREATE TABLE Cidade (
    idCidade INT AUTO_INCREMENT PRIMARY KEY,
    Cidade VARCHAR(45),
    UF_idUF INT,
    FOREIGN KEY (UF_idUF) REFERENCES UF(idUF)
);

CREATE TABLE Logradouro (
    idLogradouro INT AUTO_INCREMENT PRIMARY KEY,
    Logradouro VARCHAR(45),
    TipoLogradouro_idTipoLogradouro INT,
    FOREIGN KEY (TipoLogradouro_idTipoLogradouro) REFERENCES TipoLogradouro(idTipoLogradouro)
);

CREATE TABLE EmailMedico (
    idEmailM INT AUTO_INCREMENT PRIMARY KEY,
    EmailMedico VARCHAR(45),
    Medico_CRM VARCHAR(45),
    FOREIGN KEY (Medico_CRM) REFERENCES Medico(CRM)
);


-- Tabelas de dependencia 2

CREATE TABLE Endereço (
    idEndereço INT AUTO_INCREMENT PRIMARY KEY,
    CEP VARCHAR(45),
    Bairro_idBairro INT,
    Cidade_idCidade INT,
    Cidade_UF_idUF INT,
    Logradouro_idLogradouro INT,
    Logradouro_TipoLogradouro_idTipoLogradouro INT,
    FOREIGN KEY (Bairro_idBairro) REFERENCES Bairro(idBairro),
    FOREIGN KEY (Cidade_idCidade) REFERENCES Cidade(idCidade),
    FOREIGN KEY (Logradouro_idLogradouro) REFERENCES Logradouro(idLogradouro)
);


-- Tabelas centrais 

CREATE TABLE Paciente (
    idPaciente INT AUTO_INCREMENT PRIMARY KEY,
    Nome_Paciente VARCHAR(45),
    DataNascimento VARCHAR(45),
    Documento VARCHAR(45),
    Numero VARCHAR (45),
    Complemento VARCHAR (45),
    EstadoCivil_idEstadoCivil INT,
    Sexo_idSexo INT,
    Endereço_idEndereço INT,
    Endereço_Bairro_idBairro INT,
    Endereço_Cidade_idCidade INT,
    Endereço_Cidade_UF_idUF INT,
    Endereço_Logradouro_idLogradouro INT,
    Endereço_Logradouro_TipoLogradouro_idTipoLogradouro INT,
    FOREIGN KEY (EstadoCivil_idEstadoCivil) REFERENCES EstadoCivil(idEstadoCivil),
    FOREIGN KEY (Sexo_idSexo) REFERENCES Sexo(idSexo),
    FOREIGN KEY (Endereço_idEndereço) REFERENCES Endereço(idEndereço)
);

CREATE TABLE Fone (
    idFone INT  AUTO_INCREMENT PRIMARY KEY,
    Fone VARCHAR(45),
    DDD_idDDD INT,
    Paciente_idPaciente INT,
    DDI_idDDI INT,
    FOREIGN KEY (DDD_idDDD) REFERENCES DDD(idDDD),
    FOREIGN KEY (Paciente_idPaciente) REFERENCES Paciente(idPaciente),
    FOREIGN KEY (DDI_idDDI) REFERENCES DDI(idDDI)
);

CREATE TABLE EmailPaciente (
    idEmailP INT AUTO_INCREMENT PRIMARY KEY,
    EmailPaciente VARCHAR(45),
    Paciente_idPaciente INT,
    FOREIGN KEY (Paciente_idPaciente) REFERENCES Paciente(idPaciente)
);

CREATE TABLE Consulta (
    NroConsulta INT PRIMARY KEY,
    DataConsulta VARCHAR(45),
    Diagnostico_CID VARCHAR(24),
    Medico_CRM VARCHAR(45),
    Paciente_idPaciente INT,
    Paciente_EstadoCivil_idEstadoCivil INT,
    Paciente_Sexo_idSexo INT,
    Paciente_Exame_NroConsulta INT,
    FOREIGN KEY (Diagnostico_CID) REFERENCES Diagnostico(CID),
    FOREIGN KEY (Medico_CRM) REFERENCES Medico(CRM),
    FOREIGN KEY (Paciente_idPaciente) REFERENCES Paciente(idPaciente)
);

CREATE TABLE Exame (
    NroExame INT PRIMARY KEY,
    DataExame VARCHAR(45), 
    Observacao VARCHAR(45),
    TipoExame_idTipoExame INT,
    Resultado_idResultado INT,
    Paciente_idPaciente INT,
    Paciente_EstadoCivil_idEstadoCivil INT,
    Paciente_Sexo_idSexo INT,
    Paciente_Exame_NroConsulta INT,
    FOREIGN KEY (TipoExame_idTipoExame) REFERENCES TipoExame(idTipoExame),
    FOREIGN KEY (Resultado_idResultado) REFERENCES Resultado(idResultado),
    FOREIGN KEY (Paciente_idPaciente) REFERENCES Paciente(idPaciente)
);