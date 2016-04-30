package executionEngine;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import config.ActionKeywords;
import config.Constants;
import utility.ExcelUtils;
import utility.Log;

public class DriverScript {

	public static Properties OR;
	public static ActionKeywords actionKeywords;
	public static String sPageObject;
	public static String sActionKeyword;
	public static String sTestData;
	private static ExtentReports extent;
	private static ExtentTest testExt;

	//This is reflection class object, declared as 'public static'So that it can be used outside the scope of main[] method
	public static Method[] method;

	public static int iTestStep;
	public static int iTestLastStep;
	public static String sTestCaseID;
	public static String sTestCaseDesc;
	public static String sTestStepDesc;
	public static String sRunMode;
	public static boolean bResult;
	public static boolean bResultVerify;

	public static WebDriver driver;

	//Here we are instantiating a new object of class 'ActionKeywords'
	//public DriverScript() throws NoSuchMethodException, SecurityException{
	//actionKeywords = new ActionKeywords();
	//This will load all the methods of the class 'ActionKeywords' in it.
	//It will be like array of method, use the break point here and do the watch
	//method = actionKeywords.getClass().getMethods();
	//}

	public static void main(String[] args) throws Exception {

		//Here we are instantiating a new object for Extent Report
		extent = new ExtentReports(config.Constants.Report_Path);

		// starting test
		//ExtentTest testExt = extent.startTest("Login to DemoQA", "Login with valid user credentials");
		//testExt.assignCategory("DEMO");
		//Here we are instantiating a new object of class 'ActionKeywords'
		actionKeywords = new ActionKeywords();

		//This will load all the methods of the class 'ActionKeywords' in it. It will be like array of method, use the break point here and do the watch
		method = actionKeywords.getClass().getMethods();

		//This is to start the Log4j logging in the test case
		DOMConfigurator.configure("log4j.xml");

		// Here we are passing the Excel path and SheetName as arguments to connect with Excel file 
		ExcelUtils.setExcelFile(Constants.Path_TestData);

		//Declaring String variable for storing Object Repository path
		String Path_OR = Constants.Path_OR;

		//Creating file system object for Object Repository text/property file
		FileInputStream fs = new FileInputStream(Path_OR);

		//Creating an Object of properties
		OR= new Properties(System.getProperties());

		//Loading all the properties from Object Repository property file in to OR object
		OR.load(fs);

		DriverScript startEngine = new DriverScript();
		startEngine.execute_TestCase();

	}

