# Selenium

## Prerequisite

### easyTravel

* Download the demo application easyTravel: https://community.dynatrace.com/community/display/DL/Demo+Applications+-+easyTravel
* Follow the installation & configuration steps: https://community.dynatrace.com/community/display/DL/Demo+Applications+-+easyTravel#DemoApplications-easyTravel-InstallingeasyTravel
<img src="https://github.com/Dynatrace/Dynatrace-AppMon-Test-Automation-Samples/blob/master/jmeter/img/EasyTravel_configuration.PNG"/>

* Make sure that easyTravel portal is running on http://localhost:8079/ 
<img src="https://github.com/Dynatrace/Dynatrace-AppMon-Test-Automation-Samples/blob/master/jmeter/img/EasyTravel_running.PNG"/>

## Sample

The [sample scenario](./code) runs two different test cases (booking a journey to New York and Linz).
All requests being sent are stored in a separate session.
Both are executed in two separate thread groups:
1. execute load test scenario (disabled by default)
2. execute test request to capture performance metrics in Test Automation

Such organization is used to show the recommended usage of Test Automation feature, to avoid monitoring load test requests, and capture performance metrics only once per request.
Please refer also to the tutorial on [how to integrate web API performance monitoring in JMeter](https://www.dynatrace.com/support/doc/appmon/continuous-delivery-test-automation/test-automation-tutorials/integrate-web-api-performance-monitoring-in-jmeter/) and the [sample script](https://assets.dynatrace.com/global/doc/appmon/continuous-delivery-test-automation/test-automation-tutorials/integrate-web-api-performance-monitoring-in-jmeter/findJourneys-tutorial.jmx) provided there. 
 
#### Preparation

```
git clone https://github.com/Dynatrace/Dynatrace-Test-Automation-Samples
C:\YOURDIR> Set JAVA_HOME=YOUR JAVA HOME
```

#### Run

* Start easyTravel (only web required, Standard scenario, manual visits)
* Make sure the agents are connected to your Dynatrace AppMon server
* Load and execute script in JMeter
<img src="https://github.com/Dynatrace/Dynatrace-AppMon-Test-Automation-Samples/blob/master/jmeter/img/JMeter.PNG"/>


#### Result

* Test Results are available

<img src="https://github.com/Dynatrace/Dynatrace-AppMon-Test-Automation-Samples/blob/master/jmeter/img/AppMon_TestResults_dashlet.PNG" />

* Session is stored

<img src="https://github.com/Dynatrace/Dynatrace-AppMon-Test-Automation-Samples/blob/master/jmeter/img/AppMon_StoredSessions_dashlet.PNG" />
