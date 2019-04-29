package com.screenmedia.demo.config;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.screenmedia.demo.repository.FuelPricesDataRepository;
import com.screenmedia.demo.repository.impl.FuelPricesDataRepositoryImpl;
import com.screenmedia.demo.service.JourneyCostEstimateService;
import com.screenmedia.demo.service.impl.JourneyCostEstimateServiceImpl;


@Configuration
@EnableScheduling
public class JourneyCostEstimateConfig {

	@Bean
	@Primary
	public JourneyCostEstimateService journeyCostEstimateService() {
		return new JourneyCostEstimateServiceImpl();
	}

	@Bean
	public FuelPricesDataRepository fuelPricesDataRepository() {
		return new FuelPricesDataRepositoryImpl();
	}

	@PostConstruct
	public void loadFuelPrices() {
		fuelPricesDataRepository().loadFuelPricesData();
	}

}