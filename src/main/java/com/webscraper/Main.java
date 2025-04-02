package com.webscraper;

import com.webscraper.scraping.Config;
import com.webscraper.scraping.FileCompressor;
import com.webscraper.scraping.FileDownloader;
import com.webscraper.scraping.ScraperService;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            ScraperService scraper = new ScraperService();
            List<String> pdfLinks = scraper.getPdfLinks();

            FileDownloader downloader = new FileDownloader(Config.OUTPUT_FOLDER);
            for (String link : pdfLinks) {
                downloader.downloadFile(link);
            }

            FileCompressor fileCompressor = new FileCompressor(Config.OUTPUT_FOLDER, Config.OUTPUT_FOLDER+"anexos.zip");
            fileCompressor.compressFiles();

        } catch(IOException e){
            e.printStackTrace();
        }
    }
}