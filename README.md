# Journey Cost Estimate Service
The RESTFul service to provide cost of a journey given the date of journey, fuel used, mpg of vehicle and total miles of journey

## Summary
**JourneyCostEstimateService** is an application with associated tests that can calculates the cost of the journey based on the journey date, fuel type, mpg and mileage of the journey. The service also calculates the journey cost for current date and provides a summary message to tell how cheap/expensive if the journey date is today. The fuel prices are loaded from the rates file found at [UK government weekly fuel prices](https://www.gov.uk/government/statistical-data-sets/oil-and-petroleum-products-weekly-statistics).

## Implementation Summary

### Assumptions made
- The prices will be loaded from CSV file placed under resources folder named Weekly_road_fuel_prices_CSV.csv
- The allowed fule types are 'petrol' and 'diesel' (case sensitive)
- The journey date is supplied in ddMMyyyy format
- The CSV file downloaded from UK Gov website will be modified such that there will only one header row
- Extra duty rate on fuel provided as 'Duty rate per litre (�) from 7 March 2001' are ignored to keep the solution simple

### Technologies used
- The application has been implemented using Spring Boot
- Jackson library is used to load CSV file data
- The application has been implemented as a Maven project

### Implementation classes summary
- FuelPricesDataRepositoryImpl class acts as a repository of loading Fuel prices from CSV file. All the prices loaded in to memory on midnight every day.
- If there is a new rates file available, place it under resources folder and the new data will be loaded midnight.
- It requires application reboot to make the new rates available
- If the fuel prices are not loaded or the fuel price not available for any given date and appropriate error message is shown.
- Limited test scenarios has been provided for basic success and failure scenarios. Testing dao's, helper class and extended scenarios has been considered but not implemented due to time constraints.

## How to Build and Run the application locally
- Install maven locally if it is not installed already. 
- You need internet connection to download maven then to run the application
- Check out the project from [Git Hub](https://github.com/sureshkavali/JourneyCostEstimateService)
- From project's root folder issue the command **maven clean install**
- It will produce runnable jar file under project_home/target folder
- run the jar with the command **java -jar journey-cost-estimate-service-0.0.1-SNAPSHOT.jar** or alteratively run the main class **com.screenmedia.demo.JourneyCostEstimateServiceApplication**
- The application will start and will be accessible on port **9090**
- In the browser address bar follow the link like http://localhost:9090/journeycost/01012019/petrol/10/100. This will return you the summary of cost, duty paid and message explaining how expensive/cheaper if the journey was today.
- The url will be in the format of http://[host]:[port]/journeycost/{date}/{fuelType}/{mpg}/{mileage} and all the elements are mandatory
- Alternatively the application can be tested using tools like postman or from linux command line use cURL command.

## How to test the solution from publicly hosted server
- The application has been deployed to Cloud Foundry
- Access the url https://journeycostestimateserviceapp.cfapps.io/journeycost/{date}/{fuelType}/{mpg}/{mileage}
- - eg., Try the url https://journeycostestimateserviceapp.cfapps.io/journeycost/01012019/petrol/30/250
- All the placeholders are mandatory, missing value for any placeholder will result in 404 error.
## Additional comments
- The application behaves as expected in majority of scenarios. However this is not a full solution and there may be some gaps like handling of path parameter validation or intelligently handling the value of fuel type is kept outside scope.
- BigDecimal has been used to do the calculation as it is best suited for the scenario. However Rounding values has been left to default behaviour in most places and scaled 2 digits with Rounding Up mode where required. So if the value returned varies by 0.01 pence at times.
- Uploading rates file every week requires manual intervension. It would be nice to automate the process.  
