package AllureReports;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.codehaus.plexus.util.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import io.qameta.allure.Story;

public class Tests {

	 WebDriver driver;

	 public ExtentHtmlReporter htmlreporter;
	 public ExtentReports extent;
	 public ExtentTest test;
	 
	 @BeforeSuite
	 public void setupextent() {
		  htmlreporter = new ExtentHtmlReporter(System.getProperty("user.dir")+"/test-output/myreport.html");
		 htmlreporter.config().setDocumentTitle("Automation report");
		 htmlreporter.config().setReportName("Functional report");
		 htmlreporter.config().setTheme(Theme.DARK);
		 
		 extent = new ExtentReports();
		 
		 extent.attachReporter(htmlreporter);
		 extent.setSystemInfo("Hostname", "LocalHost");
		 extent.setSystemInfo("OS", "Windows 10");
		 extent.setSystemInfo("Tester name", "Sekar");
		 extent.setSystemInfo("Browser","chrome");
	 }
	 
	 @AfterSuite
	 public void endreport() {
		 extent.flush();
	 }
	 
	@BeforeClass
	public void setup() {
		System.setProperty("webdriver.chrome.driver","E:\\New folder\\sekar\\Allure_Reporting_TestNG\\driver\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		
		//BaseClass bs= new BaseClass();
	//	bs.initialize_driver();
		driver.get("https://demo.nopcommerce.com/");	
	}
	
	@Test(priority=1, description ="verify logo presence on home page")
	@Description("Verfiy Logo presence on Home page....")
	@Epic("EP001")
	@Feature("feature1: Logo")
	@Story("story: Logo presence")
	@Step("verify Logo presence")
	@Severity(SeverityLevel.MINOR)
	public void LogoPresence() {
		test=extent.createTest("LogoPresence");
	boolean display = 	driver.findElement(By.xpath("//div[@class='header-logo']//a//img")).isDisplayed();
		Assert.assertEquals(display, true);
			
	}
	
	
	@Test(priority=2)
	public void logintest() {
		test = extent.createTest("logintest");
		driver.findElement(By.xpath("//a[@class='ico-login']")).click();
		driver.findElement(By.xpath("//input[@id='Email']")).sendKeys("sehkar21@gmail.com");
		driver.findElement(By.xpath("//input[@id='Password']")).sendKeys("csksehkar1");
		driver.findElement(By.xpath("//input[@class='button-1 login-button']")).click();
		
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		Assert.assertEquals(driver.getTitle(), "nopCommerce demo store.j Login");
	}
	
	@Test(priority=3)
	public void Resgistration() {
		test = extent.createTest("Registration");
		throw new SkipException("skipping this test");
	}
	
	@AfterClass
	public void teardown() {
		driver.quit();
	}
	
	
	@AfterMethod
	public void results(ITestResult result) throws IOException {
	
		if(result.getStatus()==ITestResult.FAILURE) {
			test.log(Status.FAIL, "Test case failed is "+ result.getName());
			test.log(Status.FAIL, "Test case failed is)"+ result.getThrowable());
			
			String Screenshotpath = Tests.getscreenshot(driver, result.getName());
			test.addScreenCaptureFromPath(Screenshotpath);
			
			
		}else if(result.getStatus()==ITestResult.SKIP) {
			test.log(Status.SKIP, "Test case skipped is"+ result.getName());
			
		}
		else if(result.getStatus()==ITestResult.SUCCESS) {
			test.log(Status.PASS, "Test case passed is" + result.getName());
		}
	}
	
	
	
	public static String getscreenshot(WebDriver driver , String Screenshotname) throws IOException {
		
		String datename = new SimpleDateFormat("yyyymmddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		
		String dest = System.getProperty("user.dir")+"/screenshot/"+ Screenshotname + datename + ".png";
		File finaldest = new File(dest);
		FileUtils.copyFile(source, finaldest);
		return dest;
	}
	
	
	
	
	
	
	
	
	
	
	
}
