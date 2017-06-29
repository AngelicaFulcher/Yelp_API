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

import com.yelp.domain.Restaurant;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Sql("/test-data.sql")
public class RestaurantRepositoryTests {

	private static final int RESTAURANTS_IN_TEST_DATA = 2;

	@Autowired
	RestaurantRepository repository;

	@Test
	public void count() {
		assertThat(repository.count()).isEqualTo(RESTAURANTS_IN_TEST_DATA);
	}

	@Test
	public void findById() {
		Long id = repository.findById(1L).get().getId();
		assertThat(repository.findById(id)).isPresent();
		assertThat(repository.findById(999999L)).isNotPresent();
		assertThat(repository.findById(id).get().getName()).isEqualTo("RuenThai");
	}

	@Test
	public void findByNameIgnoreCaseContaining() {
		Restaurant restaurant = findThaiRestaurant();
		assertThat(restaurant.getName()).isEqualTo("RuenThai");
	}

	@Test
	public void findAll() {
		List<String> names = repository.findAll().stream().map(Restaurant::getName).collect(toList());
		assertThat(names).containsExactlyInAnyOrder("RuenThai", "Clouds");
	}

	private Restaurant findThaiRestaurant() {
		List<Restaurant> restaurants = repository.findByNameIgnoreCaseContaining("thai");
		assertThat(restaurants).hasSize(1);
		return restaurants.get(0);
	}

}
