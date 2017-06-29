package com.yelp.repository;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import com.yelp.domain.User;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@Transactional
@Sql("/test-data.sql")
public class UserRepositoryTests {

	private static final int USERS_IN_TEST_DATA = 2;

	@Autowired
	UserRepository repository;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Test
	public void count() {
		assertThat(repository.count()).isEqualTo(USERS_IN_TEST_DATA);
	}

	@Test
	public void findById() {
		Long id = repository.findById(1L).get().getId();
		assertThat(repository.findById(id)).isPresent();
		assertThat(repository.findById(999999L)).isNotPresent();
		assertThat(repository.findById(id).get().getFirstName()).isEqualTo("Laurent");
	}

	@Test
	public void save() {
		User user = new User("fake1@email.com", "Alice", "Doe", "5678");
		repository.save(user);
		assertNumUsers(USERS_IN_TEST_DATA + 1);
	}

	@Test
	public void findAll() {
		List<String> firstNames = repository.findAll().stream().map(User::getFirstName).collect(toList());
		assertThat(firstNames).containsExactlyInAnyOrder("Laurent", "Bob");
	}

	// @Test
	// public void deleteById() {
	// assertNumUsers(2);
	// Long id = repository.findById(1L).get().getId();
	// repository.delete(id);
	// repository.flush();
	// assertNumUsers(USERS_IN_TEST_DATA - 1);
	// }

	private void assertNumUsers(int expected) {
		assertThat(JdbcTestUtils.countRowsInTable(jdbcTemplate, "user")).isEqualTo(expected);
	}

}