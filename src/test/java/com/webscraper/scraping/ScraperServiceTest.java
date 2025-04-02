package com.webscraper.scraping;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;


import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

class ScraperServiceTest {

    @Mock
    private Document mockDocument;

    @Mock
    private Elements mockElements;

    @Mock
    private Element mockElement1;

    @Mock
    private Element mockElement2;

    @Mock
    private Connection mockConnection;

    private ScraperService scraperService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        scraperService = new ScraperService();
    }

    @Test
    void getPdfLinks() throws IOException {
        when(mockElement1.absUrl("href")).thenReturn("https://example.com/Anexo_I.pdf");
        when(mockElement2.absUrl("href")).thenReturn("https://example.com/Anexo_II.pdf");

        when(mockElements.iterator()).thenReturn(List.of(mockElement1, mockElement2).iterator());
        when(mockDocument.select("a[href$=.pdf]")).thenReturn(mockElements);

        try (MockedStatic<Jsoup> mockedJsoup = mockStatic(Jsoup.class)) {
            mockedJsoup.when(() -> Jsoup.connect(Config.URL_SITE)).thenReturn(mockConnection);
            when(mockConnection.get()).thenReturn(mockDocument);

            List<String> pdfLinks = scraperService.getPdfLinks();

            assertEquals(2, pdfLinks.size());
            assertTrue(pdfLinks.contains("https://example.com/Anexo_I.pdf"));
            assertTrue(pdfLinks.contains("https://example.com/Anexo_II.pdf"));
        }
    }
}