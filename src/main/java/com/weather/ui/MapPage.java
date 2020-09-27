package com.weather.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.weather.base.WeatherBase;
import com.weather.util.UiUtil;
import com.weather.util.WeatherInfo;

public class MapPage extends WeatherBase {
	public WeatherInfo uiWeatherInfo=null;
	public Actions action = new Actions(driver);
	
	@FindBy(xpath ="//input[@class='searchBox']")
	WebElement searchBox;
	
	@FindBy(xpath ="//div/span[text()='Bengaluru, Karnataka']")
	WebElement cityNameOnMap;
	
	@FindBy(xpath ="//span//b[contains(text(),'Wind')]")
	WebElement windSpeed;
	
	@FindBy(xpath ="//span//b[contains(text(),'Humidity')]")
	WebElement humidity;
	
	@FindBy(xpath ="//span//b[contains(text(),'Degrees')]")
	WebElement tempDegree;
	
	@FindBy(xpath ="//span//b[contains(text(),'Fahrenheit')]")
	WebElement tempFahrenheit;
	
	@FindBy(xpath ="//a[@aria-label='Zoom out']")
	WebElement zoomOut;
	
	
	public MapPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	public void searchCity(String cityName) throws InterruptedException {
		UiUtil.waitForElement(driver, searchBox);
		searchBox.sendKeys(cityName);
		WebElement checkBox=driver.findElement(By.xpath("//label//input[@id='"+cityName+"']"));
		UiUtil.isCityChecked(cityName,checkBox);
	}
	
	public void enteredCityOnMapDisplayed(String cityName) throws InterruptedException {
		WebElement cityOnMap=driver.findElement(By.xpath("//div[text()='"+cityName+"']"));
		UiUtil.CityOnMap(cityName,cityOnMap,zoomOut,action);
	}
	
	public void selectCityOnMap(String cityName) {
		driver.findElement(By.xpath("//div[text()='"+cityName+"']")).click();
	}
	
	public WeatherInfo weatherInfo() {
		UiUtil.waitForElement(driver, tempDegree);
		String[] tempDeg= tempDegree.getText().split(" ");
		log.info("Temp in Degree on map ---> "+tempDeg[tempDeg.length-1].trim());
		String tempInDegree=tempDeg[tempDeg.length-1].trim();
		
		UiUtil.waitForElement(driver, tempFahrenheit);
		String[] tempFah= tempFahrenheit.getText().split(" ");
		log.info("Temp in Fahrenheit on map  ---> "+tempFah[tempDeg.length-1].trim());
		String tempInFah=tempFah[tempDeg.length-1].trim();
		
		UiUtil.waitForElement(driver, windSpeed);
		String windInfo[] = windSpeed.getText().split(":");
		String windSpd[] = windInfo[1].split(" ");
		log.info("Wind Speed on map ---> "+windSpd[1].trim());
		String windSpeedKmh=windSpd[1];
		
		uiWeatherInfo= new WeatherInfo(tempInDegree,tempInFah,windSpeedKmh);
		return uiWeatherInfo;
	}
	
}
