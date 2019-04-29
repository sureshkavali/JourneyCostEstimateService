package com.screenmedia.demo.repository;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;
import java.util.TreeMap;

import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ReflectionUtils;

import com.screenmedia.demo.data.FuelPricesDataForDate;
import com.screenmedia.demo.exception.FuelRatesNotAvailableException;
import com.screenmedia.demo.repository.impl.FuelPricesDataRepositoryImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { FuelPricesDataRepositoryImpl.class })
public class FuelPricesDataRepositoryImplTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Autowired
	private FuelPricesDataRepository test;

	@Test
	public void testWhenNoDataAvailable() {

		Assertions.assertThat(test).isNotNull();
		thrown.expect(FuelRatesNotAvailableException.class);
		test.getFuelPricesByDate(new Date());

	}

	@Test
	public void testWhenNoMatchingDataAvailable() {

		Calendar date = Calendar.getInstance();
		date.set(2019, 4, 15);
		TreeMap<Date, FuelPricesDataForDate> fuelPrices = new TreeMap<>();
		fuelPrices.put(date.getTime(), new FuelPricesDataForDate());
		Field field = ReflectionUtils.findField(FuelPricesDataRepositoryImpl.class, "fuelPrices");
		ReflectionUtils.makeAccessible(field);
		ReflectionUtils.setField(field, test, fuelPrices);

		// No data available for the given date
		date.set(2019, 1, 1);
		thrown.expect(FuelRatesNotAvailableException.class);
		test.getFuelPricesByDate(date.getTime());
	}

	@Test
	public void testWhenMatchingDataAvailable() {

		Calendar date = Calendar.getInstance();
		date.set(2019, 4, 15);
		TreeMap<Date, FuelPricesDataForDate> fuelPrices = new TreeMap<>();
		fuelPrices.put(date.getTime(), new FuelPricesDataForDate());
		Field field = ReflectionUtils.findField(FuelPricesDataRepositoryImpl.class, "fuelPrices");
		ReflectionUtils.makeAccessible(field);
		ReflectionUtils.setField(field, test, fuelPrices);

		// Data available for the given date
		FuelPricesDataForDate result = test.getFuelPricesByDate(date.getTime());
		Assertions.assertThat(result).isNotNull();

	}

	// TODO: Add more tests to mock data and verify the data

}