	private void execute_TestCase() throws Exception {
		try{
			//This will return the total number of test cases mentioned in the Test cases sheet
			int iTotalTestCases = ExcelUtils.getRowCount(Constants.Sheet_TestCases);

			//This loop will execute number of times equal to Total number of test cases
			for(int iTestcase=1;iTestcase<iTotalTestCases;iTestcase++){
				//Setting the value of bResult variable to 'true' before starting every test case
				bResult = true;
				bResultVerify = true;
				//This is to get the Test case name from the Test Cases sheet
				sTestCaseID = ExcelUtils.getCellData(iTestcase, Constants.Col_TestCaseID, Constants.Sheet_TestCases);
				sTestCaseDesc = ExcelUtils.getCellData(iTestcase, Constants.Col_TestCaseDesc, Constants.Sheet_TestCases);
				//This is to get the value of the Run Mode column for the current test case
				sRunMode = ExcelUtils.getCellData(iTestcase, Constants.Col_RunMode,Constants.Sheet_TestCases);
				//This is the condition statement on RunMode value
				if (sRunMode.equals("Yes")){
					//Only if the value of Run Mode is 'Yes', this part of code will execute
					iTestStep = ExcelUtils.getRowContains(sTestCaseID, Constants.Col_TestCaseID, Constants.Sheet_TestSteps);
					iTestLastStep = ExcelUtils.getTestStepsCount(Constants.Sheet_TestSteps, sTestCaseID, iTestStep);
					Log.startTestCase(sTestCaseID);

					testExt = extent.startTest(sTestCaseID, sTestCaseDesc);
					//testExt = extent.startTest(sTestCaseID, sTestCaseDesc);

					//Setting the value of bResult variable to 'true' before starting every test step
					bResult=true;
					//This loop will execute number of times equal to Total number of test steps
					for (;iTestStep<iTestLastStep;iTestStep++){

						sActionKeyword = ExcelUtils.getCellData(iTestStep, Constants.Col_ActionKeyword,Constants.Sheet_TestSteps);
						sPageObject = ExcelUtils.getCellData(iTestStep, Constants.Col_PageObject, Constants.Sheet_TestSteps);
						sTestData = ExcelUtils.getCellData(iTestStep, Constants.Col_TestData, Constants.Sheet_TestSteps);

						execute_Actions();
						//This is the result code, this code will execute after each test step
						//The execution flow will go in to this only if the value of bResult is 'false'
						if(bResult==false || bResultVerify==false){
							//If 'false' then store the test case result as Fail
							ExcelUtils.setCellData(Constants.KEYWORD_FAIL,iTestcase,Constants.Col_Result,Constants.Sheet_TestCases);
							//End the test case in the logs
							//Log.endTestCase(sTestCaseID);

							//testExt.log(LogStatus.FAIL, "Test Case Failed: " + sTestCaseID);

							//By this break statement, execution flow will not execute any more test step of the failed test case	
							//break;
						}
					}

					//This will only execute after the last step of the test case, if value is not 'false' at any step	
					if(bResult==true && bResultVerify==true){
						//Storing the result as Pass in the excel sheet
						ExcelUtils.setCellData(Constants.KEYWORD_PASS,iTestcase,Constants.Col_Result,Constants.Sheet_TestCases);
						Log.endTestCase(sTestCaseID);
					}else{						
						ExcelUtils.setCellData(Constants.KEYWORD_FAIL,iTestcase,Constants.Col_Result,Constants.Sheet_TestCases);
						Log.endTestCase(sTestCaseID);
					}

				// ending test
				extent.endTest(testExt);
				// writing everything to document
				extent.flush();
			}
		}
	}catch(Exception e){
		System.out.println("TC Failed due to exception: " +e.getMessage());
	}
	/*finally{
			//driver.close();
			driver.quit();
			//driver.get(config.Constants.Report_Path);
			//System.out.println("");
		}*/
}


//This method contains the code to perform some action
//As it is completely different set of logic, which revolves around the action only,
//It makes sense to keep it separate from the main driver script
//This is to execute test step (Action)
private static void execute_Actions() throws Exception {

	for(int i=0;i<method.length;i++){

		if(method[i].getName().equals(sActionKeyword)){
			method[i].invoke(actionKeywords,sPageObject,sTestData);
			//This code block will execute after every test step
			if(bResult==true){
				//If the executed test step value is true, Pass the test step in Excel sheet
				ExcelUtils.setCellData(Constants.KEYWORD_PASS, iTestStep, Constants.Col_TestStepResult, Constants.Sheet_TestSteps);
				sTestStepDesc = ExcelUtils.getCellData(iTestStep,Constants.Col_TestStepDesc,Constants.Sheet_TestSteps);
				// step log
				testExt.log(LogStatus.PASS, "Test Step Passed: " + sTestStepDesc);
				break;
			}else{
				//If the executed test step value is false, Fail the test step in Excel sheet
				ExcelUtils.setCellData(Constants.KEYWORD_FAIL, iTestStep, Constants.Col_TestStepResult, Constants.Sheet_TestSteps);
				//In case of false, the test execution will not reach to last step of closing browser
				//So it make sense to close the browser before moving on to next test case
				sTestStepDesc = ExcelUtils.getCellData(iTestStep,Constants.Col_TestStepDesc,Constants.Sheet_TestSteps);
				//step log
				testExt.log(LogStatus.FAIL, "Test Step Failed: " + sTestStepDesc);

				String ssPath = config.Constants.Screenshots_Path + sPageObject +".png";
				//inserting snapshot
				testExt.log(LogStatus.INFO, "Snapshot below: " + testExt.addScreenCapture(ssPath));
				//ActionKeywords.closeBrowser("","");
				break;
			}
		}
	}
}

//This method will take screenshots
public static String captureScreenshot(String snapshotName) { 
	try{
		driver.getCurrentUrl();
		File source = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);			
		String dest = config.Constants.Screenshots_Path + snapshotName + ".png";
		File destination = new File(dest);
		FileUtils.copyFile(source, destination);
		System.out.println("Snapshot taken!");
		return dest;
	} catch (Exception e) {
		System.out.println("Error while taking Snapshot " +e.getMessage());
		return e.getMessage();
	}
}


}


