package com.erail.qa.Tests;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.concurrent.TimeoutException;

import org.testng.annotations.*;
import com.aventstack.extentreports.ExtentTest;
import com.erail.qa.Pages.HomePage;
import com.erail.qa.TestBase.TestAutomationBase;

public class HomePageTests extends TestAutomationBase {

	protected HomePage homePage;
	public ExtentTest reporter;

	public HomePageTests() throws IOException {
		super();
	}

	@BeforeTest
	@Parameters({ "browserName" })
	public void SetUpBeforeTest(String broswerName) throws IOException {
		Initialization(broswerName);
		homePage = new HomePage();
		LaunchUrl();
	}

	@Test(priority = 1) // , threadPoolSize = 2, invocationCount = 2, timeOut = 1000)
	public void VerifyHomePage() throws TimeoutException {
		reporter = extentReport.createTest("VerifyHomePage");
		homePage.VerifyHomePageLanding();
	}

	@Test(priority = 2, dataProvider = "GetTestData") // , threadPoolSize = 2, invocationCount = 2, timeOut = 1000)
	public void SearchTrain(String fromStn, String toStn) throws TimeoutException, InterruptedException {
		reporter = extentReport.createTest("SearchTrain");
		homePage.EnterFromToStation(fromStn, toStn);
		homePage.SelectDepartureDate(LocalDateTime.now().plusDays(7));
	}

	@Test(priority = 3) // ,threadPoolSize = 2, invocationCount = 2, timeOut = 1000)
	public void BestMacthTrain() throws TimeoutException {
		reporter = extentReport.createTest("BestMacthTrain");
		homePage.GetBestMatchTrains();
	}

		@AfterTest
	public void TearDownAfterTest() {
		driver.quit();
	}

	@AfterClass
	public void TearDown() {
		extentReport.flush();
	}

	@DataProvider
	public Object[][] GetTestData() throws SQLException, IOException {
		return new Object[][] { { "Mumbai", "New Delhi" }, { "New Delhi", "Karnataka" } };
		/*
		 * Statement st = new TestUtil().ExecuteQuery(""); ResultSet rs =
		 * st.executeQuery(""); while (rs.next()) { rs.getInt(1); } return new
		 * Object[][] { { rs.getInt(1) }, { rs.getString(2) } };
		 */

		/*
		 * FileInputStream fis = new FileInputStream(""); HSSFWorkbook workbook = new
		 * HSSFWorkbook(fis); HSSFSheet sheet = workbook.getSheet(""); HSSFRow row =
		 * sheet.getRow(0); int rowCount = sheet.getPhysicalNumberOfRows(); int
		 * columnCount = sheet.getRow(1).getLastCellNum();
		 * 
		 * Object[][] data = new Object[rowCount][columnCount];
		 * 
		 * for(int i=0;i<rowCount-1;i++) { row = sheet.getRow(i+1); for(int
		 * j=0;j<columnCount;j++) { if(row==null) { data[i][j]=""; }else { data[i][j]
		 * =row.getCell(j); } } }
		 * 
		 * return data;
		 */
	}
	// for understanding purpose
	//	@BeforeClass
	//	public void SetUp() throws IOException {
	//	
	//	}
	//
	//	@BeforeMethod
	//	
	//	public void SetUpBeforeMethod() throws IOException {
	//		
	//	}
	//	@AfterMethod
	//	public void TearDownAfterMethod() {
	//
	//	}

}
