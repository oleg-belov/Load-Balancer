package it.discovery.order.controller;

import com.obelov.balancer.LoadBalancer;
import it.discovery.order.OrderApplication;
import it.discovery.order.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@SpringJUnitWebConfig(OrderApplication.class)
@AutoConfigureWebClient
@SpringBootTest
@AutoConfigureJsonTesters
@TestPropertySource(locations="classpath:application.yml")
public class OrderControllerMockTest {

	private MockRestServiceServer mockBookServer;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private OrderController orderController;

	@Autowired
	private JacksonTester<Map> jacksonTester;

	@Autowired
	private LoadBalancer loadBalancer;

	@BeforeEach
	void setup() {
		mockBookServer = MockRestServiceServer.createServer(restTemplate);
	}

	@Test
	void makeOrder_bookExists_orderCreated() throws Exception {
		//given
		int bookId = 1;
		double price = 2000;
		Map<String, Object> book = new HashMap<>();
		book.put("price", price);
		book.put("id", bookId);
		mockBookServer.expect(requestTo(
				loadBalancer.getServer().getUrl() + "book/" + bookId))
				.andRespond(withSuccess(
						jacksonTester.write(book).getJson(),
						MediaType.APPLICATION_JSON_UTF8));
		//when
		Order order = orderController.makeOrder(bookId);
		//then
		mockBookServer.verify();
		assertNotNull(order);
		assertEquals(bookId, order.getBookId());
		assertEquals(price, order.getPrice());
	}
}