package com.screenmedia.demo.web;

import java.math.BigDecimal;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.screenmedia.demo.data.JourneyCostEstimation;
import com.screenmedia.demo.service.JourneyCostEstimateService;

@RestController
public class JourneyCostEstimateController {

	Logger logger = LoggerFactory.getLogger(JourneyCostEstimateController.class);

	@Autowired
	private JourneyCostEstimateService journeyCostEstimateService;

	@GetMapping(value = "/journeycost/{date}/{fuelType}/{mpg}/{mileage}", produces = MediaType.APPLICATION_JSON_VALUE)
	public JourneyCostEstimation getFuelCostEstimate(
			@PathVariable("date") @DateTimeFormat(pattern = "ddMMyyyy") Date date,
			@PathVariable("fuelType") String fuelType, @PathVariable("mpg") BigDecimal mpg,
			@PathVariable("mileage") BigDecimal mileage) {

		logger.debug("Request received to find fuel cost for journey on {} for fuel {} for mpg {} and mileage {}", date,
				fuelType, mpg, mileage);
		return journeyCostEstimateService.findFuelCostForJourney(date, fuelType, mpg, mileage);
	}

}
