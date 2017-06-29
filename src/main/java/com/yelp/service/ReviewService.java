package com.yelp.service;

import java.util.List;

import com.yelp.domain.Review;

public interface ReviewService {

	Review saveReviewforRestaurant(Review review, Long id);

	Review findById(Long id);

	List<Review> findAllByRestaurantId(Long id);

	Review update(Review review);

	void deleteById(Long id);

}
