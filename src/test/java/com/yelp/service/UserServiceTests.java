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

import com.yelp.domain.User;
import com.yelp.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@Transactional
@Sql("/test-data.sql")
public class UserServiceTests {

	@Autowired
	UserService service;

	@Autowired
	UserRepository repository;

	private static final int USERS_IN_TEST_DATA = 2;

	@Test
	public void registerNewUser() {
		assertThat(repository.count()).isEqualTo(USERS_IN_TEST_DATA);
		User user = new User("fake@email.com", "Angelica", "Fulcher", "3456");
		service.registerNewUser(user);
		assertThat(repository.count()).isEqualTo(USERS_IN_TEST_DATA + 1);
	}

	@Test
	public void findById() {
		Long id = service.findById(1L).getId();
		assertThat(service.findById(id).getFirstName()).isEqualTo("Laurent");
	}

	@Test
	public void update() {
		User user = service.findById(1L);
		user.setEmail("fake2@email.com");
		service.update(user);
		assertThat(service.findById(1L).getEmail()).isEqualTo("fake2@email.com");
	}

	@Test
	public void updateToAnonymous() {
		User user = service.findById(1L);
		service.updateToAnonymous(user);
		assertThat(service.findById(1L).getFirstName()).isEqualTo("Anonymous");
		assertThat(service.findById(1L).getLastName()).isEmpty();
		assertThat(service.findById(1L).getEmail()).isEmpty();
	}

	// @Test
	// public void deleteById() {
	// assertThat(repository.count()).isEqualTo(USERS_IN_TEST_DATA);
	// Long id = service.findById(2L).getId();
	// service.deleteById(id);
	// assertThat(repository.count()).isEqualTo(USERS_IN_TEST_DATA - 1);
	// }

}
