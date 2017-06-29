package com.yelp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yelp.domain.Restaurant;
import com.yelp.repository.RestaurantRepository;

@Transactional(readOnly = true)
@Service
public class DefaultRestaurantService implements RestaurantService {

	private final RestaurantRepository repository;

	@Autowired
	public DefaultRestaurantService(RestaurantRepository repository) {
		this.repository = repository;
	}

	@Override
	public Restaurant findById(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Could not find Restaurant with id [" + id + "]"));
	}

	@Override
	public List<Restaurant> findByNameIgnoreCaseContaining(String searchName) {
		return repository.findByNameIgnoreCaseContaining(searchName);
	}

	@Override
	public List<Restaurant> findAll() {
		return repository.findAll();
	}

}
