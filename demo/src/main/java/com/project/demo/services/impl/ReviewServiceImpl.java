package com.project.demo.services.impl;

import com.project.demo.dtos.ReviewCreateDto;
import com.project.demo.models.Review;
import com.project.demo.models.Soup;
import com.project.demo.models.User;
import com.project.demo.repositories.ReviewRepository;
import com.project.demo.repositories.SoupRepository;
import com.project.demo.repositories.UserRepository;
import com.project.demo.services.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final SoupRepository soupRepository;
    private final UserRepository userRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, SoupRepository soupRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.soupRepository = soupRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Review create(ReviewCreateDto reviewCreateDto, int soupId, int userId) {
        Soup soup = soupRepository.findById(soupId)
                .orElseThrow(() -> new RuntimeException("invalid soup id"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("invalid user id"));

        Review review = reviewCreateDto.toReview(new Review());
        review.setSoup(soup);
        review.setUser(user);

        return reviewRepository.save(review);
    }

    @Override
    public void delete(Integer reviewId) {
        if (reviewRepository.existsById(reviewId)) {
            reviewRepository.deleteById(reviewId);
        } else {
            throw new IllegalArgumentException("Review with ID " + reviewId + " not found");
        }
    }

    @Override
    public List<Review> getAll() {
        return reviewRepository.findAll();
    }

    @Override
    public List<Review> getBySoupId(Integer soupId) {
        return reviewRepository.findBySoupId(soupId);
    }
}