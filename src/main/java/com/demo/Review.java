package com.demo;

import java.util.List;


public class Review {

    private String reviewer;
    private String title;
    private String body;
    private Double rating;
    private List<String> employees;
    private String reviewDate;
    private boolean verified;

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public List<String> getEmployees() {
        return employees;
    }

    public void setEmployees(List<String> employees) {
        this.employees = employees;
    }

    public String getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(String reviewDate) {
        this.reviewDate = reviewDate;
    }

    public boolean getVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    @Override
    public String toString() {
        return "Review{" +
                "reviewer='" + reviewer + '\'' +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", rating=" + rating +
                ", employees=" + employees +
                ", reviewDate='" + reviewDate + '\'' +
                ", verified=" + verified +
                '}';
    }
}
