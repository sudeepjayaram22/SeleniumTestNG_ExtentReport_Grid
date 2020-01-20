package com.erail.qa.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class TestUtil {

	public static int PAGE_LOAD_TIMEOUT = 60;
	public static int ImplicitWait = 60;
	public static TimeUnit timeUnit = TimeUnit.SECONDS;

	public String DateTimeFormat(LocalDateTime dateTime, String pattern) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
		return dateTime.format(dateTimeFormatter);
	}

	public int GetColumnindexByColumnName(List<WebElement> webElements, String columnName) {
		int columnIndex = -1;
		for (int i = 0; i < webElements.size(); i++) {
			if (webElements.get(i).getText().contains(columnName)) {
				columnIndex = i + 1;
			}
		}
		Assert.assertTrue(columnIndex > -1, "Unable to find the column name: " + columnName);
		return columnIndex;
	}

	public Statement ExecuteQuery(String query) throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:oracle:thin@localhost:xe", "", "");
		return conn.createStatement();
	}
}
