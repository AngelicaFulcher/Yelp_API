package com.yelp.service;

import java.util.List;

import com.yelp.domain.Restaurant;

public interface RestaurantService {

	Restaurant findById(Long id);

	List<Restaurant> findByNameIgnoreCaseContaining(String searchName);

	List<Restaurant> findAll();
}
