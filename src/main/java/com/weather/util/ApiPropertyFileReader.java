package com.weather.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import com.weather.base.WeatherBase;

public class ApiPropertyFileReader {

	public static void loadProperty() {
		FileInputStream fis=null;
		try {
			WeatherBase.apiProd= new Properties();
			fis = new FileInputStream("src/main/resources/api.properties");
			WeatherBase.apiProd.load(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
