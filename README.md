###Paging Mission Control Submission

Author: Morris Schultz

###About
This Java application is built to process status telemetry from satellites and generates alert messages in cases of certain limit violation scenarios.

###Build and Dependencies
Maven 3.6.1 was used to build this project.
Gson 2.8.5 has been imported (using Maven) in order to handle JSON objects

###Assumptions
1. All data in the input is in chronological order
2. Data in the output does not need to be in a particular order

###Future Updates / Maintenance
* Instead of using a single array/list I put the data into a map based on SatId and Component.  This will hopefully be flexible enough if additional satellites are added, code changes would be minimal.  <br>
* Within DataTester.checkForAlarms() I have tried to prepare for larger sets of data, where everything is not just within 5 minutes.  <br>
* As an overall goal, I have attempted to iterate over the data as little as possible, to increase efficiency and to detect when violation scenarios have not been met quickly.

### Input

```
src/main/resources/satelliteData.txt
```

### My Ouput

```javascript
[
  {
    "satelliteId": "1000",
    "severity": "RED LOW",
    "component": "BATT",
    "timestamp": "2018-01-01T23:01:09.521Z"
  },
  {
    "satelliteId": "1000",
    "severity": "RED HIGH",
    "component": "TSTAT",
    "timestamp": "2018-01-01T23:01:38.001Z"
  }
]
```
