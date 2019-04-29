package com.screenmedia.demo.repository;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.screenmedia.demo.data.FuelPricesDataForDate;

@Component
public interface FuelPricesDataRepository {

	public FuelPricesDataForDate getFuelPricesByDate(Date journeyDate);

	public void loadFuelPricesData();

}
