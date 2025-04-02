package com.webscraper.scraping;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileDownloader {
    private final String outputFolder;

    public FileDownloader(String outputFolder) {
        this.outputFolder = outputFolder;
        new java.io.File(outputFolder).mkdirs();
    }

    public void downloadFile(String fileURL) throws IOException{
        URL url = new URL(fileURL);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setRequestMethod("GET");

        int responseCode = httpConn.getResponseCode();
        if(responseCode == HttpURLConnection.HTTP_OK){
            String fileName = fileURL.substring(fileURL.lastIndexOf("/")+1);
            try (InputStream inputStream = httpConn.getInputStream();
            FileOutputStream outputStream = new FileOutputStream(outputFolder + fileName)){

                byte[] buffer = new byte[4096];
                int bytesRead;
                while((bytesRead = inputStream.read(buffer)) != -1){
                    outputStream.write(buffer, 0, bytesRead);
                }
                System.out.println("Download concluido " + fileName);
            }
        }
        httpConn.disconnect();
    }
}
