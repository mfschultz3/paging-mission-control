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

public class DataParser {
	
	public Map<String, ArrayList<Data>> loadAndReadFile(String fileName) {
		
		File file = null;
		BufferedReader br = null;
		try {
			Map<String, ArrayList<Data>> map = new HashMap<String, ArrayList<Data>>();
			
			file = getFileFromResources(fileName);
			FileReader reader = new FileReader(file);
			br = new BufferedReader(reader);
			
			String line = null;

			while ((line = br.readLine()) != null) {
//				System.out.println(line);
				Data data = parseLine(line);
				
				String key = data.getSatelliteId() + "_" + data.getComponent(); //use the ID and Component as the Key
				ArrayList<Data> list = map.get(key);
				if (list == null) {
					list = new ArrayList<Data>();
					map.put(key, list);
				}
				list.add(data);
	
			}
			
			return map;
			
		} catch (IOException e) {
			System.out.println("Error reading input");
			e.printStackTrace();
		}
		finally {
			try {
				if (br != null) {
					br.close();
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
	
	private Data parseLine(String line) {
		String[] array = line.split("\\|");
//		DateFormat: 20180101 23:01:05.001
		try {
		Date date;
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd HH:mm:ss.SSS");
		date = df.parse(array[0].toString());
//		System.out.println(date);
		Data data = new Data(date //timestamp
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
		}
		return null;
	
	}
	
}
