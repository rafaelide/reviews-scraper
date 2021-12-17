package com.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ReviewScraperTest {

    ReviewScraper reviewScraper;

    @Mock
    Scraper scraper;

    @BeforeEach
    void setup() {
        reviewScraper = new ReviewScraper(scraper);
    }

    @Test
    void testMultiContentExtractor() {
        String c1 = "<div class=\"review-entry\">content1</div>";
        String c2 = "<div class=\"review-entry\">content2</div>";
        String c3 = "<div class=\"review-entry\">content3</div>";
        var result = reviewScraper.multiContentExtractor("<div class=\"review-entry(.*?)<\\/div>", c1 + c2 + c3,0);

        assertThat(result).contains(c1, c2, c3);
    }

    @Test
    void testContentExtractor() {
        String s = "asdf";
        var res = reviewScraper.contentExtractor("a", s, 0);
        assertThat(res).isEqualTo("a");
    }

}