package com.screenmedia.demo.service;

import java.math.BigDecimal;
import java.util.Date;

import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.screenmedia.demo.data.FuelPricesDataForDate;
import com.screenmedia.demo.data.JourneyCostEstimation;
import com.screenmedia.demo.exception.FuelRatesNotAvailableException;
import com.screenmedia.demo.exception.InvalidRequestParamsException;
import com.screenmedia.demo.repository.FuelPricesDataRepository;
import com.screenmedia.demo.service.impl.JourneyCostEstimateServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { JourneyCostEstimateServiceImpl.class })
public class JourneyCostEstimateServiceTest {
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@MockBean
	private FuelPricesDataRepository fuelPricesDataRepository;

	@Autowired
	private JourneyCostEstimateService test;

	@Test
	public void testWhenNoDataAvailable() {

		Mockito.when(fuelPricesDataRepository.getFuelPricesByDate(Mockito.any()))
				.thenThrow(FuelRatesNotAvailableException.class);
		Assertions.assertThat(test).isNotNull();
		thrown.expect(FuelRatesNotAvailableException.class);
		test.findFuelCostForJourney(new Date(), "petrol", BigDecimal.ONE, BigDecimal.ONE);

	}

	@Test
	public void testWhenMatchingDataAvailable() {

		FuelPricesDataForDate prices = new FuelPricesDataForDate(new Date(), BigDecimal.valueOf(125.25),
				BigDecimal.valueOf(115.15), BigDecimal.valueOf(55.25), BigDecimal.valueOf(50.50));

		Mockito.when(fuelPricesDataRepository.getFuelPricesByDate(Mockito.any())).thenReturn(prices);

		// Data available for the given date
		JourneyCostEstimation result = test.findFuelCostForJourney(new Date(), "petrol", BigDecimal.ONE,
				BigDecimal.ONE);
		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result.getFuelCostOfTheJourney()).isEqualTo("£5.69");
		Assertions.assertThat(result.getDutyPaidForJourney()).isEqualTo("£2.51");

	}

	@Test
	public void testWhenInvalidInputsSupplied() {

		FuelPricesDataForDate prices = new FuelPricesDataForDate(new Date(), BigDecimal.valueOf(125.25),
				BigDecimal.valueOf(115.15), BigDecimal.valueOf(55.25), BigDecimal.valueOf(50.50));

		Mockito.when(fuelPricesDataRepository.getFuelPricesByDate(Mockito.any())).thenReturn(prices);

		thrown.expect(InvalidRequestParamsException.class);
		test.findFuelCostForJourney(new Date(), "petrol", BigDecimal.ZERO, BigDecimal.ZERO);

	}

	// TODO: Add more tests here

}
