package com.weather.util;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;


public class UiUtil {
	public static WebDriver driver;
	public static Logger log = Logger.getLogger(UiUtil.class);
	public static Boolean tempDegreeMatch,tempFahrMatch,windSpeedMatch=false;
	public static Boolean tempDegree,tempFahr,windSpeed=false;
	public static JSONObject jsonObj=null;
	public static int TIMEOUT_SEC=30;
	public static WebDriverWait wait=null;

	public static void waitForElement(WebDriver driver,WebElement element) {
		wait = new WebDriverWait(driver,TIMEOUT_SEC);
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public static void isCityChecked(String cityName, WebElement checkBox) throws InterruptedException {
		try {
			if(checkBox.isSelected()==false) {
				checkBox.click();
				waitForElement(driver,checkBox);
				wait = new WebDriverWait(driver,TIMEOUT_SEC);
				wait.until(ExpectedConditions.elementToBeClickable(checkBox));
			}
			else {
				log.info("City name is already selected by default\n");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void CityOnMap(String cityName,WebElement cityOnMap, WebElement zoomOut, Actions action) throws InterruptedException {
		if(cityOnMap.isDisplayed()==false) {
			zoomOut.click();
			for(int i=0;i<3;i++) {
				action.sendKeys(Keys.ARROW_DOWN).perform();
				zoomOut.click();
			}
		}
	}

	public static Map<String, Float> comparator(WeatherInfo uiWeatherInfo, WeatherInfo apiWeatherInfo, String tempDegreeVariance,String tempFahrenheitVariance,String windSpeedVariance) {
		float tempKelvinApi = 0,tempFaherApi=0,windSpeedApi = 0;
		float tempDegreeApi=0, tempFahrApi=0, windSpeedKmphApi=0;
		if(apiWeatherInfo.tempDegree!= null) {
			tempKelvinApi = Float.parseFloat(apiWeatherInfo.tempDegree);
			tempDegreeApi = (float) (tempKelvinApi-273.15);
		}
		if(apiWeatherInfo.tempFahr!=null) {
			tempFaherApi = Float.parseFloat(apiWeatherInfo.tempFahr);
			tempFahrApi = (float) (((tempFaherApi-273.15)*(1.8))+32);
		}
		if(apiWeatherInfo.windSpeed!=null) {
			windSpeedApi = Float.parseFloat(apiWeatherInfo.windSpeed);
			windSpeedKmphApi = (float) (1.609344*windSpeedApi);
		}

		Reporter.log("API weather info: Temp in degree "+tempDegreeApi+", Temp in Fahrenheit "+tempFahrApi+", Wind speed in kmph "+windSpeedKmphApi,true);

		float tempDegreeUi = Float.parseFloat(uiWeatherInfo.tempDegree);
		float tempFaherUi = Float.parseFloat(uiWeatherInfo.tempFahr);
		float windSpeedUi = Float.parseFloat(uiWeatherInfo.windSpeed);
		Reporter.log("UI weather info:  Temp in degree "+tempDegreeUi+", Temp in Fahrenheit "+tempFaherUi+", Wind speed in kmph "+windSpeedUi, true);

		float varTempDegreeApi=Float.parseFloat(tempDegreeVariance);
		float varTempFaherApi=Float.parseFloat(tempFahrenheitVariance);
		float varWindSpeedApi=Float.parseFloat(windSpeedVariance);
		Reporter.log("Variance Info: Temp degree variance: "+varTempDegreeApi+", Temp Fahrenheit variance: "+varTempFaherApi+", Wind speed variance: "+varWindSpeedApi,true);
		Map<String, Float> map= new LinkedHashMap<String,Float>();
		map.put("tempDegreeUi", tempDegreeUi);
		map.put("tempDegreeApi",tempDegreeApi);
		map.put("varTempDegreeApi",varTempDegreeApi);
		map.put("tempFaherUi",tempFaherUi);
		map.put("tempFahrApi",tempFahrApi);
		map.put("varTempFaherApi",varTempFaherApi);
		map.put("windSpeedUi",windSpeedUi);
		map.put("windSpeedKmphApi",windSpeedKmphApi);
		map.put("varWindSpeedApi",varWindSpeedApi);
		return map;

	}
}
