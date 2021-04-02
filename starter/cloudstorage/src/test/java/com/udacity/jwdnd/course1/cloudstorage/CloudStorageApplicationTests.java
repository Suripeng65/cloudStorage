package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	private WebDriverWait webDriverWait;
	private static String firstName = "checkFirstName";
	private static String lastName = "checkLastName";
	private static String userName = "testusername";
	private static String password = "test1234";

	@BeforeAll
	static void beforeAll() {
//		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new SafariDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}
	
	@Test
	public void newUserSignupLoginLogoutTest() throws InterruptedException{
		WebDriverWait wait = new WebDriverWait(driver, 30);
		
		//test signup
		driver.get("http://localhost:" + this.port + "/signup");
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.sendKeys(firstName);
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.sendKeys(lastName);
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.sendKeys(userName);
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.sendKeys(password);
		WebElement signupBtn = driver.findElement(By.id("signupBtn"));
		signupBtn.click();
		
		//test login
		driver.get("http://localhost:" + this.port + "/login");
		inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.sendKeys(userName);
		inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.sendKeys(password);
		WebElement loginButton = driver.findElement(By.id("login-btn"));
		loginButton.click();
		System.out.println("Your page title Is : "+ driver.getTitle());
		Assertions.assertEquals("Login", driver.getTitle());
		Thread.sleep(3000);
		
		//test logout
		WebElement logoutButton = driver.findElement(By.id("logout-btn"));
		logoutButton.click();
		Thread.sleep(3000);
		System.out.println("Your page title Is : "+driver.getTitle());
		Assertions.assertEquals("Login", driver.getTitle());
	}

}
