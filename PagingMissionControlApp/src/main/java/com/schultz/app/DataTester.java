package com.schultz.app;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


/**
 * 
 * @author Morris Schultz
 * 
 * This class is built to test the data and creates/prints an alarm if violation conditions are met.
 *
 */

public class DataTester {

	public void checkForAlarms (Map<String, ArrayList<TelemetryData>> dataMap) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonArray jsonArray = new JsonArray();
		for (String key : dataMap.keySet()) { //all data has been broken into 4 types based on SatId and Component
			TelemetryData baseLineData = null;
			Date fiveMinutesLater = null; // five minutes after the baseline
			int count = 0; //keeps track of how many "red" we get
			boolean searching = true; //when we are done searching through a set of data, we will set to false
			while (searching) { //using this to check after a 'red' is found to be too long after initial red
								//if all data will be within 5 minute windows (per file), we can remove this
				for (TelemetryData data : dataMap.get(key)) { 
					if (data.isRedHigh() || data.isRedLow()) { //if this is red
						baseLineData = (baseLineData == null) ? data : baseLineData; //make sure baseline is set
						fiveMinutesLater = makeFiveMinutesLater(data.timestamp); //setup the 5 minute window
						if (fiveMinutesLater.after(data.getTimestamp())) { //we assume all data is in chronological order
							count ++; //add to the red count. This includes the initial baseline red
							if (count > 2) {
								jsonArray.add(makeJsonObject(baseLineData)); //add the baseline to the output to create alarm
								searching = false; //we can stop searching this list, it has met the criteria
								break; //make sure we don't accidently hit the reset in the else statement below and protects from too many JsonObjects added
							}
						} else {
							dataMap.get(key).remove(baseLineData); //we remove the old data before we go over this list again
							count = 0; //reset the count to 0
							baseLineData = null; //set the baseline to null to start over
						}
					}
					if (dataMap.get(key).indexOf(data) + 1 == dataMap.get(key).size()) { //are we on last in the list
						searching = false; //stop searching this list
					}
				}
			}			
		}
		System.out.println(gson.toJson(jsonArray)); //print the alerts
	}
	
	/**
	 * This is used when an alarm is created
	 * 
	 * @param data
	 * @return JsonObject
	 */
	private JsonObject makeJsonObject(TelemetryData data) {
		
		JsonObject json = new JsonObject();
		
		json.addProperty("satelliteId", data.getSatelliteId());
		
		String severity = ((data.isRedHigh()) ? "RED HIGH" : "RED LOW");
		json.addProperty("severity", severity);
		
		json.addProperty("component", data.getComponent());
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		json.addProperty("timestamp", df.format(data.getTimestamp()));
		
		return json;
	}
	
	private Date makeFiveMinutesLater(Date start) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(start);
		cal.add(GregorianCalendar.MINUTE, 5);
		return cal.getTime();
	}
	
}
