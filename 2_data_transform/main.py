import pdfplumber
import pandas as pd
import zipfile

pdf_path = (
    "../1_webscraping/webscraper/downloads/Anexo_I_Rol_2021RN_465.2021_RN627L.2024.pdf"
)
output_csv = "output.csv"
name = "Leonardo"
zip_name = f"Teste_{name}.zip"

dfs = []

with pdfplumber.open(pdf_path) as pdf:
    for page in pdf.pages:
        table = page.extract_table()
        if table:
            df = pd.DataFrame(table)

            if not dfs:
                df.iloc[0] = df.iloc[0].replace(
                    {"OD": "Seg. Odontológica", "AMB": "Seg. Ambulatorial"}
                )
                dfs.append(df)
            else:
                dfs.append(df.iloc[1:])

df_final = pd.concat(dfs, ignore_index=True)
df_final.to_csv(output_csv, index=False, header=False)

with zipfile.ZipFile(zip_name, "w", zipfile.ZIP_DEFLATED) as zipf:
    zipf.write(output_csv)

print(f"Arquivo CSV salvo como: {output_csv} e compactado como: {zip_name}")
