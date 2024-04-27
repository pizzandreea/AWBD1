package com.project.demo.controllers;

import com.project.demo.models.Review;
import com.project.demo.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@CrossOrigin
public class ReviewController {
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/getAllReviews")
    public String getAll(Model model) {
        List<Review> soupsList = reviewService.getAll();
        model.addAttribute("reviews", soupsList);
        return "reviews-list";
    }

    @GetMapping("/createReview")
    public String create(Model model) {
        Review review = new Review();
        model.addAttribute("review", review);
        return "reviews-create";
    }

}
