package com.yelp.service;

import com.yelp.domain.User;

public interface UserService {

	User registerNewUser(User user);

	User update(User user);

	void deleteById(Long id);

	User findById(Long id);

	User updateToAnonymous(User user);

}
