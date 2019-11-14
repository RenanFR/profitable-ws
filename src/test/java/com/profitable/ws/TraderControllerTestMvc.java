package com.profitable.ws;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.profitable.ws.service.ExchangeAccountService;
import com.profitable.ws.service.impl.TraderService;

@RunWith(SpringRunner.class)
@WebMvcTest
public class TraderControllerTestMvc {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private TraderService traderService;
	
	@MockBean
	private ExchangeAccountService exchangeService;
	
	private String token;

	@Before
	public void authenticate() throws Exception {
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
		parameters.add("username", "renanfr1047@gmail.com");
		parameters.add("password", "pass");
		parameters.add("grant_type", "password");
		parameters.add("client_id", "client");
		ResultActions result = this.mvc
			.perform(post("/oauth/token")
					.header("Content-Type", "application/x-www-form-urlencoded")
					.params(parameters)
					.with(httpBasic("client", "secret"))
					.accept("application/json;charset=UTF-8"))
			.andExpect(status().isOk())
			.andExpect(content().contentType("application/json;charset=UTF-8"));
		token = new JacksonJsonParser().parseMap(result.andReturn().getResponse().getContentAsString()).get("access_token").toString();
	}
	
	@Test
	public void balanceUnauthorized() throws Exception {
		this.mvc
			.perform(get("/traders/1/wallet/balance")).andDo(print()).andExpect(status().isUnauthorized());
	}

}
