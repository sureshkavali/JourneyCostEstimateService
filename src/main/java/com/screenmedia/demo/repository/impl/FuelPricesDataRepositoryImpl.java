package com.screenmedia.demo.repository.impl;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Scheduled;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.screenmedia.demo.data.FuelPricesDataForDate;
import com.screenmedia.demo.exception.FuelRatesNotAvailableException;
import com.screenmedia.demo.repository.FuelPricesDataRepository;

public class FuelPricesDataRepositoryImpl implements FuelPricesDataRepository {

	Logger logger = LoggerFactory.getLogger(FuelPricesDataRepositoryImpl.class);

	@Value("${fuel.prices.data}")
	private String fuelPricesFile;

	private TreeMap<Date, FuelPricesDataForDate> fuelPrices = new TreeMap<>();

	@Override
	public FuelPricesDataForDate getFuelPricesByDate(Date journeyDate) {

		if (fuelPrices.isEmpty()) {
			// Action needed as fuel prices map is empty
			logger.error("Server Error: Fuel prices not available - please contact administrator");
			throw new FuelRatesNotAvailableException("Fuel prices not available");
		}

		if (journeyDate.before(fuelPrices.firstKey())) {
			logger.error("Error: Fuel prices not available for the journey date supplied");
			throw new FuelRatesNotAvailableException("Fuel prices not available for the journey date supplied");
		}

		return fuelPrices.get(fuelPrices.headMap(journeyDate, true).lastKey());
	}

	@Scheduled(cron = "${fuel.prices.reload.frequency}")
	public void loadFuelPricesData() {
		logger.info("Processing Fuel prices from CSV fiel..");

		// Create CSV schema with one header line - the csv file will be modified
		// accordingly
		CsvSchema schema = CsvSchema.emptySchema().withHeader();
		CsvMapper mapper = new CsvMapper();

		try {
			File file = new ClassPathResource(fuelPricesFile).getFile();
			MappingIterator<FuelPricesDataForDate> data = mapper.readerWithTypedSchemaFor(FuelPricesDataForDate.class)
					.with(schema).readValues(file);
			List<FuelPricesDataForDate> list = data.readAll();

			// Load the fuel prices
			// Not clearing them because the new prices override the existing one
			list.stream().forEach(element -> fuelPrices.put(element.getFuelRateEffectiveFrom(), element));
		} catch (IOException ioEx) {
			logger.error("ERROR: Error while reloading Fuel prices", ioEx);
		}

	}

}
