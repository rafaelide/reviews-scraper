package com.demo;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Component
public class ReviewScraper {

    public static final String PAGE = "https://www.dealerrater.com/dealer/McKaig-Chevrolet-Buick-A-Dealer-For-The-People-dealer-reviews-23685/page";
    public static final String DATE_REGEX = "\\b(?:January?|February?|March?|April?|May?|June?|July?|August?|September?|October?|November?|December?) (\\d+), (?:19[7-9]\\d|2\\d{3})(?=\\D|$)";
    private static final String TITLE_REGEX = "<span class=\"review-title bolder font-18 italic\">(.*?)<\\/span>";
    private static final String BODY_REGEX = "<span class=\"review-whole display-none\">(.*?)<\\/span>";
    private static final String RATING_REGEX = "dealership-rating\">(.*?)rating-(\\d+)";
    private static final String VERIFIED_REGEX = "verified customer";
    private static final String REVIEWER_REGEX = "by (.*?)<\\/span>";
    private static final String EMPLOYEES_REGEX = "emp-id=(.*?)>(.*?)<\\/a>";
    public static final String REVIEW_LIST_REGEX = "class=\"review-entry(.*?)<a href=\"#\" class=\"helpful-button\"";
    private final Scraper scraper;

    public ReviewScraper(Scraper scraper) {
        this.scraper = scraper;
    }

    public List<Review> getOffendingReviews() {
        return Stream.iterate(1, n -> n + 1)
                .limit(5)
                .map(n -> loadPage(PAGE + n))
                .map(page -> multiContentExtractor(REVIEW_LIST_REGEX, page, 0))
                .flatMap(List::stream)
                .map(r -> extractReview(r))
                .filter(r -> r.getVerified() == false && r.getRating().equals(5d) && r.getEmployees().size() > 3)
                .sorted(Comparator.comparing( (Review c) -> c.getEmployees().size()).reversed())
                .limit(3)
                .collect(Collectors.toList());

    }

    private Review extractReview(String r) {
        Review review = new Review();
        review.setTitle(cleanUp(contentExtractor(TITLE_REGEX, r, 0)));
        review.setBody(cleanUp(contentExtractor(BODY_REGEX, r, 0)));
        review.setRating(extractRating(r));
        review.setVerified(extractVerified(r));
        review.setReviewer(extractReviewer(r));
        review.setEmployees(extractEmployees(r));
        review.setReviewDate(extractDate(r));
        return review;
    }

    public String extractDate(String r) {
        return contentExtractor(DATE_REGEX, r, 0);
    }

    public List<String> extractEmployees(String r) {
        return multiContentExtractor(EMPLOYEES_REGEX, r, 2);
    }

    public String extractReviewer(String r) {
        return contentExtractor(REVIEWER_REGEX, r, 1).trim();
    }

    public Boolean extractVerified(String r) {
        return r.contains(VERIFIED_REGEX);
    }

    private Double extractRating(String r) {
        try {
            var d = Double.parseDouble(cleanUp(contentExtractor(RATING_REGEX, r, 2))) / 10;
            return d;
        } catch (Exception e) {
            return 0d;
        }
    }

    public String loadPage(String url) {
        return scraper.getPageContent(url).replaceAll("\n", "").replaceAll("\r", "");
    }


    public String cleanUp(String s) {
        return s.replaceAll("\\<[^>]*>", "")
                .trim();
    }


    public String contentExtractor(String regex, String content, int group) {
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(content);
        if (m.find())
            return m.group(group);
        return "";
    }

    public List<String> multiContentExtractor(String regex, String content, int group) {
        Pattern pattern = Pattern.compile(regex);
        List<String> list = new ArrayList<>();
        Matcher m = pattern.matcher(content);
        while (m.find()) {
            list.add(m.group(group).trim());
        }
        return list;
    }


}
