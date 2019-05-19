package com.schultz.app;

import java.util.Date;

/**
 * 
 * @author Morris Schultz
 *
 */

public class TelemetryData {
	
	public TelemetryData(Date timestamp, String satelliteId, int redHighLimit, int redLowLimit, int yellowHighLimit,
			int yellowLowLimit, double rawValue, String component) {
		super();
		this.timestamp = timestamp;
		this.satelliteId = satelliteId;
		this.redHighLimit = redHighLimit;
		this.redLowLimit = redLowLimit;
		this.yellowHighLimit = yellowHighLimit;
		this.yellowLowLimit = yellowLowLimit;
		this.rawValue = rawValue;
		this.component = component; 
	}

	Date timestamp;
	
	String satelliteId;
	String component;
	


	int redHighLimit;
	int redLowLimit;
	int yellowHighLimit;
	int yellowLowLimit;
	
	double rawValue;
	
	public boolean isRedHigh() {
		if (component.contentEquals("TSTAT")) {
			if (rawValue > redHighLimit) {
				return true;
			}
		}		
		return false;
	}
	
	public boolean isRedLow() {
		if (component.contentEquals("BATT")) {
			if (rawValue < redLowLimit) {
				return true;
			}
		}
		return false;
	}
	
	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getSatelliteId() {
		return satelliteId;
	}

	public void setSatelliteId(String satelliteId) {
		this.satelliteId = satelliteId;
	}

	public int getRedHighLimit() {
		return redHighLimit;
	}

	public void setRedHighLimit(int redHighLimit) {
		this.redHighLimit = redHighLimit;
	}

	public int getRedLowLimit() {
		return redLowLimit;
	}

	public void setRedLowLimit(int redLowLimit) {
		this.redLowLimit = redLowLimit;
	}

	public int getYellowHighLimit() {
		return yellowHighLimit;
	}

	public void setYellowHighLimit(int yellowHighLimit) {
		this.yellowHighLimit = yellowHighLimit;
	}

	public int getYellowLowLimit() {
		return yellowLowLimit;
	}

	public void setYellowLowLimit(int yellowLowLimit) {
		this.yellowLowLimit = yellowLowLimit;
	}

	public double getRawValue() {
		return rawValue;
	}

	public void setRawValue(double rawValue) {
		this.rawValue = rawValue;
	}
	
	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

}
