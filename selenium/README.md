# Selenium

## Prerequisite

### easyTravel

* Download the demo application easyTravel: https://community.dynatrace.com/community/display/DL/Demo+Applications+-+easyTravel
* Follow the installation & configuration steps: https://community.dynatrace.com/community/display/DL/Demo+Applications+-+easyTravel#DemoApplications-easyTravel-InstallingeasyTravel
* Make sure that easyTravel portal is running on http://localhost:8079/ 

<img src="https://github.com/Dynatrace/Dynatrace-Test-Automation-Samples/blob/master/selenium/img/easyTravelConfiguration.png" width=400" /> <img src="https://github.com/Dynatrace/Dynatrace-Test-Automation-Samples/blob/master/selenium/img/easyTravelRunning.png" width=400" />

### Browsers

Two test cases (bookParis and bookSpecialOffers) are run with three different browsers (Firefox, Chrome and Internet Explorer). The browsers needs to be installed locally.

## Samples

### 1. Without test run registration (automatic test run generation)

* Code: [Auto test-run](./code/auto-test-run/)
* External libraries for Selenium (http://www.seleniumhq.org/download/):
  * [selenium-2.53.0](./code/auto-test-run/lib/selenium-2.53.0) 
  * [third party web driver](./code/auto-test-run/lib/driver)
 
#### Run

# Start easyTravel (only web required, Standard scenario, manual visits)
# Make sure the agents are connected to Dynatrace
# Run the sample with Ant: ant test

#### Result



### 2. With test run registration

* Code: 
* External libraries: 
