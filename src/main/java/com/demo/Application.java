package com.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
        ReviewScraper reviewScraper = ctx.getBean(ReviewScraper.class);
        System.out.println("Collecting latest reviews");
        var reviews = reviewScraper.getOffendingReviews();
        System.out.println("===========================================================");
        reviews.forEach(Application::printReview);
    }

    public static void printReview(Review r) {
        System.out.println(String.format("Reviewer: %s", r.getReviewer()));
        System.out.println(String.format("Date: %s", r.getReviewDate()));
        System.out.println(String.format("Rating: %.2f", r.getRating()));
        System.out.println(String.format("Body: %s", r.getTitle() +" " + r.getBody() ));
        System.out.println("Employees:");
        r.getEmployees().forEach( e -> System.out.println(String.format("  %s",e)));
        System.out.println("===========================================================");
    }
}
