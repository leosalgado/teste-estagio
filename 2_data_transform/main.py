import pdfplumber
import pandas as pd
import zipfile
import csv

pdf_path = (
    "../1_webscraping/webscraper/downloads/Anexo_I_Rol_2021RN_465.2021_RN627L.2024.pdf"
)
output_csv = "output.csv"

with pdfplumber.open(pdf_path) as pdf:
    first_page = pdf.pages[2]
    table = first_page.extract_table()

with open(output_csv, mode="w", newline="", encoding="utf-8") as file:
    writer = csv.writer(file)

    for row in table:
        writer.writerow(row)

print(f"Arquivo CSV salvo como: {output_csv}")
