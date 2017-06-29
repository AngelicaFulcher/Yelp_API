package com.yelp.service;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.yelp.domain.Restaurant;
import com.yelp.domain.Review;
import com.yelp.domain.User;
import com.yelp.repository.ReviewRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@Transactional
@Sql("/test-data.sql")
public class ReviewServiceTests {

	@Autowired
	ReviewService reviewService;

	@Autowired
	RestaurantService restaurantService;

	@Autowired
	UserService userService;

	@Autowired
	ReviewRepository repository;

	private static final int REVIEWS_IN_TEST_DATA = 3;

	@Test
	public void saveReviewforRestaurant() {
		assertThat(repository.count()).isEqualTo(REVIEWS_IN_TEST_DATA);

		User user = new User("fake@email.com", "Angelica", "Fulcher", "3456");
		userService.registerNewUser(user);

		Restaurant restaurant = restaurantService.findById(1L);
		Review review = new Review("Very good", 4, restaurant, user);
		reviewService.saveReviewforRestaurant(review, restaurant.getId());
		assertThat(repository.count()).isEqualTo(REVIEWS_IN_TEST_DATA + 1);
	}

	@Test
	public void findById() {
		Long id = reviewService.findById(1L).getId();
		assertThat(reviewService.findById(id).getText()).isEqualTo("excellent");
		assertThat(reviewService.findById(id).getRestaurant().getName()).isEqualTo("RuenThai");
		assertThat(reviewService.findById(id).getUser().getFirstName()).isEqualTo("Laurent");
	}

	@Test
	public void findAllByRestaurantId() {
		List<Review> reviews = reviewService.findAllByRestaurantId(1L);
		assertThat(reviews.size()).isEqualTo(2);

		List<String> reviewTexts = reviews.stream().map(Review::getText).collect(toList());
		assertThat(reviewTexts).containsExactlyInAnyOrder("excellent", "good");
	}

	@Test
	public void update() {
		Review review = reviewService.findById(1L);
		review.setText(review.getText() + ", super recommended");
		reviewService.update(review);
		assertThat(review.getText()).isEqualTo("excellent, super recommended");
	}

	@Test
	public void deleteById() {
		assertThat(repository.count()).isEqualTo(REVIEWS_IN_TEST_DATA);
		Long id = reviewService.findById(2L).getId();
		reviewService.deleteById(id);
		assertThat(repository.count()).isEqualTo(REVIEWS_IN_TEST_DATA - 1);
	}

}
