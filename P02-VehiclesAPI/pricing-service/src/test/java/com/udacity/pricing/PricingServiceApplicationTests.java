package com.udacity.pricing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URI;
import java.net.URISyntaxException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PricingServiceApplicationTests {

	@Autowired
	private MockMvc mvc;

	@Test
	public void contextLoads() {
	}

	/**
	 * Add an additional test to check whether the application appropriately
	 * generates a price for a given vehicle ID
	 */
	@Test
	public void generatePriceForId() throws Exception {

		// String result = mvc.perform(get(new URI("/prices/1"))).andReturn().getResponse().getContentAsString();

		// Test for microservice endpoint
		mvc.perform(get(new URI("/prices/1")))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.currency").value("USD"))
				.andExpect(jsonPath("$.price").value(10000.0));


		// Test for known vehicleId when hitting controller endpoint
		mvc.perform(get(new URI("/services/price?vehicleId=1")))
				.andExpect(status().isOk());

		// Test for unknown vehicleId when hitting controller endpoint
		mvc.perform(get(new URI("/services/price?vehicleId=999")))
				.andExpect(status().isNotFound());
	}

}