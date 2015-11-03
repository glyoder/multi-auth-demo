package com.galenyoder.spring.security.sample;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.net.URI;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MultiAuthDemoApplication.class)
@WebAppConfiguration
public class SecurityConfigurationTest {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;

	@Before
	public void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(context)
				.apply(springSecurity()).build();
	}

	@Test
	public void thatBasicSecureEndpointsRequiresAuthentication()
			throws Exception {
		mvc.perform(get(URI.create("/basic-secure")))
				.andExpect(MockMvcResultMatchers.status().isUnauthorized());
	}

	@Test
	public void thatBasicSecureEndpointCanBeAccessedUsingBasicAuthentication()
			throws Exception {
		mvc.perform(get(URI.create("/basic-secure"))
				.with(httpBasic("bob", "bobpass")).with(csrf()))
				.andExpect(status().is2xxSuccessful());
	}

	@Test
	public void thatCasSecureEndpointsRequiresAuthentication()
			throws Exception {
		mvc.perform(get(URI.create("/secure")))
				// redirects to login
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	@Test
	@WithMockUser(username = "someguy")
	public void thatCasSecureEndpointCanBeAccessedIfLoggedIn()
			throws Exception {
		mvc.perform(get(URI.create("/secure")).with(csrf()))
				.andExpect(status().is2xxSuccessful());
	}

}