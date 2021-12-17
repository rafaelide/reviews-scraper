package com.demo;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.springframework.stereotype.Component;

@Component
public class Scraper {

    public String getPageContent(String pageUrl) {
        try (final WebClient webClient = new WebClient()) {
            webClient.getOptions().setCssEnabled(false);
            webClient.getOptions().setJavaScriptEnabled(false);
            HtmlPage page = webClient.getPage(pageUrl);
            return page.asXml();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
