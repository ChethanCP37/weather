#Project Title:
Weather comparator

#Tools and Technology Used:
Java, TestNG, Selenium, Maven, RestAssured, Log4j

#Framework Used:
Page Object Model

#Features:
It helps to compare the weather data from API (openweathermap) and UI (NDTV)

#Setup:
1. You need to download the zip file, unzip it and open in eclipse.
2. Navigate to project *weather* > right click on it > select *Maven* > select *Update Project* so that all maven dependencies will be downloaded. 
3. To run the project navigate to *testng.xml* > right click on xml file > select *Run As* > select *TestNg Suite*
4. To change the cityName, variance difference to match weather details from UI and API, navigate to *api.properties* file present under *src/main/resources/api.properties* path.
Ex: i. Comment Mysore and uncomment Bengaluru.
	ii. Change the variance values based on differences to pass/fail test case
5. To run scripts on chrome or firefox, change the browser value in *api.properties* present under *src/main/resources/ui.properties* path
6. To check the execution logs, open *application.log* present under weather project
7. To run the project file, navigate to *WeatherReport.java* present under *src/test/java/com/weather/test/WeatherReport.java* > Right click > select Run As > select TestNg Test
8. To change the pageLoadTimeout and implicitWait details, navigate to *WeatherBase.java* java file present under *src/main/java/com/weather/base/WeatherBase.java* and change PAGE_LOAD_TIMEOUT & IMPLICIT_WAIT as per the requirements.
9. To change WebDriverWait (TIMEOUT_SEC), navigate to *UiUtil.java* present under *src/main/java/com/weather/util/UiUtil.java* and change TIMEOUT_SEC value 

#Note: 
1. CityName, StateCode and CountryName must be within INDIA
2. If the combinations of cityName, stateCode and countryName doesn't work please check the same in postman 
3. As metioned on openweathermap api website, searching by states available only for the USA locations.




