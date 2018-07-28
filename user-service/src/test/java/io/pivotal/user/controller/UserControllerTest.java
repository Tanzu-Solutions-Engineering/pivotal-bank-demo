package io.pivotal.user.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.pivotal.user.configuration.ServiceTestConfiguration;
import io.pivotal.user.controller.UserController;
import io.pivotal.user.service.UserService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * Tests for the UserController.
 * @author David Ferreira Pinto
 *
 */
public class UserControllerTest {
	private static String API_ROLE = "API_USER";
	MockMvc mockMvc;

	@InjectMocks
	UserController controller;

	@Mock
	UserService service;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

		this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Before
	public void login() {
		/*Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		grantedAuthorities.add(new SimpleGrantedAuthority(API_ROLE));
		UserDetails user = new CustomUser(ServiceTestConfiguration.USER_ID,
				ServiceTestConfiguration.PASSWORD, grantedAuthorities,
				ServiceTestConfiguration.PROFILE_ID,
				ServiceTestConfiguration.AUTH_TOKEN);
		Authentication authentication = new TestingAuthenticationToken(user,
				ServiceTestConfiguration.PASSWORD,
				(List<GrantedAuthority>) grantedAuthorities);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		*/
	}

	@After
	public void logout() {
		//SecurityContextHolder.clearContext();
	}

	/**
	 * Test the POST to <code>/account</code>.
	 * test creation of accounts.
	 * @throws Exception
	 */
	@Test
	public void doPostAccount() throws Exception {
		when(service.saveUser(ServiceTestConfiguration.user()))
				.thenReturn(ServiceTestConfiguration.PROFILE_ID);


		mockMvc.perform(
				post("/users").contentType(MediaType.APPLICATION_JSON)
						.content(
								convertObjectToJson(ServiceTestConfiguration
										.user())))
				.andExpect(status().isCreated()).andDo(print());
	}

	/**
	 * Test the GET to <code>/account</code>.
	 * test retrieval of accounts.
	 * @throws Exception
	 */
	@Test
	public void doGetAccount() throws Exception {
		when(service.findUser(ServiceTestConfiguration.USER_ID))
				.thenReturn(ServiceTestConfiguration.user());

		mockMvc.perform(
				get("/users/" + ServiceTestConfiguration.USER_ID)
						.contentType(MediaType.APPLICATION_JSON).content(
								convertObjectToJson(ServiceTestConfiguration
										.user())))
				.andExpect(status().isOk())
				.andDo(print())
				.andExpect(
						content().contentTypeCompatibleWith(
								MediaType.APPLICATION_JSON))
				.andExpect(
						jsonPath("$.id").value(
								ServiceTestConfiguration.PROFILE_ID))
				.andExpect(
						jsonPath("$.creationdate").value(
								ServiceTestConfiguration.ACCOUNT_DATE.getTime()))
				.andExpect(
						jsonPath("$.logoutcount").value(
								ServiceTestConfiguration.LOGOUT_COUNT
										.intValue()))
				.andExpect(
						jsonPath("$.lastlogin").value(
								ServiceTestConfiguration.ACCOUNT_DATE.getTime()))
				.andExpect(
						jsonPath("$.logincount").value(
								ServiceTestConfiguration.LOGIN_COUNT))
				.andDo(print());
	}
	
	private String convertObjectToJson(Object request) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		return mapper.writeValueAsString(request);
	}
}
