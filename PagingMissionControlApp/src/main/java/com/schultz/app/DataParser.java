package com.schultz.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Morris Schultz
 *
 *<br>
 * @description
 *	This class will load and read a file, parse that file, and sort the data into a Map of Telemetry data
 * that meets Red Limit criteria
 *
 */

public class DataParser {
	
	private final static SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd HH:mm:ss.SSS");
	
	public Map<String, ArrayList<TelemetryData>> loadAndReadFile(String fileName) {
		
		File file = null;
		BufferedReader br = null;
		FileReader reader = null;
		try {
			Map<String, ArrayList<TelemetryData>> map = new HashMap<String, ArrayList<TelemetryData>>();
			
			file = getFileFromResources(fileName);
			reader = new FileReader(file);
			br = new BufferedReader(reader);
			
			String line = null;

			while ((line = br.readLine()) != null) {
				TelemetryData data = parseLine(line);
				if (data != null) {
					String key = data.getSatelliteId() + "_" + data.getComponent(); //use the ID and Component as the Key
					ArrayList<TelemetryData> list = map.get(key);
					if (list == null) {
						list = new ArrayList<TelemetryData>();
						map.put(key, list);
					}
					if (data.isRedHigh() || data.isRedLow()) { //if this meets criteria we add it to the list, if not, we don't need it
						list.add(data);
					}
				}
			}
			
			return map;
			
		} catch (IOException e) {
			System.out.println("Error reading input");
			e.printStackTrace();
		} 
		finally {
			try {
				if (br != null) {
					br.close(); //close the bufferedReader	
				}
				if (reader != null) {
					reader.close(); //close the fileReader
				}
			} catch (IOException e) {
				System.out.println("Error closing Stream");
				e.printStackTrace();				
			}
		}
		//if we get here, something went wrong and we should be throwing an exception before this point
		return null;
		
	}

	private File getFileFromResources(String fileName) {
		ClassLoader classLoader = getClass().getClassLoader();
		URL resource = classLoader.getResource(fileName);
		if (resource == null) {
			throw new IllegalArgumentException("File Not Found!");
		} else {
			return new File(resource.getFile());
		}
	}
	
	private TelemetryData parseLine(String line) {
		String[] array = line.split("\\|");
//		DateFormat: 20180101 23:01:05.001
		try {
		Date date;
		
		date = df.parse(array[0].toString());

		TelemetryData data = new TelemetryData(date //timestamp
				, array[1].toString() //sat number
				, Integer.parseInt(array[2]) //Red high
				, Integer.parseInt(array[3]) //yellow high
				, Integer.parseInt(array[4]) // red low
				, Integer.parseInt(array[5]) // yellow low
				, Double.parseDouble(array[6]) //actual value
				, array[7].toString()); //component type (Thermostat or Battery)
		
		return data;
		} catch (ParseException e) {
			System.out.println("Date did not parse");
			e.printStackTrace();
		} catch (NumberFormatException e) {
			System.out.println("A number did not parse");
			e.printStackTrace();
		}
		//if we get here, something went wrong and we should be throwing an exception before this point
		return null;
	
	}
	
}
