package com.search.drivingschool;

import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import wiremock.org.apache.http.impl.client.CloseableHttpClient;
import wiremock.org.apache.http.impl.client.HttpClients;

import static org.junit.Assert.assertEquals;

//@RunWith(SpringRunner.class)
//@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DrivingschoolApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class DrivingschoolApplicationTests {

	@Test
	public void contextLoads() {
	}

	private static final int WIREMOCK_PORT = 9999;

	@ClassRule
	public static WireMockClassRule wireMockRule = new WireMockClassRule(WIREMOCK_PORT);

	CloseableHttpClient httpClient;
	String baseURL = "";


	@Before
	public void setup(){
		baseURL = "http://localhost:"+WIREMOCK_PORT;
		httpClient = HttpClients.createDefault();
	}




}
