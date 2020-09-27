package com.weather.base;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.weather.util.ApiPropertyFileReader;
import com.weather.util.UiPropertyFileReader;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class WeatherBase {

	public static Logger log = Logger.getLogger(WeatherBase.class);
	public static WebDriver driver;
	public static Properties uiProd=null;
	public static Properties apiProd=null;

	public static Map<String, String> queryPara;
	public static Map<String, String> headerPara;
	public static Response res=null;
	public static RequestSpecification request=null;
	public static String queryParam=null;
	public static String endPoint=null;

	public static int PAGE_LOAD_TIMEOUT =80;
	public static int IMPLICIT_WAIT =30;

	public WeatherBase(){
		UiPropertyFileReader.loadProperty();
		ApiPropertyFileReader.loadProperty();
	}
	public static void uiSetup() {
		String browser = uiProd.getProperty("browser");
		if(browser.equalsIgnoreCase("Chrome")) {
			System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver.exe");
			driver = new ChromeDriver();
		}
		else if(browser.equalsIgnoreCase("Firefox")) {
			System.setProperty("webdriver.gecko.driver", "src/main/resources/drivers/geckodriver.exe");
			driver = new FirefoxDriver();
		}
		else {
			System.out.println("Mentioned browser is not chrome or firefox");
		}

		log.info(browser+" browser is opened");
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.SECONDS);
		driver.get(uiProd.getProperty("url"));
		log.info("Url is intiated");
	}

	public static void apiSetup() {
		RestAssured.baseURI=apiProd.getProperty("baseUri");
		endPoint=apiProd.getProperty("endPoint");
		request=RestAssured.given();
	}
}
