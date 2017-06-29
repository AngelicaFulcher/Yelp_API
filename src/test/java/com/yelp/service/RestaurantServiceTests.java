package com.yelp.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.yelp.domain.Restaurant;
import com.yelp.repository.RestaurantRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@Transactional
@Sql("/test-data.sql")
public class RestaurantServiceTests {

	@Autowired
	RestaurantService service;

	@Autowired
	RestaurantRepository repository;

	private static final int RESTAURANTS_IN_TEST_DATA = 2;

	@Test
	public void findAll() {
		assertThat(service.findAll()).hasSize(RESTAURANTS_IN_TEST_DATA);
	}

	@Test
	public void findById() {
		Long id = service.findById(2L).getId();
		assertThat(service.findById(id).getName()).isEqualTo("Clouds");
	}

	@Test
	public void findByNameContaining() {
		Restaurant restaurant = service.findByNameIgnoreCaseContaining("thai").get(0);
		assertThat(restaurant.getName()).isEqualTo("RuenThai");
		assertThat(service.findByNameIgnoreCaseContaining("Thai").size()).isEqualTo(1);
	}
}
