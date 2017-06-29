package com.yelp.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@EqualsAndHashCode(exclude = "id")
@ToString(exclude = "reviews")
public class Restaurant implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonView(JsonViews.Public.class)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@JsonView(JsonViews.Public.class)
	@Column(nullable = false, length = 50)
	private String name;

	@JsonView(JsonViews.Public.class)
	@Column(nullable = false, length = 100)
	private String address;

	@JsonView(JsonViews.Public.class)
	@Column(nullable = false, length = 50)
	private String city;

	@JsonView(JsonViews.Public.class)
	@Column(name = "phone_number", nullable = false, length = 50)
	private String phoneNumber;

	@JsonView(JsonViews.Public.class)
	@Column(name = "logo_uri", nullable = false, length = 50)
	private String logoUri;

	@JsonView(JsonViews.Public.class)
	@Column(nullable = false, length = 100)
	private String uri;

	@OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
	// @JsonView(JsonViews.Public.class)
	private List<Review> reviews = new ArrayList<>();

	public Restaurant() {

	}

	public Restaurant(String name, String address, String city, String phoneNumber, String logoUri, String uri) {
		this(null, name, address, city, phoneNumber, logoUri, uri);
	}

	public Restaurant(Long id, String name, String address, String city, String phoneNumber, String logoUri,
			String uri) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.city = city;
		this.phoneNumber = phoneNumber;
		this.logoUri = logoUri;
		this.uri = uri;
	}

	public void addReview(Review review) {
		getReviews().add(review);
		review.setRestaurant(this);
	}

}
