package baseClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {

	public static WebDriver driver = null;
	public static Properties config = new Properties();
	public static Properties or = new Properties();
	public static String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
	public static ExtentReports extent;
	public static ExtentTest test;
	
	@BeforeSuite
	public static void loadingFiles() throws IOException {

		FileInputStream f1 = new FileInputStream(
				System.getProperty("user.dir") + "/src/test/java/propertiesFiles/Config.properties");
		config.load(f1);

		FileInputStream f2 = new FileInputStream(
				System.getProperty("user.dir") + "/src/test/java/propertiesFiles/OR.properties");
		or.load(f2);
		
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir")+ "/src/test/java/extentReports/ExtentReports_" + timeStamp + ".html");
		
		extent = new ExtentReports();
		
		extent.attachReporter(htmlReporter);
	}

	@BeforeMethod
	public static void loadingBrowser() {

		if (config.getProperty("Browser").equalsIgnoreCase("chrome")) {

			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		} else if (config.getProperty("browser").equalsIgnoreCase("firefox")) {

			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		} else if (config.getProperty("browser").equalsIgnoreCase("ie")) {

			WebDriverManager.iedriver().setup();
			driver = new InternetExplorerDriver();
		}

		driver.manage().window().maximize();
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		driver.get(config.getProperty("ApplicationURL"));
		
		String actual = driver.getTitle();
		
		String expected = "Online Shopping for Women, Men, Kids Fashion & Lifestyle - Myntra";
		
		Assert.assertEquals(actual, expected);
		
	}
	
	@AfterMethod
	public static void tearDown() {

		driver.quit();
	}
	
	@AfterSuite(alwaysRun = true)
	public static void writeToExtentReport() {

		extent.flush();
	}
	
	public static void screenShots() throws IOException {

		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		
		String reportDirectory = new File(System.getProperty("user.dir")).getAbsolutePath() + "/src/test/java/screenShots/ScreenShots_" + timeStamp + ".png";
		
		File destFile = new File(reportDirectory);
		
		FileUtils.copyFile(scrFile,destFile);
	
		test.addScreenCaptureFromPath(reportDirectory);
		
		String filePath = destFile.toString();

		String path = "<img src=\"file://" + filePath + "\" alt=\"\"/>";
		
		Reporter.log(path);

	}

}
