package com.screenmedia.demo.data;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JourneyCostEstimation implements Serializable {

	private static final long serialVersionUID = 1L;

	private String dutyPaidForJourney;

	private String fuelCostOfTheJourney;

	private String journeyCostSummaryMsg;

	@JsonIgnore
	private String todaysEstimatedDuty;

	@JsonIgnore
	private String todaysEstimatedFuelCost;

	public JourneyCostEstimation() {
	}

	public JourneyCostEstimation(Builder builder) {
		fuelCostOfTheJourney = builder.fuelCostOfTheJourney;
		dutyPaidForJourney = builder.dutyPaidForJourney;
		todaysEstimatedFuelCost = builder.todaysEstimatedFuelCost;
		todaysEstimatedDuty = builder.todaysEstimatedDuty;
		journeyCostSummaryMsg = builder.journeyCostSummaryMsg;
	}

	public String getDutyPaidForJourney() {
		return dutyPaidForJourney;
	}

	public String getFuelCostOfTheJourney() {
		return fuelCostOfTheJourney;
	}

	public String getJourneyCostSummaryMsg() {
		return journeyCostSummaryMsg;
	}

	public String getTodaysEstimatedDuty() {
		return todaysEstimatedDuty;
	}

	public String getTodaysEstimatedFuelCost() {
		return todaysEstimatedFuelCost;
	}

	public static class Builder {
		private String dutyPaidForJourney;

		private String fuelCostOfTheJourney;

		private String journeyCostSummaryMsg;

		private String todaysEstimatedDuty;

		private String todaysEstimatedFuelCost;

		public JourneyCostEstimation build() {
			return new JourneyCostEstimation(this);
		}

		public Builder dutyPaidForJourney(String dutyPaidForJourney) {
			this.dutyPaidForJourney = dutyPaidForJourney;
			return this;
		}

		public Builder estimatedFuelCostNow(String todaysEstimatedFuelCost) {
			this.todaysEstimatedFuelCost = todaysEstimatedFuelCost;
			return this;
		}

		public Builder fuelCostOfTheJourney(String fuelCostOfTheJourney) {
			this.fuelCostOfTheJourney = fuelCostOfTheJourney;
			return this;
		}

		public Builder journeyCostSummaryMsg(String journeyCostSummaryMsg) {
			this.journeyCostSummaryMsg = journeyCostSummaryMsg;
			return this;
		}

		public Builder todaysEstimatedDuty(String todaysEstimatedDuty) {
			this.todaysEstimatedDuty = todaysEstimatedDuty;
			return this;
		}
	}

}
