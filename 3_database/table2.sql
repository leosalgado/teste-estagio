drop table if exists demonstrativos_contabeis cascade;

CREATE TABLE demonstrativos_contabeis (
	id SERIAL PRIMARY KEY,
    Data_demonstrativo DATE NOT NULL,
    Reg_ans CHAR(6),
    Cd_conta_contabil INTEGER NOT NULL,
    Descricao VARCHAR(255) NOT NULL,
    Vl_saldo_inicial TEXT,
    Vl_saldo_final TEXT
);

-- Get CSVs
CREATE OR REPLACE FUNCTION import_quarterly_data(year_val int)
RETURNS void AS $$
DECLARE
  quarter int;
BEGIN
  FOR quarter IN 1..4 LOOP
	BEGIN
    EXECUTE format('COPY demonstrativos_contabeis (Data_demonstrativo, Reg_ans, Cd_conta_contabil, Descricao, Vl_saldo_inicial, Vl_saldo_final) FROM ''/var/lib/postgres/doc/%s/%sT%s.csv'' DELIMITER '';'' CSV HEADER ENCODING ''UTF8'';', 
                  year_val, quarter, year_val);
    EXCEPTION
      WHEN OTHERS THEN
        EXECUTE format('COPY demonstrativos_contabeis (Data_demonstrativo, Reg_ans, Cd_conta_contabil, Descricao, Vl_saldo_inicial, Vl_saldo_final) FROM ''/var/lib/postgres/doc/%s/%st%s.csv'' DELIMITER '';'' CSV HEADER ENCODING ''UTF8'';', 
                  year_val, quarter, year_val);
	END;
  END LOOP;
  
  RAISE NOTICE 'Import of quarterly data for year % completed', year_val;
END;
$$ LANGUAGE plpgsql;

SELECT import_quarterly_data(2023);
SELECT import_quarterly_data(2024);

-- Tests
SELECT * FROM demonstrativos_contabeis;
SELECT * FROM demonstrativos_contabeis WHERE demonstrativos_contabeis.reg_ans  = '342807'
AND demonstrativos_contabeis.cd_conta_contabil  = '217119012';

-- Check header
SELECT * FROM demonstrativos_contabeis WHERE demonstrativos_contabeis.reg_ans = 'REG_ANS';