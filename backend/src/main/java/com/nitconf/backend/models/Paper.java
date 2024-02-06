package com.nitconf.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

@Document(collection = "Paper")
public class Paper {
    @Id
    private String id;
    private String status;
    private String paperName;
    private Integer rating;
    private String authorName;
    private List<String> tags;
    private List<Review> reviews;
    private String decision;
    private byte[] pdfFile;


    public Paper(String id, String status, String paperName, Integer rating, String authorName, List<String> tags,
            List<Review> reviews, String decision,byte[] pdfFile) {
        this.id = id;
        this.status = status;
        this.paperName = paperName;
        this.rating = rating;
        this.authorName = authorName;
        this.tags = tags;
        this.reviews = reviews;
        this.decision = decision;
        this.pdfFile = pdfFile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaperName() {
        return paperName;
    }

    public void setPaperName(String paperName) {
        this.paperName = paperName;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating() {
        if (reviews != null && !reviews.isEmpty()) {
            int sum = 0;
            for (Review review : reviews) {
                if (review.getRating() != null) {
                    sum += review.getRating();
                }
            }
            this.rating = sum / reviews.size();
        } else {
            this.rating = null;
        }
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public byte[] getPdfFile() {
        return pdfFile;
    }

    public void setPdfFile(byte[] pdfFile) {
        this.pdfFile = pdfFile;
    }

    public static class Review {

        private String reviewer;
        private String comments;
        private Integer rating;
        private String reviewerDecision;

        public Review(String reviewer, String comments, Integer rating, String reviewerDecision) {
            this.reviewer = reviewer;
            this.comments = comments;
            this.rating = rating;
            this.reviewerDecision = reviewerDecision;
        }
        
        public String getComments() {
            return comments;
        }
        public void setComments(String comments) {
            this.comments = comments;
        }
        public Integer getRating() {
            return rating;
        }
        public void setRating(Integer rating) {
            this.rating = rating;
        }
        public String getReviewerDecision() {
            return reviewerDecision;
        }
        public void setReviewerDecision(String reviewerDecision) {
            this.reviewerDecision = reviewerDecision;
        }

        public String getReviewer() {
            return reviewer;
        }

        public void setReviewer(String reviewer) {
            this.reviewer = reviewer;
        }
        
    }
}