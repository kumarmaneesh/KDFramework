package utility;

import java.io.File;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Generic{

	public static WebDriver driver;

	public static String captureScreenshot(WebDriver driver, String snapshotName) { 

		try{
			//TakesScreenshot ts = (TakesScreenshot)driver;

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
