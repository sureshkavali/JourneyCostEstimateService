package com.screenmedia.demo.web;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.screenmedia.demo.JourneyCostEstimateServiceApplication;
import com.screenmedia.demo.data.ErrorResponse;
import com.screenmedia.demo.data.JourneyCostEstimation;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JourneyCostEstimateServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JourneyCostEstimateControllerIntTest {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@LocalServerPort
	private int port;

	@Test
	public void whenRequestedEstimatedFuelCost() throws Exception {

		HttpEntity<String> headers = new HttpEntity<>(null, new HttpHeaders());

		ResponseEntity<JourneyCostEstimation> response = testRestTemplate.exchange(getBaseUri() + "/10042019/petrol/10/10",
				HttpMethod.GET, headers, JourneyCostEstimation.class);

		Assertions.assertThat(response).isNotNull();
		JourneyCostEstimation responseEntity = response.getBody();
		Assertions.assertThat(responseEntity).isNotNull();
		Assertions.assertThat(responseEntity.getFuelCostOfTheJourney()).isEqualTo("£5.58");
		Assertions.assertThat(responseEntity.getDutyPaidForJourney()).isEqualTo("£2.63");
		Assertions.assertThat(responseEntity.getJourneyCostSummaryMsg())
				.isEqualTo("The journey cost is 12p more expensive if the journey would be today");

		// TODO: Extra care needs to be taken with the last assertion as the cost
		// difference will change as the time progress. It can be done by setting
		// current date to a fixed date
	}

	@Test
	public void whenRequestedWithInvalidData() throws Exception {

		HttpEntity<String> headers = new HttpEntity<>(null, new HttpHeaders());

		ResponseEntity<ErrorResponse> response = testRestTemplate.exchange(getBaseUri() + "/10042019/petrol/0/0",
				HttpMethod.GET, headers, ErrorResponse.class);

		Assertions.assertThat(response).isNotNull();
		ErrorResponse responseEntity = response.getBody();
		Assertions.assertThat(responseEntity).isNotNull();
		Assertions.assertThat(responseEntity.getCode()).isEqualTo(400);
		Assertions.assertThat(responseEntity.getMessage()).isEqualTo("Ivalid value supplied for mpg");
	}

	private String getBaseUri() {
		return "http://localhost:" + port + "/journeycost";
	}
}
