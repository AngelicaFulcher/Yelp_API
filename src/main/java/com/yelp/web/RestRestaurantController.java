package com.yelp.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.yelp.domain.JsonViews;
import com.yelp.domain.Restaurant;
import com.yelp.domain.Review;
import com.yelp.service.RestaurantService;
import com.yelp.service.ReviewService;

@RestController
@RequestMapping("/restaurants")
public class RestRestaurantController {

	private final RestaurantService restaurantService;
	private final ReviewService reviewService;

	@Autowired
	public RestRestaurantController(RestaurantService restaurantService, ReviewService reviewService) {
		this.restaurantService = restaurantService;
		this.reviewService = reviewService;
	}

	@JsonView(JsonViews.Public.class)
	@GetMapping
	public List<Restaurant> retrieveAllRestaurants() {
		return restaurantService.findAll();
	}

	@JsonView(JsonViews.Public.class)
	@GetMapping("/{id}")
	public Restaurant retrieveRestaurant(@PathVariable Long id) {
		return restaurantService.findById(id);
	}

	@JsonView(JsonViews.Public.class)
	@GetMapping("/{id}/reviews")
	public List<Review> retrieveReviewsByRestaurantId(@PathVariable Long restaurantId) {
		return reviewService.findAllByRestaurantId(restaurantId);
	}

	@JsonView(JsonViews.Public.class)
	@GetMapping("/search")
	public List<Restaurant> retrieveRestaurantByName(@RequestParam("name") String name) {
		return restaurantService.findByNameIgnoreCaseContaining(name);
	}
}
