package com.yelp.web;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.yelp.repository.RestaurantRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc(print = MockMvcPrint.SYSTEM_ERR)
@Transactional
@Sql("/test-data.sql")
public class RestRestaurantControllerTests {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	RestaurantRepository repository;

	@Test
	public void retrieveAllRestaurants() throws Exception {
		mockMvc.perform(get("/restaurants").accept(APPLICATION_JSON))//
				.andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))//
				.andExpect(status().isOk())//
				.andExpect(jsonPath("$[0]").exists())//
				.andExpect(jsonPath("$[1]").exists())//
				.andExpect(jsonPath("$[2]").doesNotExist())//
				// phone number of restaurant whose name starts with "R"
				.andExpect(jsonPath("$[?(@.name =~ /R.+/)].phoneNumber", hasItems("043 443 59 89")))//
				.andExpect(jsonPath("$[0].city", is("Zurich")));
	}

	@Test
	public void retrieveRestaurant() throws Exception {
		String id = "1";
		mockMvc.perform(get("/restaurants/{id}", id).accept(APPLICATION_JSON))//
				.andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))//
				.andExpect(status().isOk())//
				.andExpect(jsonPath("$.address", is("Ludretikonerstrasse 21")))//
				.andExpect(jsonPath("$.city", is("Zurich")))//
				.andExpect(jsonPath("$.logoUri", is("logo")))//
				.andExpect(jsonPath("$.name", is("RuenThai")))//
				.andExpect(jsonPath("$.phoneNumber", is("043 443 59 89")))//
				.andExpect(jsonPath("$.uri", is("http")))//
				.andExpect(jsonPath("$.reviews").doesNotExist());
	}

	@Test
	public void retrieveRestaurantByName() throws Exception {
		String name = "thai";
		mockMvc.perform(get("/restaurants/search?name={name}", name).accept(APPLICATION_JSON))//
				.andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))//
				.andExpect(status().isOk())//
				.andExpect(jsonPath("$[0].id", is(1)))//
				.andExpect(jsonPath("$[0].address", is("Ludretikonerstrasse 21")))//
				.andExpect(jsonPath("$[0].city", is("Zurich")))//
				.andExpect(jsonPath("$[0].logoUri", is("logo")))//
				.andExpect(jsonPath("$[0].name", is("RuenThai")))//
				.andExpect(jsonPath("$[0].phoneNumber", is("043 443 59 89")))//
				.andExpect(jsonPath("$[0].uri", is("http")))//
				.andExpect(jsonPath("$[0].reviews").doesNotExist());
	}

}
