package com.search.drivingschool;

import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import com.search.drivingschool.Handler.DrivingCustomSearchHandler;
import com.search.drivingschool.command.DrivingSchoolCommand;
import com.search.drivingschool.data.Response;
import com.search.drivingschool.service.DrivingSchoolSearchService;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import wiremock.org.apache.http.impl.client.CloseableHttpClient;
import wiremock.org.apache.http.impl.client.HttpClients;

import java.io.InputStreamReader;
import java.io.Reader;
import org.apache.commons.io.IOUtils;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

//@RunWith(SpringRunner.class)
//@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DrivingschoolApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class DrivingschoolApplicationTest {

	@Test
	public void contextLoads() {
	}



	@Autowired
	private DrivingSchoolSearchService drivingSchoolSerchService;

	@Autowired
	private DrivingSchoolCommand drivingSchoolCommand;

	@Autowired
	private DrivingCustomSearchHandler drivingCustomSearchHandler;

	private static final int WIREMOCK_PORT = 9999;
	private static final String GOOGLE_RESPONSE= "/mocks/googleResponse/GoogleResponse_%s.json";
	private static final String PROCESSED_RESPONSE= "/mocks/processedResponse/GoogleResponse_%s.json";

	private static final String LOCAL_ORDER_SUMMARY_URL = "/drivingschool";
	private static final String LOCAL_OAUTH_URL = "/oauth";
	private static final String OAUTH_RESPONSE = "/mocks/oauth/oauth.json";

	@ClassRule
	public static WireMockClassRule wireMockRule = new WireMockClassRule(WIREMOCK_PORT);

	CloseableHttpClient httpClient;
	String baseURL = "";


	@Before
	public void setup(){
		baseURL = "http://localhost:"+WIREMOCK_PORT;
		httpClient = HttpClients.createDefault();
	}

	@Ignore
	@Test
	public void when_DrivingSchool_GetDetails_Parramatta_ReturnOrder_ShouldRespondWith200() throws Exception {
		String suburb = "Parramatta";

		String sUrl = LOCAL_ORDER_SUMMARY_URL;

		Reader reader = new InputStreamReader(DrivingschoolApplicationTest.class.getResourceAsStream(String.format(GOOGLE_RESPONSE, suburb)));
		stubFor(get(urlPathEqualTo(sUrl))
				.willReturn(aResponse()
						.withStatus(200)
						.withHeader("Content-Type", "application/json")
						.withBody(IOUtils.toString(reader))));

		Response msr = drivingSchoolSerchService.getDetails(suburb,"1");

		assertNotNull(msr);


	}
    @Ignore
    @Test
    public void when_DrivingSchool_GetDetails_Parramatta_Verify_Back_And_Next() throws Exception {
        String suburb = "Parramatta";

        String sUrl = LOCAL_ORDER_SUMMARY_URL;

        Reader reader = new InputStreamReader(DrivingschoolApplicationTest.class.getResourceAsStream(String.format(GOOGLE_RESPONSE, suburb)));
        stubFor(get(urlPathEqualTo(sUrl))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(IOUtils.toString(reader))));

        Response msr = drivingSchoolSerchService.getDetails(suburb,"1");

        assertNotNull(msr);


    }


}
