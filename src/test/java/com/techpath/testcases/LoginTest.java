package com.techpath.testcases;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.techpath.utilities.ConfigReader;
import com.techpath.utilities.ScreenShotTaker;

public class LoginTest {

	WebDriver driver;
	ConfigReader reader = new ConfigReader();
	Logger logger;

	@Parameters("browser")
	@BeforeClass

	public void setup(String browser) {

//		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\drivers\\chromedriver.exe");

		logger = Logger.getLogger("techpath");
		PropertyConfigurator.configure("log4j.properties");

		if (browser.contentEquals("chrome")) {

			// System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")
			// + "//drivers//chromedriver.exe");

			System.setProperty("webdriver.chrome.driver", reader.getChromePath());
			driver = new ChromeDriver();

		} else if (browser.contentEquals("firefox")) {

			System.setProperty("webdriver.gecko.driver", reader.getFirefoxPath());
			driver = new FirefoxDriver();

		} else if (browser.contentEquals("ie")) {

			System.setProperty("webdriver.ie.driver", reader.getIEPath());
			driver = new InternetExplorerDriver();

		}

		System.setProperty("webdriver.chrome.driver", "./drivers\\chromedriver.exe");

		driver = new ChromeDriver();

		driver.get(reader.getWebURL());

		logger.info("The website URL is working");

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(45, TimeUnit.SECONDS);

	}

	@Test

	public void Login() throws Exception {

		WebElement myAcctButton = driver.findElement(By.xpath("//*[@id=\"top-links\"]/ul/li[2]/a"));
		myAcctButton.click();

		logger.info("My account button is clicked");

		WebElement loginButton1 = driver.findElement(By.xpath("//*[@id=\"top-links\"]/ul/li[2]/ul/li[2]/a"));
		loginButton1.click();

		WebElement userNameTextField = driver.findElement(By.name("email"));
		userNameTextField.sendKeys(reader.getUserName());
		logger.info("Username is inserted");

		WebElement passwordTextField = driver.findElement(By.name("password"));
		passwordTextField.sendKeys(reader.getPassword());

		WebElement loginButton2 = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[2]/div/form/input"));
		loginButton2.click();

		System.out.println(driver.getTitle());

		if (driver.getTitle().contentEquals("My Account")) {

			logger.info("My account title has matched");
		} else {

			logger.error("My account title did not matched");
			logger.info("The title is shown as " + driver.getTitle());
			ScreenShotTaker.captureScreen(driver, "logintomyAccount");
		}

		Thread.sleep(5000);
	}

	@AfterMethod
	@AfterClass

	public void tearDown() {

		driver.quit();
	}
}
