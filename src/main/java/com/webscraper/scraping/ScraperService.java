package com.webscraper.scraping;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScraperService {
    public List<String> getPdfLinks() throws IOException{
        List<String> pdfLinks = new ArrayList<>();
        Document doc = Jsoup.connect(Config.URL_SITE).get();
        Elements links = doc.select("a[href$=.pdf]");

        for (Element link: links){
            String fileUrl = link.absUrl("href");
            if(fileUrl.contains("Anexo_I") || fileUrl.contains("Anexo_II")){
                pdfLinks.add(fileUrl);
            }
        }
        return pdfLinks;
    }
}
