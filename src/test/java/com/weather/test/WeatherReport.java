package com.weather.test;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.testng.log4testng.Logger;

import com.weather.api.WeatherApi;
import com.weather.base.WeatherBase;
import com.weather.ui.HomePage;
import com.weather.ui.MapPage;
import com.weather.util.UiUtil;
import com.weather.util.WeatherInfo;

public class WeatherReport extends WeatherBase {
	static Logger log = Logger.getLogger(WeatherReport.class);
	private WeatherApi weaApi=null;
	private WeatherInfo apiWeatherInfo=null;
	private WeatherInfo uiWeatherInfo=null;
	private HomePage hmPage=null;
	private MapPage mapPage=null;

	@BeforeSuite
	public void start() {
		WeatherBase.uiSetup();
		WeatherBase.apiSetup();
	}

	@AfterSuite
	public void clean() {
		driver.close();
	}

	@Test(enabled=true,description="Comparision the weather information from NDTV ui and openweathermap api")
	public void compareWetherInfo() throws InterruptedException {
		String cityName=apiProd.getProperty("cityName");
		String stateCode=apiProd.getProperty("stateCode");
		String countryCode=apiProd.getProperty("countryCode");
		String apiKey=apiProd.getProperty("apiId");
		String tempDegreeVarience=apiProd.getProperty("tempDegreeVarience");
		String tempFahrenheitVarience=apiProd.getProperty("tempFahrenheitVarience");
		String windSpeedVarience=apiProd.getProperty("windSpeedVarience");

		weaApi= new WeatherApi();
		apiWeatherInfo=weaApi.getMethod(cityName, stateCode, countryCode, apiKey);
		
		log.info("Weather API response details are received");

		hmPage = new HomePage(driver);
		hmPage.clickNoThanksButtonToAlerts();
		hmPage.clickMoreOption();
		hmPage.clickWeatherOption();

		mapPage= new MapPage(driver);
		mapPage.searchCity(cityName);
		mapPage.enteredCityOnMapDisplayed(cityName);
		mapPage.selectCityOnMap(cityName);
		uiWeatherInfo=mapPage.weatherInfo();


		Map<String, Float> obj=UiUtil.comparator(uiWeatherInfo, apiWeatherInfo,tempDegreeVarience,tempFahrenheitVarience,windSpeedVarience);
		log.info("Weather UI details are received");

		float tempDegreeUi=(obj.get("tempDegreeUi"));
		float tempDegreeApi=(obj.get("tempDegreeApi"));
		float varTempDegreeApi=(obj.get("varTempDegreeApi"));

		float tempFaherUi=(obj.get("tempFaherUi"));
		float tempFahrApi=(obj.get("tempFahrApi"));
		float varTempFaherApi=(obj.get("varTempFaherApi"));

		float windSpeedUi=(obj.get("windSpeedUi"));
		float windSpeedKmphApi=(obj.get("windSpeedKmphApi"));
		float varWindSpeedApi=(obj.get("varWindSpeedApi"));

		if((obj.get("tempDegreeApi")!=0) && (obj.get("tempFahrApi")!=0) && (obj.get("tempFahrApi")!=0)) {
			SoftAssert sAssert= new SoftAssert();
			log.info("Temparature in degrees of API and UI with variance are checking");
			sAssert.assertTrue(tempDegreeUi<=(tempDegreeApi+varTempDegreeApi) && tempDegreeUi>=(tempDegreeApi-varTempDegreeApi), "Temparature in degree of API and UI are NOT same with variance, pls increase the variance value if necessary");
			
			log.info("Temparature in fahrenheit of API and UI with variance are checking");
			sAssert.assertTrue(tempFaherUi<=(tempFahrApi+varTempFaherApi) && tempFaherUi>=(tempFahrApi-varTempFaherApi),"Temparature in fahrenheit of API and UI are NOT same with variance, pls increase the variance value if necessary");

			log.info("Wind Speed in kmph of API and UI with variance are checking");
			sAssert.assertTrue(windSpeedUi<=(windSpeedKmphApi+varWindSpeedApi) && windSpeedUi>=(windSpeedKmphApi-varWindSpeedApi),"Wind Speed in kmph of API and UI are NOT same with variance, pls increase the variance value if necessary");
	
			sAssert.assertAll();
			
		}
		else {
			Assert.fail("No data returned from API");
		}
	}
}
