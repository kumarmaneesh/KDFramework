package config;

import java.util.concurrent.TimeUnit; 
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import executionEngine.DriverScript;
import utility.Log;

//import pageObjects.HomePage;
//import pageObjects.LoginPage;
import static executionEngine.DriverScript.OR;

public class ActionKeywords {

	public static WebDriver driver;
	static String driverPath = "C:\\Users\\ttc.mk\\Desktop\\MK\\";

	public static void openBrowser(String object,String value) throws InterruptedException{	
		try{
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
		Log.info("Verifying Text");
		String objText = driver.findElement(By.xpath(OR.getProperty(object))).getText();
		if(objText.contains(value)){
			System.out.println("Verification Successful");
		}
		else{
			System.out.println("Verification Failed");
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
