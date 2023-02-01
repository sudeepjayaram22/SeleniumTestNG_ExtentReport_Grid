package com.erail.qa.TestBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.aventstack.extentreports.ExtentReporter;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.erail.qa.Util.TestUtil;
import com.sun.jna.platform.win32.Netapi32Util.User;

public class TestAutomationBase {

	public static WebDriver driver;
	public static ExtentHtmlReporter extentHtmlReporter;
	public static ExtentReports extentReport;
	Properties prop;
	String userDirectory;

	public TestAutomationBase() throws IOException {
		userDirectory = System.getProperty("user.dir");
		FileInputStream file = new FileInputStream(
				userDirectory + "\\src\\main\\java\\com\\erail\\qa\\config\\config.properties");
		prop = new Properties();
		prop.load(file);
	}

	public void Initialization(String browserName) throws IOException {
		if (browserName.equals("param-val-not-found")) {
			browserName = prop.getProperty("browserName");
		}

		if (browserName.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver",
					userDirectory + "\\src\\main\\java\\com\\erail\\qa\\Util\\drivers\\chromedriver.exe");
			driver = new ChromeDriver();
			
//			DesiredCapabilities cap = new DesiredCapabilities();
//			cap.setCapability("browserName", "chrome");
//			driver = new RemoteWebDriver(new URL(""), cap);
			
		} else if (browserName.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver",
					userDirectory + "\\src\\main\\java\\com\\erail\\qa\\Util\\drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
		} else if (browserName.equalsIgnoreCase("ie")) {
			System.setProperty("webdriver.ie.driver",
					userDirectory + "\\src\\main\\java\\com\\erail\\qa\\Util\\drivers\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();
		}

		extentHtmlReporter = new ExtentHtmlReporter(userDirectory + "\\reports\\"
				+ new TestUtil().DateTimeFormat(LocalDateTime.now(), "ddMMMYY-HHmmss") + ".html");

		extentReport = new ExtentReports();
		extentReport.attachReporter(extentHtmlReporter);
		driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TestUtil.timeUnit);
		driver.manage().timeouts().implicitlyWait(TestUtil.ImplicitWait, TestUtil.timeUnit);
	}

	public void LaunchUrl() {
		driver.navigate().to(prop.getProperty("url"));
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();

	}

}
