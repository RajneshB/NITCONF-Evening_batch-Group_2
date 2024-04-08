package com.nitconf.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


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
            double totalScore = 0;
            int criteriaCount = 0;

            for (Review review : reviews) {
                review.setOverallScore();
                totalScore += review.getOverallScore();
                criteriaCount++;
            }

            this.rating = (int) Math.round(totalScore / criteriaCount);
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
        private Integer originalityScore;
        private Integer significanceScore;
        private Integer methodologyScore;
        private Integer clarityAndOrganizationScore;
        private Integer literatureReviewScore;
        private Integer resultsAndDiscussionScore;
        private Integer referencesAndCitationsScore;
        private Integer ethicalConsiderationsScore;
        private String reviewerDecision;
        private Integer overallScore;
    
        public Review(String reviewer, String comments, Integer originalityScore, Integer significanceScore,
                        Integer methodologyScore, Integer clarityAndOrganizationScore, Integer literatureReviewScore,
                        Integer resultsAndDiscussionScore, Integer referencesAndCitationsScore,
                        Integer ethicalConsiderationsScore, String reviewerDecision) {
            this.reviewer = reviewer;
            this.comments = comments;
            this.originalityScore = originalityScore;
            this.significanceScore = significanceScore;
            this.methodologyScore = methodologyScore;
            this.clarityAndOrganizationScore = clarityAndOrganizationScore;
            this.literatureReviewScore = literatureReviewScore;
            this.resultsAndDiscussionScore = resultsAndDiscussionScore;
            this.referencesAndCitationsScore = referencesAndCitationsScore;
            this.ethicalConsiderationsScore = ethicalConsiderationsScore;
            this.reviewerDecision = reviewerDecision;
        }

        public void setOverallScore() {
            int totalScores = originalityScore + significanceScore + methodologyScore +
                    clarityAndOrganizationScore + literatureReviewScore + resultsAndDiscussionScore +
                    referencesAndCitationsScore + ethicalConsiderationsScore;
            int criteriaCount = 8;
    
            overallScore = totalScores / criteriaCount;
        }

        public Integer getOverallScore() {
            return overallScore;
        }
        
    
        // Getters and Setters
        public String getReviewer() {
            return reviewer;
        }
    
        public void setReviewer(String reviewer) {
            this.reviewer = reviewer;
        }
    
        public String getComments() {
            return comments;
        }
    
        public void setComments(String comments) {
            this.comments = comments;
        }
    
        public Integer getOriginalityScore() {
            return originalityScore;
        }
    
        public void setOriginalityScore(Integer originalityScore) {
            this.originalityScore = originalityScore;
        }
    
        public Integer getSignificanceScore() {
            return significanceScore;
        }
    
        public void setSignificanceScore(Integer significanceScore) {
            this.significanceScore = significanceScore;
        }
    
        public Integer getMethodologyScore() {
            return methodologyScore;
        }
    
        public void setMethodologyScore(Integer methodologyScore) {
            this.methodologyScore = methodologyScore;
        }
    
        public Integer getClarityAndOrganizationScore() {
            return clarityAndOrganizationScore;
        }
    
        public void setClarityAndOrganizationScore(Integer clarityAndOrganizationScore) {
            this.clarityAndOrganizationScore = clarityAndOrganizationScore;
        }
    
        public Integer getLiteratureReviewScore() {
            return literatureReviewScore;
        }
    
        public void setLiteratureReviewScore(Integer literatureReviewScore) {
            this.literatureReviewScore = literatureReviewScore;
        }
    
        public Integer getResultsAndDiscussionScore() {
            return resultsAndDiscussionScore;
        }
    
        public void setResultsAndDiscussionScore(Integer resultsAndDiscussionScore) {
            this.resultsAndDiscussionScore = resultsAndDiscussionScore;
        }
    
        public Integer getReferencesAndCitationsScore() {
            return referencesAndCitationsScore;
        }
    
        public void setReferencesAndCitationsScore(Integer referencesAndCitationsScore) {
            this.referencesAndCitationsScore = referencesAndCitationsScore;
        }
    
        public Integer getEthicalConsiderationsScore() {
            return ethicalConsiderationsScore;
        }
    
        public void setEthicalConsiderationsScore(Integer ethicalConsiderationsScore) {
            this.ethicalConsiderationsScore = ethicalConsiderationsScore;
        }
    
        public String getReviewerDecision() {
            return reviewerDecision;
        }
    
        public void setReviewerDecision(String reviewerDecision) {
            this.reviewerDecision = reviewerDecision;
        }
    }
}