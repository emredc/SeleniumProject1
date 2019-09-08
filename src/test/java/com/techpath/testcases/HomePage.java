package com.techpath.testcases;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.techpath.utilities.ConfigReader;

public class HomePage {

	WebDriver driver;
	ConfigReader reader = new ConfigReader();
	Logger logger;

	@BeforeMethod
	public void setup() {

		logger = Logger.getLogger("HomePage"); // it was techpath when we are testing the LoginTest
		PropertyConfigurator.configure("log4j.properties");

		System.setProperty("webdriver.chrome.driver", "./drivers\\chromedriver.exe");

		driver = new ChromeDriver();

		driver.get(reader.getWebURL());

		logger.info("The website URL is working");

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(45, TimeUnit.SECONDS);

	}

	@Test (enabled = false)
	public void HomePage() throws InterruptedException {

		WebElement MacImage = driver.findElement(By.xpath("//*[@id=\"content\"]/div[2]/div[1]/div/div[1]/a/img"));

		MacImage.click();

		logger.info("Macbook image is clicked");

		WebElement Wish = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[2]/div[1]/button[1]"));

		Wish.click();

		logger.info("Added wish list");

		WebElement ClickedWishList = driver.findElement(By.xpath("//*[@id=\"wishlist-total\"]/span"));

		ClickedWishList.click();

		logger.info("Clicked wish list");

		System.out.println(driver.getTitle());
		AssertJUnit.assertEquals(driver.getTitle(), "Account Login");
		
		WebElement userNameTextField = driver.findElement(By.name("email"));
		userNameTextField.sendKeys(reader.getUserName());
		logger.info("Username is inserted");

		WebElement passwordTextField = driver.findElement(By.name("password"));
		passwordTextField.sendKeys(reader.getPassword());

		WebElement loginButton2 = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[2]/div/form/input"));
		loginButton2.click();


		Thread.sleep(5000);

	}

	@Test (enabled = false)

	public void test2() throws InterruptedException {
	
		WebElement desktop = driver .findElement(By.xpath("//*[@id=\"menu\"]/div[2]/ul/li[1]/a"));
		desktop.click();
		
		WebElement pcZero = driver.findElement(By.xpath("//*[@id=\"menu\"]/div[2]/ul/li[1]/div/div/ul/li[1]/a"));

		pcZero.click();
		
		AssertJUnit.assertTrue(driver.getTitle().contentEquals("PC"));
		logger.info("PC text is clickable");
		

	}
	
	@Test
	public void test3() throws InterruptedException {
		driver.get(reader.getWebURL());
		
		WebElement icon = driver.findElement(By.xpath("//span[@id='cart-total'][.='0 item(s) - $0.00']"));
		AssertJUnit.assertTrue(icon.isDisplayed());
		icon.click();
	}

	@AfterMethod
	

	public void tearDown() {

		driver.quit();
	}
}
