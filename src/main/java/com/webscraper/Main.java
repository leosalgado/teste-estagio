package com.webscraper;

import com.webscraper.scraping.Config;
import com.webscraper.scraping.FileDownloader;
import com.webscraper.scraping.ScraperService;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            ScraperService scraper = new ScraperService();
            List<String> pdfLinks = scraper.getPdfLinks();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}