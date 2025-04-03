import pdfplumber
import pandas as pd
import zipfile

pdf_path = (
    "../1_webscraping/webscraper/downloads/Anexo_I_Rol_2021RN_465.2021_RN627L.2024.pdf"
)
output_csv = "output.csv"

dfs = []

with pdfplumber.open(pdf_path) as pdf:
    for page in pdf.pages:
        table = page.extract_table()
        if table:
            df = pd.DataFrame(table)

            if not dfs:
                dfs.append(df)
            else:
                dfs.append(df.iloc[1:])

df_final = pd.concat(dfs, ignore_index=True)
df_final.to_csv(output_csv, index=False, header=False)

print(f"Arquivo CSV salvo como: {output_csv}")
