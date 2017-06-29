package com.yelp.web;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.fromMethodCall;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;

import com.fasterxml.jackson.annotation.JsonView;
import com.yelp.domain.JsonViews;
import com.yelp.domain.Review;
import com.yelp.service.ReviewService;

@RestController
@RequestMapping("/restaurants/{restaurantId}/reviews")
public class RestReviewController {

	private final ReviewService reviewService;

	@Autowired
	public RestReviewController(ReviewService reviewService) {
		this.reviewService = reviewService;
	}

	@JsonView(JsonViews.Public.class)
	@GetMapping("/{id}")
	public Review retrieveReview(@PathVariable Long id) {
		return reviewService.findById(id);
	}

	@PostMapping
	public HttpEntity<Void> createReview(@RequestBody Review postedReview, @PathVariable Long restaurantId) {

		Review savedReview = reviewService.saveReviewforRestaurant(postedReview, restaurantId);

		UriComponents uriComponents = fromMethodCall(on(getClass()).retrieveReview(savedReview.getId())).build();

		return ResponseEntity.created(uriComponents.encode().toUri()).build();
	}

	@PutMapping("/{id}")
	public Review updateReview(@RequestBody Review review) {
		return reviewService.update(review);
	}

	@DeleteMapping("/{id}")
	public void deleteReview(@PathVariable Long id) {
		reviewService.deleteById(id);
	}

}
