-- 1. TABELAS DE DOMÍNIO / INDEPENDENTES

INSERT INTO UF (UF, SiglaUF) VALUES 
('Paraná', 'PR'),
('São Paulo', 'SP'),
('Santa Catarina', 'SC');

INSERT INTO Bairro (Bairro) VALUES 
('Centro'),
('Vila A'),
('Morumbi');

INSERT INTO TipoLogradouro (TipoLogradouro) VALUES 
('Rua'),
('Avenida'),
('Travessa');

INSERT INTO EstadoCivil (EstadoCivil) VALUES 
('Solteiro(a)'),
('Casado(a)'),
('Divorciado(a)');

INSERT INTO Sexo (Sexo) VALUES 
('Feminino'),
('Masculino');

INSERT INTO DDI (DDI) VALUES 
('55');

INSERT INTO DDD (DDD) VALUES 
('45'),
('11'),
('48');

INSERT INTO Medico (CRM, Nome_Medico, Area) VALUES 
('CRM-PR 12345', 'Dra. Fabiana Peres', 'Cardiologia'),
('CRM-SP 67890', 'Dr. Carlos Silva', 'Ortopedia'),
('CRM-SC 11223', 'Dra. Mariana Souza', 'Dermatologia');

INSERT INTO Diagnostico (CID, NomeCID, DescricaoCID) VALUES 
('I10', 'Hipertensão Essencial', 'Pressão arterial elevada sistêmica'),
('M54.5', 'Dor Lombar Baixa', 'Lombalgia mecânica postural'),
('L30.9', 'Dermatite Não Especificada', 'Eczema de contato alérgico');

INSERT INTO TipoExame (TipoExame, Descricao) VALUES 
('Hemograma Completo', 'Avaliação das células sanguíneas globais'),
('Raio-X de Tórax', 'Radiografia para análise pulmonar e cardíaca'),
('Glicemia de Jejum', 'Medição dos níveis de açúcar no sangue');

INSERT INTO Resultado (Resultado) VALUES 
('Normal'),
('Alterado'),
('Pendente');


-- 2. TABELAS DE DEPENDÊNCIA 1 (Cidade, Logradouro, EmailMedico)

INSERT INTO Cidade (Cidade, UF_idUF) VALUES 
('Foz do Iguaçu', 1),
('Cascavel', 1),
('São Paulo', 2);

INSERT INTO Logradouro (Logradouro, TipoLogradouro_idTipoLogradouro) VALUES 
('Brasil', 2),
('Juscelino Kubitschek', 2),
('Paraná', 1);

INSERT INTO EmailMedico (EmailMedico, Medico_CRM) VALUES 
('fabiana.peres@clinica.com', 'CRM-PR 12345'),
('carlos.silva@clinica.com', 'CRM-SP 67890'),
('mariana.souza@clinica.com', 'CRM-SC 11223');


-- 3. TABELAS DE DEPENDÊNCIA 2 (Endereço)

INSERT INTO Endereço (CEP, Bairro_idBairro, Cidade_idCidade, Cidade_UF_idUF, Logradouro_idLogradouro, Logradouro_TipoLogradouro_idTipoLogradouro) VALUES 
('85851-000', 1, 1, 1, 1, 2),
('85856-000', 2, 1, 1, 2, 2),
('85852-100', 3, 1, 1, 3, 1),
('85800-111', 1, 2, 1, 1, 2),
('01000-000', 1, 3, 2, 2, 2);


-- 4. PACIENTES (5 Pacientes)

INSERT INTO Paciente (Nome_Paciente, DataNascimento, Documento, Numero, Complemento, EstadoCivil_idEstadoCivil, Sexo_idSexo, Endereço_idEndereço, Endereço_Bairro_idBairro, Endereço_Cidade_idCidade, Endereço_Cidade_UF_idUF, Endereço_Logradouro_idLogradouro, Endereço_Logradouro_TipoLogradouro_idTipoLogradouro) VALUES 
('Ana Souza', '12/05/1998', '111.222.333-44', '100', 'Apto 101', 1, 1, 1, 1, 1, 1, 1, 2),
('Bruno Oliveira', '25/08/1990', '222.333.444-55', '205', 'Bloco B', 2, 2, 2, 2, 1, 1, 2, 2),
('Carla Mendes', '03/02/1985', '333.444.555-66', '310', 'Casa', 3, 1, 3, 3, 1, 1, 3, 1),
('Daniel Costa', '19/11/1992', '444.555.666-77', '45', 'Fundos', 1, 2, 4, 1, 2, 1, 1, 2),
('Eduarda Lima', '30/07/2000', '555.666.777-88', '789', 'Apto 304', 1, 1, 5, 1, 3, 2, 2, 2);

-- 5. FONES (3 Telefones para cada Paciente = 15 registros)

