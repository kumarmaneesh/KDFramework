package config;

//import pageObjects.HomePage;
//import pageObjects.LoginPage;
import static executionEngine.DriverScript.OR;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentReports;

import executionEngine.DriverScript;
import utility.Log;

public class ActionKeywords {

	public static WebDriver driver;
	public static ExtentReports extent;

	static String driverPath = "C:\\Users\\ttc.mk\\Desktop\\MK\\";

	private static String storedText;

	//extent = new ExtentReports(config.Constants.Report_Path);

	public static void openBrowser(String object,String value) throws InterruptedException{	
		try{
			if(value.equalsIgnoreCase("chrome")){
				System.out.println("launching Chrome browser");
				Log.info("Opening Browser");
				System.setProperty("webdriver.chrome.driver", driverPath+"chromedriver.exe");
				driver = new ChromeDriver();
				driver.manage().window().maximize();

				//driver = new FirefoxDriver();
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				driver.get("http://www.store.demoqa.com");

				//Below code is to bypass zscalar authentication
				//*****************************************************************************************************
				Thread.sleep(3000);
				driver.findElement(By.name("lognsfc")).sendKeys("surbhi.kukreja.ttc@sgx.com");
				driver.findElement(By.name("lsubmit")).click();

				driver.findElement(By.name("passsfc")).sendKeys("Password123");
				driver.findElement(By.name("bsubmit")).click();
				Thread.sleep(2000);
			}
			else if(value.equalsIgnoreCase("firefox")){
				System.out.println("launching firefox browser");
				driver = new FirefoxDriver();
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				driver.get("http://www.store.demoqa.com");
				Thread.sleep(3000);
			}
		}catch(Exception e){
			//This is to print the logs - Method Name & Error description/stack
			Log.info("Not able to open Browser --- " + e.getMessage());
			//Set the value of result variable to false
			DriverScript.bResult = false;
		}
	}

	//All the methods in this class now accept 'Object' name as an argument

	public static void navigate(String object,String value){
		try{
			Log.info("Navigating to URL");
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.get(Constants.URL);
		}catch(Exception e){
			Log.info("Not able to navigate --- " + e.getMessage());
			DriverScript.bResult = false;
		}
	}

	public static void click(String object,String value){
		try{
			Log.info("Clicking on Webelement "+ object);
			driver.findElement(By.xpath(OR.getProperty(object))).click();
			Thread.sleep(2000);
			
			//WebDriverWait wait = new WebDriverWait(driver, 15); 
			//wait.until(ExpectedConditions.titleContains("Google"));
		}catch(Exception e){
			Log.error("Not able to click --- " + e.getMessage());
			DriverScript.bResult = false;
		}
	}

	public static void input(String object,String value){
		Log.info("Entering the text in: "+ object);
		driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(value); 
	}

	/*public static void input_UserName(String object,String value){
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Constants.UserName); 
			}

		public static void input_Password(String object,String value){
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(Constants.Password);
			}*/

	public static void waitFor(String object,String value) throws Exception{
		Log.info("Wait for 5 seconds");
		Thread.sleep(5000);
	}

	public static void verify(String object,String value){
		WebDriverWait wait = new WebDriverWait(driver, 15); 
		wait.until(ExpectedConditions.elementToBeSelected(driver.findElement(By.xpath(OR.getProperty(object)))));
		
		Log.info("Verifying Text for item: " + object);
		String objText = driver.findElement(By.xpath(OR.getProperty(object))).getText();
		if(objText.contains(value)){
			System.out.println("Verification Successful");
		}
		else{
			System.out.println("Verification Failed");
		}
	}

	public static void verifyStoredText(String object,String value){
		Log.info("Verifying Text for item: " + object);
		String objText = driver.findElement(By.xpath(OR.getProperty(object))).getText();
		if(objText.contains(storedText)){
			Log.info("Stored Text for object "+ object +"is: " + storedText);
			System.out.println("Verification Successful for stored text");
		}
		else{
			System.out.println("Verification Failed for stored text");
			System.out.println("Expected Value: " + storedText);
			System.out.println("Actual Value: " + objText);
		}
	}

	public static String storeValue(String object,String value){
		Log.info("Store Text in a variable for: " + object);
		storedText = driver.findElement(By.xpath(OR.getProperty(object))).getText();
		return storedText;
	}

	public static void mouseHover(String object,String value) throws InterruptedException{
		try{
		Log.info("MouseHover element: "+ object + " and clicking on: " + value);
		Actions action = new Actions(driver);
		WebElement we = driver.findElement(By.xpath(OR.getProperty(object)));
		WebElement we2 = driver.findElement(By.xpath(OR.getProperty(value)));
		Thread.sleep(500);
		action.moveToElement(we).moveToElement(we2).click().perform();
		Thread.sleep(500);
		}catch(Exception e){
			Log.error("Not able to perform MouseHover --- " + e.getMessage());
			DriverScript.bResult = false;
		}
	}

	public static void closeBrowser(String object,String value){
		try{
			Log.info("Closing the browser");
			driver.quit();
		}catch(Exception e){
			Log.error("Not able to Close the Browser --- " + e.getMessage());
			DriverScript.bResult = false;
		}
	}
}
