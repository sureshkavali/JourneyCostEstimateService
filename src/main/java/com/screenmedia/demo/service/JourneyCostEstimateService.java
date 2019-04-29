package com.screenmedia.demo.service;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.screenmedia.demo.data.JourneyCostEstimation;

@Service
public interface JourneyCostEstimateService {

	public JourneyCostEstimation findFuelCostForJourney(Date date, String fuelType, BigDecimal mpg, BigDecimal mileage);

}
