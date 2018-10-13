package it.discovery.order.controller;

import it.discovery.order.OrderApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitWebConfig(OrderApplication.class)
@AutoConfigureMockMvc
/*@TestPropertySource(properties = {
		"book.service.url=http://localhost:8080/book",
})*/
@TestPropertySource(locations="classpath:application.properties")
public class OrderControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void makeOrder_bookExists_orderCreated() throws Exception {
		//given
		//when
		ResultActions actions = mockMvc.perform(
				post("/order/1"));
		//then
		actions.andExpect(status().isCreated());
	}
}
