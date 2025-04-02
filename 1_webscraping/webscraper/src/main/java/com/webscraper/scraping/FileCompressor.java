package com.webscraper.scraping;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileCompressor {
    private final String outputFolder;
    private final String zipFilePath;

    public FileCompressor(String outputFolder, String zipFilePath) {
        this.outputFolder = outputFolder;
        this.zipFilePath = zipFilePath;
    }

    public void compressFiles() throws IOException{
        File folder = new File(outputFolder);
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".pdf"));

        if (files == null || files.length == 0){
            System.out.println("Nenhum arquivo.");
            return;
        }
        try(FileOutputStream fos = new FileOutputStream(zipFilePath);
        ZipOutputStream zipOutput = new ZipOutputStream(fos)){
            for (File file : files){
                try (FileInputStream fis = new FileInputStream(file)){
                    ZipEntry zipEntry = new ZipEntry(file.getName());
                    zipOutput.putNextEntry(zipEntry);

                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = fis.read(buffer)) != -1){
                        zipOutput.write(buffer, 0, bytesRead);
                    }
                    zipOutput.closeEntry();
                }
            }
            System.out.println("Arquivos compactados em " + zipFilePath);
        }
    }
}
