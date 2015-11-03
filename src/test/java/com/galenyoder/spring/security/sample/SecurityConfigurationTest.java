package com.galenyoder.spring.security.sample;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.net.URI;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
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
		//@formatter:off
		mvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity())
				.build();
		//@formatter:on
	}

	@Test
	public void thatSecureEndpointRequiresAuthentication() throws Exception {
		mvc.perform(get(URI.create("/secure")))
				.andExpect(MockMvcResultMatchers.status().isUnauthorized());
	}

	@Test
	public void thatSecureEndpointCanBeAccessedUsingBasicAuthentication()
			throws Exception {
		//@formatter:off
		mvc.perform(
				get(URI.create("/secure"))
					.with(httpBasic("bob", "bobpass"))
					.with(csrf()))
				.andExpect(status().is2xxSuccessful());
		//@formatter:on
	}

}