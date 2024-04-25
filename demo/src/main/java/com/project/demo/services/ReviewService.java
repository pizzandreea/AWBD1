package com.project.demo.services;

import com.project.demo.dtos.ReviewCreateDto;
import com.project.demo.models.Review;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReviewService {
    public Review create(ReviewCreateDto reviewCreateDto, int soupId, int userId);
    public void delete(Integer reviewId);
    public List<Review> getAll();
    public List<Review> getBySoupId(Integer soupId);

}
