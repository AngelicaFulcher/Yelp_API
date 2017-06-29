package com.yelp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yelp.domain.Restaurant;
import com.yelp.domain.Review;
import com.yelp.repository.ReviewRepository;

@Transactional(readOnly = true)
@Service
public class DefaultReviewService implements ReviewService {

	private final ReviewRepository repository;

	private final RestaurantService restaurantService;

	@Autowired
	public DefaultReviewService(ReviewRepository repository, RestaurantService restaurantService) {
		this.repository = repository;
		this.restaurantService = restaurantService;
	}

	@Transactional(readOnly = false)
	@Override
	public Review saveReviewforRestaurant(Review review, Long restaurantId) {

		// Link review to restaurant
		Restaurant restaurant = restaurantService.findById(restaurantId);
		restaurant.addReview(review);

		// Make sure we are saving a new review and not updating an existing one
		review.setId(null);

		return repository.save(review);
	}

	@Override
	public Review findById(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Could not find Review with ID [" + id + "]"));
	}

	@Override
	public List<Review> findAllByRestaurantId(Long id) {
		return repository.findByRestaurantId(id);
	}

	@Transactional(readOnly = false)
	@Override
	public Review update(Review review) {
		return repository.save(review);
	}

	@Transactional(readOnly = false)
	@Override
	public void deleteById(Long id) {
		repository.delete(id);

	}

}
