package com.yelp.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@EqualsAndHashCode(exclude = "id")
@ToString(exclude = "password")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonView(JsonViews.Public.class)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@JsonView(JsonViews.Public.class)
	@Column(name = "first_name", nullable = false, length = 50)
	private String firstName;

	@JsonView(JsonViews.Public.class)
	@Column(name = "last_name", nullable = false, length = 50)
	private String lastName;

	@JsonView(JsonViews.Public.class)
	@Column(nullable = false, length = 50)
	private String email;

	// BCrypt encoded passwords can need 50-76 characters.
	@Column(nullable = false, length = 76)
	private String password;

	public User() {

	}

	public User(String firstName, String lastName, String email, String password) {
		this(null, firstName, lastName, email, password);
	}

	public User(Long id, String firstName, String lastName, String email, String password) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}

}
