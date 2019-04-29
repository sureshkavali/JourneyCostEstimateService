package com.screenmedia.demo.data;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FuelPricesDataForDate {

	@JsonProperty(required = true, value = "Date")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date fuelRateEffectiveFrom;
	@JsonProperty(required = true, value = "ULSP")
	private BigDecimal ulspPrice;
	@JsonProperty(required = true, value = "ULSD")
	private BigDecimal ulsdPrice;
	@JsonProperty(required = true, value = "ULSP_DUTY")
	private BigDecimal dutyOnUlsp;
	@JsonProperty(required = true, value = "ULSD_DUTY")
	private BigDecimal dutyOnUlsd;
	@JsonProperty(required = true, value = "ULSP_VAT")
	private double vatOnUlsp;
	@JsonProperty(required = true, value = "ULSD_VAT")
	private double vatOnUlsd;
	@JsonProperty(value = "ADD_DUTY_EFF_FROM_DESC")
	private String additionalDutyEffectiveFrom;
	@JsonProperty(value = "ADD_DUTY")
	private BigDecimal additionalFuelDuty;

	public FuelPricesDataForDate() {
	}

	// Test purpose
	public FuelPricesDataForDate(Date fuelRateEffectiveFrom, BigDecimal ulspPrice, BigDecimal ulsdPrice,
			BigDecimal dutyOnUlsp, BigDecimal dutyOnUlsd) {
		super();
		this.fuelRateEffectiveFrom = fuelRateEffectiveFrom;
		this.ulspPrice = ulspPrice;
		this.ulsdPrice = ulsdPrice;
		this.dutyOnUlsp = dutyOnUlsp;
		this.dutyOnUlsd = dutyOnUlsd;
	}

	public Date getFuelRateEffectiveFrom() {
		return fuelRateEffectiveFrom;
	}

	public BigDecimal getUlspPrice() {
		return ulspPrice;
	}

	public BigDecimal getUlsdPrice() {
		return ulsdPrice;
	}

	public BigDecimal getDutyOnUlsp() {
		return dutyOnUlsp;
	}

	public BigDecimal getDutyOnUlsd() {
		return dutyOnUlsd;
	}

	public double getVatOnUlsp() {
		return vatOnUlsp;
	}

	public double getVatOnUlsd() {
		return vatOnUlsd;
	}

	public String getAdditionalDutyEffectiveFrom() {
		return additionalDutyEffectiveFrom;
	}

	public BigDecimal getAdditionalFuelDuty() {
		return additionalFuelDuty;
	}

	@Override
	public String toString() {
		return " " + additionalDutyEffectiveFrom + " " + ulspPrice + " " + ulsdPrice + " " + dutyOnUlsp + " "
				+ dutyOnUlsd + " " + additionalFuelDuty;
	}
}
