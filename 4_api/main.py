from fastapi import FastAPI, Query
from typing import Union
import pandas as pd
import os

app = FastAPI()

csv = "Relatorio_cadop.csv"
if not os.path.exists(csv):
    raise FileNotFoundError(f"O arquivo {csv} não foi encontrado.")

df = pd.read_csv(csv, delimiter=";")

required_columns = {
    "Nome_Fantasia",
    "Razao_Social",
    "CNPJ",
    "Modalidade",
    "Cidade",
    "UF",
}
if not required_columns.issubset(df.columns):
    raise ValueError(
        f"O CSV não contém todas as colunas necessárias: {required_columns}"
    )


@app.get("/search")
def buscar_operadora(name: str = Query(..., description="Nome da operadora")):
    """Busca operadoras pelo Nome Fantasia ou Razão Social."""
    result = df[
        df["Nome_Fantasia"].str.contains(name, case=False, na=False)
        | df["Razao_Social"].str.contains(name, case=False, na=False)
    ]

    return (
        result[["Nome_Fantasia", "Razao_Social", "CNPJ", "Modalidade", "Cidade", "UF"]]
        .fillna("")
        .to_dict(orient="records")
    )