INSERT INTO Fone (Fone, DDD_idDDD, Paciente_idPaciente, DDI_idDDI) VALUES 
-- Paciente 1
('99111-1111', 1, 1, 1),
('3522-2222', 1, 1, 1),
('98888-8888', 2, 1, 1),
-- Paciente 2
('99222-2222', 1, 2, 1),
('3533-3333', 1, 2, 1),
('98777-7777', 3, 2, 1),
-- Paciente 3
('99333-3333', 1, 3, 1),
('3544-4444', 1, 3, 1),
('98666-6666', 2, 3, 1),
-- Paciente 4
('99444-4444', 1, 4, 1),
('3555-5555', 1, 4, 1),
('98555-5555', 3, 4, 1),
-- Paciente 5
('99555-5555', 1, 5, 1),
('3566-6666', 1, 5, 1),
('98444-4444', 2, 5, 1);


-- 6. EMAILS DO PACIENTE (3 E-mails para cada Paciente = 15 registros)

INSERT INTO EmailPaciente (EmailPaciente, Paciente_idPaciente) VALUES 
-- Paciente 1
('ana.souza@email.com', 1),
('ana.trabalho@email.com', 1),
('ana.pessoal@email.com', 1),
-- Paciente 2
('bruno.oliveira@email.com', 2),
('bruno.eng@email.com', 2),
('b.oliveira@email.com', 2),
-- Paciente 3
('carla.mendes@email.com', 3),
('carla.contato@email.com', 3),
('carlinha@email.com', 3),
-- Paciente 4
('daniel.costa@email.com', 4),
('daniel.dev@email.com', 4),
('d.costa@email.com', 4),
-- Paciente 5
('eduarda.lima@email.com', 5),
('eduarda.uni@email.com', 5),
('duda.lima@email.com', 5);


-- 7. CONSULTAS (3 Consultas para cada Paciente = 15 registros)

INSERT INTO Consulta (DataConsulta, Diagnostico_CID, Medico_CRM, Paciente_idPaciente, Paciente_EstadoCivil_idEstadoCivil, Paciente_Sexo_idSexo) VALUES 
-- Paciente 1
('10/01/2026', 'I10', 'CRM-PR 12345', 1, 1, 1),
('15/02/2026', 'M54.5', 'CRM-SP 67890', 1, 1, 1),
('20/03/2026', 'L30.9', 'CRM-SC 11223', 1, 1, 1),
-- Paciente 2
('11/01/2026', 'M54.5', 'CRM-SP 67890', 2, 2, 2),
('16/02/2026', 'I10', 'CRM-PR 12345', 2, 2, 2),
('21/03/2026', 'L30.9', 'CRM-SC 11223', 2, 2, 2),
-- Paciente 3
('12/01/2026', 'L30.9', 'CRM-SC 11223', 3, 3, 1),
('17/02/2026', 'I10', 'CRM-PR 12345', 3, 3, 1),
('22/03/2026', 'M54.5', 'CRM-SP 67890', 3, 3, 1),
-- Paciente 4
('13/01/2026', 'I10', 'CRM-PR 12345', 4, 1, 2),
('18/02/2026', 'M54.5', 'CRM-SP 67890', 4, 1, 2),
('23/03/2026', 'L30.9', 'CRM-SC 11223', 4, 1, 2),
-- Paciente 5
('14/01/2026', 'M54.5', 'CRM-SP 67890', 5, 1, 1),
('19/02/2026', 'L30.9', 'CRM-SC 11223', 5, 1, 1),
('24/03/2026', 'I10', 'CRM-PR 12345', 5, 1, 1);


-- 8. EXAMES (3 Exames para cada Paciente = 15 registros)

INSERT INTO Exame (DataExame, Observacao, TipoExame_idTipoExame, Resultado_idResultado, Paciente_idPaciente, Paciente_EstadoCivil_idEstadoCivil, Paciente_Sexo_idSexo) VALUES 
-- Paciente 1
('10/01/2026', 'Jejum de 12 horas realizado', 1, 1, 1, 1, 1),
('15/02/2026', 'Sem alteracoes pulmonares', 2, 1, 1, 1, 1),
('20/03/2026', 'Glicemia limítrofe', 3, 2, 1, 1, 1),
-- Paciente 2
('11/01/2026', 'Colesterol total elevado', 1, 2, 2, 2, 2),
('16/02/2026', 'Leve desvio na coluna', 2, 2, 2, 2, 2),
('21/03/2026', 'Glicemia normal', 3, 1, 2, 2, 2),
-- Paciente 3
('12/01/2026', 'Tudo dentro dos parâmetros', 1, 1, 3, 3, 1),
('17/02/2026', 'Raio-X limpo', 2, 1, 3, 3, 1),
('22/03/2026', 'Aguardando reavaliação', 3, 3, 3, 3, 1),
-- Paciente 4
('13/01/2026', 'Hemoglobina glicada boa', 1, 1, 4, 1, 2),
('18/02/2026', 'Nenhuma fratura aparente', 2, 1, 4, 1, 2),
('23/03/2026', 'Jejum incompleto', 3, 2, 4, 1, 2),
-- Paciente 5
('14/01/2026', 'Anemia leve detectada', 1, 2, 5, 1, 1),
('19/02/2026', 'Exame inconclusivo', 2, 3, 5, 1, 1),
('24/03/2026', 'Glicemia ótima', 3, 1, 5, 1, 1);