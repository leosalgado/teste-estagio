drop table if exists operadora_saude cascade;

CREATE TABLE operadora_saude (
    REGISTRO_OPERADORA CHAR(6) PRIMARY KEY,
    CNPJ CHAR(14) NOT NULL,
    Razao_Social VARCHAR(140) NOT NULL,
    Nome_Fantasia VARCHAR(140),
    Modalidade CHAR(50), -- 2?
    Logradouro VARCHAR(40),
    Numero VARCHAR(20),
    Complemento VARCHAR(40),
    Bairro VARCHAR(30),
    Cidade VARCHAR(30),
    UF CHAR(2),
    CEP CHAR(8),
    DDD CHAR(2), -- 4?
    Telefone VARCHAR(20),
    Fax VARCHAR(20),
    Endereco_eletronico VARCHAR(255),
    Representante VARCHAR(50),
    Cargo_Representante VARCHAR(40),
    Regiao_de_Comercializacao INT CHECK (Regiao_de_Comercializacao BETWEEN 1 AND 6),
    Data_Registro_ANS DATE
);

-- Get CSV
copy operadora_saude from '/var/lib/postgres/doc/operadoras_de_plano_de_saude_ativas/Relatorio_cadop.csv'
delimiter ';' csv header encoding 'UTF8';

-- Tests
SELECT * FROM operadora_saude;
SELECT * FROM operadora_saude WHERE REGISTRO_OPERADORA = '419761';