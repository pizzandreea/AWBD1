package com.project.demo.dtos;

import com.project.demo.models.Review;

public class ReviewCreateDto {
    private String comment;
    //aici validari
    private int rating;
//    private Integer soupId;
//    private Integer userId;


    public Review toReview(Review review) {
        review.setComment(this.comment);
        review.setRating(this.rating);
        return review;
    }

    // Getters and Setters

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }


}
