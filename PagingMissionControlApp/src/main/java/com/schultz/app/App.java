package com.schultz.app;

import java.util.ArrayList;
import java.util.Map;

/**
 * 
 * @author Morris Schultz
 *
 */

public class App 
{
    public static void main( String[] args )
    {
        runPagingMissionControl("satelliteData.txt");  	
    }
    
    private static void runPagingMissionControl(String fileName) {
    	DataParser dataParser = new DataParser();
    	Map<String, ArrayList<TelemetryData>> dataMap = dataParser.loadAndReadFile(fileName);
    	DataTester tester = new DataTester();
    	tester.checkForAlarms(dataMap);
    }
}
