package com.yelp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yelp.domain.User;
import com.yelp.repository.UserRepository;

@Transactional(readOnly = true)
@Service
public class DefaultUserService implements UserService {

	private final UserRepository repository;

	// To encrypt the password. Bean created in YelpApplication
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public DefaultUserService(UserRepository repository, PasswordEncoder passwordEncoder) {
		this.repository = repository;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional(readOnly = false)
	@Override
	public User registerNewUser(User user) {
		// 2 steps before creating the user in database:

		// First, set id to null to make sure we're creating a new user and not
		// updating an existing one.
		user.setId(null);

		// Second, encrypt password. Bean created in YelpApplication:
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		// Now we store the user in the database
		return repository.save(user);

	}

	@Transactional(readOnly = false)
	@Override
	public User update(User updatedUser) {
		return repository.save(updatedUser);
	}

	// User is wrapped in an Optional object in our repository. Therefore, we
	// need to define what to do when the user does not exist (null).
	@Override
	public User findById(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Could not find User with ID [" + id + "]"));
	}

	@Transactional(readOnly = false)
	@Override
	public User updateToAnonymous(User user) {
		user.setFirstName("Anonymous");
		user.setLastName("");
		user.setEmail("");
		return repository.save(user);
	}

	@Transactional(readOnly = false)
	@Override
	public void deleteById(Long id) {
		repository.delete(id);
	}
}
