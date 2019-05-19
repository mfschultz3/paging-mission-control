package com.schultz.app;

import java.util.ArrayList;
import java.util.Map;

public class App 
{
    public static void main( String[] args )
    {
        //run pagingMissionControl
    	DataParser dataParser = new DataParser();
    	
    	Map<String, ArrayList<Data>> dataMap = dataParser.loadAndReadFile("satelliteData.txt");
    	
    	DataTester tester = new DataTester();
    	tester.testData(dataMap);
    	
    }
}
