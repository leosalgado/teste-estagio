-- Last year
SELECT 
    os.razao_social,
    SUM(
        CAST(REPLACE(dc.vl_saldo_final, ',', '.') AS NUMERIC) - 
        CAST(REPLACE(dc.vl_saldo_inicial, ',', '.') AS NUMERIC)
    ) AS total_despesa
FROM 
    demonstrativos_contabeis dc
JOIN 
    operadora_saude os 
    ON os.registro_operadora = dc.reg_ans
WHERE 
    dc.descricao ILIKE '%EVENTOS/ SINISTROS CONHECIDOS OU AVISADOS  DE ASSISTÊNCIA A SAÚDE MEDICO HOSPITALAR%'
    AND dc.data_demonstrativo BETWEEN (DATE '2024-10-01' - INTERVAL '1 year') AND DATE '2024-10-01'
GROUP BY 
    os.razao_social
ORDER BY 
    total_despesa DESC
LIMIT 10;

-- Last three months
SELECT 
    os.razao_social,
    SUM(
        CAST(REPLACE(dc.vl_saldo_final, ',', '.') AS NUMERIC) - 
        CAST(REPLACE(dc.vl_saldo_inicial, ',', '.') AS NUMERIC)
    ) AS total_despesa
FROM 
    demonstrativos_contabeis dc
JOIN 
    operadora_saude os 
    ON os.registro_operadora = dc.reg_ans
WHERE 
    dc.descricao ILIKE '%EVENTOS/ SINISTROS CONHECIDOS OU AVISADOS  DE ASSISTÊNCIA A SAÚDE MEDICO HOSPITALAR%'
    AND dc.data_demonstrativo BETWEEN (DATE '2024-10-01' - INTERVAL '3 months') AND DATE '2024-10-01'
GROUP BY 
    os.razao_social
ORDER BY 
    total_despesa DESC
LIMIT 10;