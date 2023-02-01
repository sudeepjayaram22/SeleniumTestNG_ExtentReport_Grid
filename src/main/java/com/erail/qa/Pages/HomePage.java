package com.erail.qa.Pages;

import static org.testng.Assert.fail;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;
import com.erail.qa.TestBase.TestAutomationBase;
import com.erail.qa.Util.TestUtil;

public class HomePage extends TestAutomationBase {

	WebDriverWait wait;

	private final String trainListDataTableXPath = "//table[starts-with(@class,'DataTable TrainList TrainListHeader')]";

	@FindBy(xpath = "//input[@id='txtStationFrom']")
	WebElement fromStationTextBox;

	@FindBy(xpath = "//input[@id='txtStationTo']")
	WebElement toStationTextBox;

	@FindBy(xpath = "//input[@type='button' and contains(@title,'Select Departure date for availability')]")
	WebElement selectDateButton;

	@FindBy(id = "buttonFromTo")
	WebElement getTrainsButton;

	@FindBy(id = "divCalender")
	WebElement calendar;

	@FindBy(xpath = trainListDataTableXPath)
	WebElement trainListDataTable;

	private HashMap Integer;
	ExtentTest reporter;

	public HomePage() throws IOException {
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, 60);
	}

	public void VerifyHomePageLanding() {
		try {
			wait.until(ExpectedConditions.visibilityOf(fromStationTextBox));
		} catch (Exception e) {
			Assert.fail("Home page did not load successfully\nException: " + e.getMessage());
		}
	}

	public void EnterFromToStation(String fromStation, String toStation) throws InterruptedException {
		fromStationTextBox.clear();
		Thread.sleep(1000);
		fromStationTextBox.sendKeys(fromStation);
		Assert.assertEquals(fromStationTextBox.getAttribute("value"), fromStation);
		toStationTextBox.clear();
		Thread.sleep(1000);
		toStationTextBox.sendKeys(Keys.CLEAR + toStation);
		Assert.assertEquals(toStationTextBox.getAttribute("value"), toStation);
	}

	public void SelectDepartureDate(LocalDateTime date) throws NullPointerException {
		TestUtil util = new TestUtil();
		String month = util.DateTimeFormat(date, "MMM");
		String day = util.DateTimeFormat(date, "d");
		selectDateButton.click();
		wait.until(ExpectedConditions.visibilityOf(calendar));

		List<WebElement> monthsEle = driver
				.findElements(By.xpath("//div[@id='divCalender']//table//tr//td//table//tr[1]"));
		for (int i = 0; i < monthsEle.size(); i++) {
			if (monthsEle.get(i).getText().contains(month)) {
				List<WebElement> dateEle = driver.findElements(
						By.xpath("//div[@id='divCalender']//table//tr//td[" + (i + 1) + "]//table//tr//td"));
				for (WebElement datePick : dateEle) {
					if (datePick.getText().equalsIgnoreCase(day)) {
						datePick.click();
						break;
					}
				}
				break;
			}
		}
		Assert.assertTrue(selectDateButton.getAttribute("value").contains(util.DateTimeFormat(date, "dd-MMM-YY")));
	}

	public void GetBestMatchTrains() throws TimeoutException {
		getTrainsButton.click();
		try {
			wait.until(ExpectedConditions.visibilityOf(trainListDataTable));
		} catch (Exception e) {
			Assert.fail("Train list datatable did not appear");
		}

		TreeMap<String, Integer> arrivalDateMap = new TreeMap<String, Integer>();

		int columnIndex = new TestUtil().GetColumnindexByColumnName(
				driver.findElements(By.xpath(trainListDataTableXPath + "//tr//th")), "Arr.");
		List<WebElement> trains = driver.findElements(By.xpath(trainListDataTableXPath+"//tr"));
		for (int i = 0; i < trains.size(); i++) {
			try {
				arrivalDateMap
						.put(driver.findElement(By.xpath(trainListDataTableXPath+"//tr["
								+ (i + 1) + "]//td[" + columnIndex + "]")).getText(), i + 1);
			} catch (Exception e) {
			}
		}
		System.out.println("Map displayed as " + arrivalDateMap);
		List<Integer> rowIndexList = new ArrayList<Integer>();
		for (Entry<String, Integer> entry : arrivalDateMap.entrySet()) {
			rowIndexList.add(entry.getValue());
		}
	}
}