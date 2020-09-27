package com.weather.test;

import java.util.Map;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

import com.weather.api.WeatherApi;
import com.weather.base.WeatherBase;
import com.weather.ui.HomePage;
import com.weather.ui.MapPage;
import com.weather.util.UiUtil;
import com.weather.util.WeatherInfo;

import junit.framework.Assert;

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
		if((obj.get("tempDegreeApi")!=0) && (obj.get("tempFahrApi")!=0) && (obj.get("tempFahrApi")!=0)) {
			log.info("Temparature in degrees of API and UI with variance are checking");
			Assert.assertTrue("Temparature in degree of API and UI are NOT same with variance, pls increase the variance value if necessary", (obj.get("tempDegreeUi")<=(obj.get("tempDegreeApi")+obj.get("varTempDegreeApi")) && obj.get("tempDegreeUi")>=(obj.get("tempDegreeApi")-obj.get("varTempDegreeApi"))));

			log.info("Temparature in fahrenheit of API and UI with variance are checking");
			Assert.assertTrue("Temparature in fahrenheit of API and UI are NOT same with variance, pls increase the variance value if necessary", (obj.get("tempFaherUi")<=(obj.get("tempFahrApi")+obj.get("varTempFaherApi")) && obj.get("tempFaherUi")>=(obj.get("tempFahrApi")-obj.get("varTempFaherApi"))));

			log.info("Wind Speed in kmph of API and UI with variance are checking");
			Assert.assertTrue("Wind Speed in kmph of API and UI are NOT same with variance, pls increase the variance value if necessary", (obj.get("tempFaherUi")<=(obj.get("tempFahrApi")+obj.get("varTempFaherApi")) && obj.get("tempFaherUi")>=(obj.get("tempFahrApi")-obj.get("varTempFaherApi"))));
		}
		else {
			Assert.fail("No data returned from API");
		}
	}
}
