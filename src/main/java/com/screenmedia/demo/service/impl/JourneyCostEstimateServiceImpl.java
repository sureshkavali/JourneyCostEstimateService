package com.screenmedia.demo.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.screenmedia.demo.data.FuelPricesDataForDate;
import com.screenmedia.demo.data.JourneyCostEstimation;
import com.screenmedia.demo.exception.InvalidRequestParamsException;
import com.screenmedia.demo.helper.CurrencyUtil;
import com.screenmedia.demo.repository.FuelPricesDataRepository;
import com.screenmedia.demo.service.JourneyCostEstimateService;

public class JourneyCostEstimateServiceImpl implements JourneyCostEstimateService {

	Logger logger = LoggerFactory.getLogger(JourneyCostEstimateServiceImpl.class);

	@Autowired
	private FuelPricesDataRepository fuelPriceDataRepository;

	private static final BigDecimal GAL_TO_LTR = BigDecimal.valueOf(4.54609);

	public JourneyCostEstimation findFuelCostForJourney(Date date, String fuelType, BigDecimal mpg,
			BigDecimal mileage) {

		logger.debug("Get fuel cost for journey on {} for fuel {} for mpg {} and mileage {}", date, fuelType, mpg,
				mileage);

		if (BigDecimal.ZERO.compareTo(mpg) >= 0) {
			logger.error("Invalid value supplied for MPG");
			throw new InvalidRequestParamsException("Ivalid value supplied for mpg");
		}

		if (BigDecimal.ZERO.compareTo(mileage) > 0) {
			logger.error("Invalid value supplied for mileage");
			throw new InvalidRequestParamsException("Ivalid value supplied for mileage");
		}

		FuelPricesDataForDate fuelPriceDataForDate = fuelPriceDataRepository.getFuelPricesByDate(date);

		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 0);
		FuelPricesDataForDate fuelPriceDataForToday = fuelPriceDataRepository.getFuelPricesByDate(today.getTime());

		BigDecimal journeyFuelCost;
		BigDecimal dutyPaidOnjourney;

		BigDecimal todaysJourneyFuelCost;

		switch (fuelType) {
		case "diesel":
			journeyFuelCost = mileage.divide(mpg).multiply(GAL_TO_LTR).multiply(fuelPriceDataForDate.getUlsdPrice());
			dutyPaidOnjourney = mileage.divide(mpg).multiply(GAL_TO_LTR).multiply(fuelPriceDataForDate.getDutyOnUlsd());
			todaysJourneyFuelCost = mileage.divide(mpg).multiply(GAL_TO_LTR)
					.multiply(fuelPriceDataForToday.getUlsdPrice());
			break;
		case "petrol":
			journeyFuelCost = mileage.divide(mpg).multiply(GAL_TO_LTR).multiply(fuelPriceDataForDate.getUlspPrice());
			dutyPaidOnjourney = mileage.divide(mpg).multiply(GAL_TO_LTR).multiply(fuelPriceDataForDate.getDutyOnUlsp());
			todaysJourneyFuelCost = mileage.divide(mpg).multiply(GAL_TO_LTR)
					.multiply(fuelPriceDataForToday.getUlspPrice());
			break;
		default:
			logger.error("Invalid fuel type {} supplied. The supported fuel types are 'petrol' and 'diesel", fuelType);
			throw new InvalidRequestParamsException(
					"Invalid fuel type supplied. The supported fuel types are 'petrol' and 'diesel'");
		}

		String summaryMessage = "The journey cost is %s %s if the journey would be today";
		String message = "Have a nice trip!";

		if (journeyFuelCost.compareTo(todaysJourneyFuelCost) == 0) {
			// do nothing - no message
		} else if (journeyFuelCost.compareTo(todaysJourneyFuelCost) == 1) {
			message = String.format(summaryMessage,
					CurrencyUtil.formatMonetaryValue(journeyFuelCost.subtract(todaysJourneyFuelCost)), "cheaper");
		} else {
			message = String.format(summaryMessage,
					CurrencyUtil.formatMonetaryValue(todaysJourneyFuelCost.subtract(journeyFuelCost)),
					"more expensive");
		}

		logger.debug(
				"Got estimated fule cost - For the given journey the fuel costs {} and if the journey would be today, then the cost would be {}",
				journeyFuelCost, todaysJourneyFuelCost);

		return new JourneyCostEstimation.Builder()
				.fuelCostOfTheJourney(CurrencyUtil.formatMonetaryValue(journeyFuelCost))
				.dutyPaidForJourney(CurrencyUtil.formatMonetaryValue(dutyPaidOnjourney)).journeyCostSummaryMsg(message)
				.build();
	}

}
