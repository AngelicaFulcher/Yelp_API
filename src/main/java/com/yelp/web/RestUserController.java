package com.yelp.web;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.fromMethodCall;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;

import com.fasterxml.jackson.annotation.JsonView;
import com.yelp.domain.JsonViews;
import com.yelp.domain.User;
import com.yelp.service.UserService;

@RestController
@RequestMapping("/users")
public class RestUserController {

	private final UserService userService;

	@Autowired
	public RestUserController(UserService userService) {
		this.userService = userService;
	}

	@JsonView(JsonViews.Public.class)
	@GetMapping("/{id}")
	public User retrieveUser(Long id) {
		return userService.findById(id);
	}

	@PutMapping("/{id}")
	public User updateUserProfile(@RequestBody User updatedUser) {
		return userService.update(updatedUser);
	}

	@PutMapping("/{id}")
	public User updateUserToAnonymous(@RequestBody User user) {
		return userService.updateToAnonymous(user);
	}

	@PostMapping("/{sign_up}")
	public HttpEntity<Void> registerNewUser(@RequestBody User postedUser) {
		User savedUser = userService.registerNewUser(postedUser);

		UriComponents uriComponents = fromMethodCall(on(getClass()).retrieveUser(savedUser.getId())).build();

		return ResponseEntity.created(uriComponents.encode().toUri()).build();
	}

}
