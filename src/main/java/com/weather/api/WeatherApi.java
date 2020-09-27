package com.weather.api;

import java.util.LinkedHashMap;

import org.apache.log4j.Logger;
import org.testng.Reporter;

import com.weather.base.WeatherBase;
import com.weather.util.WeatherInfo;

public class WeatherApi extends WeatherBase{
	Logger log = Logger.getLogger(WeatherApi.class);

	public WeatherInfo getMethod(String cityName,String stateCode, String countryCode, String apiId) {
		if(cityName!=null && stateCode!=null && countryCode!=null) {
			queryParam=cityName+","+stateCode+","+countryCode;
			Reporter.log("City, State and Country parameters are passed",true);
		}
		else if(cityName!=null && stateCode!=null && countryCode==null) {
			queryParam=cityName+","+stateCode;
			Reporter.log("City and State are passed",true);
		}
		else if(cityName!=null && stateCode==null && countryCode==null) {
			queryParam=cityName;
			Reporter.log("Only City is passed",true);
		}else {
			Reporter.log("Please pass atleast city name to get weather information",true);
		}
		queryPara = new LinkedHashMap<String, String>();
		queryPara.put("appid",apiId);
		queryPara.put("q",queryParam);
		request.queryParams(queryPara);

		headerPara = new LinkedHashMap<String, String>();
		headerPara.put("Content-Type", "application/json");
		headerPara.put("Accept", "application/json");
		request.headers(headerPara);
		WeatherInfo apiWeatherInfo = null;
		try {
			res=request.get(endPoint);
			Reporter.log("Response of API: "+res.asString(),true);
			String temp=res.jsonPath().getString("main.temp");
			String windSpeed=res.jsonPath().getString("wind.speed");
			apiWeatherInfo= new WeatherInfo(temp,temp,windSpeed);

		}catch(Exception e) {
			Reporter.log("Response of API: "+res.asString(),true);

		}
		return apiWeatherInfo;
	}

}
