package com.yelp.repository;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import com.yelp.domain.Review;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Sql("/test-data.sql")
public class ReviewRepositoryTests {

	private static final int REVIEWS_IN_TEST_DATA = 3;

	@Autowired
	ReviewRepository repository;

	@Test
	public void count() {
		assertThat(repository.count()).isEqualTo(REVIEWS_IN_TEST_DATA);
	}

	@Test
	public void findById() {
		Long id = repository.findById(1L).get().getId();
		assertThat(repository.findById(id)).isPresent();
		assertThat(repository.findById(999999L)).isNotPresent();
		assertThat(repository.findById(id).get().getText()).isEqualTo("excellent");
	}

	@Test
	public void findByRestaurantId() {
		List<Review> reviews = repository.findByRestaurantId(1L);
		assertThat(reviews).isNotNull();
		List<String> reviewTexts = reviews.stream().map(Review::getText).collect(toList());
		assertThat(reviewTexts).containsExactlyInAnyOrder("excellent", "good");
	}

}
