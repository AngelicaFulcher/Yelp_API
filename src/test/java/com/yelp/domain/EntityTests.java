package com.yelp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class EntityTests {

	@Test
	public void testConstructors() {
		User user = new User("Angelica", "Fulcher", "fake@email.com", "3456");
		Restaurant restaurant = new Restaurant("Lumiere", "Widdergasse 5", "Zürich", "044 211 56 65", "logo",
				"http://restaurant-lumiere.ch/");
		Review review = new Review("Excellent!", 5, restaurant, user);

		assertThat(review.getUser()).isEqualTo(user);
		assertThat(review.getRestaurant()).isEqualTo(restaurant);
	}

	@Test
	public void addReviewInRestaurant() {

		// Given
		Restaurant restaurant = new Restaurant("Lumiere", "Widdergasse 5", "Zürich", "044 211 56 65", "logo",
				"http://restaurant-lumiere.ch/");
		User user = new User("Angelica", "Fulcher", "fake@email.com", "3456");
		Review review = new Review("Excellent!", 5, restaurant, user);

		// When
		restaurant.addReview(review);

		// Then
		assertThat(restaurant.getReviews()).containsExactlyInAnyOrder(review);
	}

}
