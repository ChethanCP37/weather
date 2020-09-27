package com.weather.ui;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.weather.base.WeatherBase;
import com.weather.util.UiUtil;

public class HomePage extends WeatherBase {
	public WebDriver driver=null;
	
	@FindBy(xpath="//a[@id='h_sub_menu'][@class='topnavmore']")
	WebElement moreOption;
	
	@FindBy(xpath = "//div[@class='topnav_cont']//a[text()='WEATHER']")
	WebElement weatherOption;
	
	@FindBy(xpath = "//div[@class='noti_btnwrap']//a[text()='No Thanks']")
	WebElement cancelAlert;
	
	@FindBy(xpath = "//div[@class='noti_btnwrap']//a[contains(text(),'Allow')]")
	WebElement allowAlert;
	
	
	public HomePage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	public void clickNoThanksButtonToAlerts() {
		//UiUtil.waitForElement(driver, cancelAlert);
		cancelAlert.click();
		log.info("No-Thanks button clicked on popup");
	}
	
	public void clickAllowButtonToAlerts() {
		UiUtil.waitForElement(driver, allowAlert);
		allowAlert.click();
		log.info("Allow button clicked on popup");
	}
	
	public void clickMoreOption() {
		UiUtil.waitForElement(driver, moreOption);
		moreOption.click();
		log.info("More option button clicked on home page");
	}
	
	public void clickWeatherOption() {
		UiUtil.waitForElement(driver, weatherOption);
		weatherOption.click();
		log.info("Weather option button clicked on home page");
	}
}
