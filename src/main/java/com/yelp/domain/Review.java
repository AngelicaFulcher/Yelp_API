package com.yelp.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(exclude = "id")
public class Review implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonView(JsonViews.Public.class)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@JsonView(JsonViews.Public.class)
	@Column(name = "date_created", updatable = false, nullable = false)
	private LocalDateTime dateCreated = LocalDateTime.now();

	@JsonView(JsonViews.Public.class)
	@Column(nullable = false, length = 140)
	private String text;

	@JsonView(JsonViews.Public.class)
	@Column(nullable = false)
	private Integer rating;

	@ManyToOne(optional = false)
	private Restaurant restaurant;

	@JsonView(JsonViews.Public.class)
	@ManyToOne(optional = false)
	private User user;

	public Review() {

	}

	public Review(String text, Integer rating, Restaurant restaurant, User user) {
		this(null, text, rating, restaurant, user);
	}

	public Review(Long id, String text, Integer rating, Restaurant restaurant, User user) {
		this.id = id;
		this.text = text;
		this.rating = rating;
		this.restaurant = restaurant;
		this.user = user;
	}

	@PrePersist
	protected void prePersist() {
		this.dateCreated = LocalDateTime.now();
	}

	@Override
	public String toString() {
		return "Review [id=" + this.id + ", text=" + this.text + ", rating=" + this.rating + ", restaurant="
				+ this.restaurant.getName() + ", user=" + this.user.getFirstName() + ", dateCreated=" + this.dateCreated
				+ "]";
	}

}
