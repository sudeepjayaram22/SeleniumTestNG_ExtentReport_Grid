package com.erail.qa.Tests;

import java.io.IOException;


import org.testng.annotations.*;

import com.erail.qa.TestBase.TestAutomationBase;

public class SearchOperationTest extends TestAutomationBase {

	
	
	public SearchOperationTest() throws IOException {
		super();
	}

	@BeforeTest
	public void SetUp() throws IOException {
		Initialization("");
		
	}

	@Test
	public void SearchTrains() {

	}

	@AfterClass
	public void TearDown() {

	}

}
