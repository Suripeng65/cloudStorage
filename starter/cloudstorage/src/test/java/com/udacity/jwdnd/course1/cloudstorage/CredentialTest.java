package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.NoSuchElementException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import io.github.bonigarcia.wdm.WebDriverManager;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CredentialTest {
	@LocalServerPort
	private int port;
	
	private WebDriver driver;
	private WebDriverWait webDriverWait;
	
	private JavascriptExecutor js;
	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}
	
	@BeforeEach
	public void beforeEach() throws InterruptedException{
		this.driver = new ChromeDriver();
//		this.driver = new SafariDriver();
		this.webDriverWait = new WebDriverWait(driver, 5);
		this.js = (JavascriptExecutor) driver;
		this.signupAndLogin();
		this.insertNewCredential();
		this.goToHomePage();
//		Thread.sleep(2000);
		System.out.println("done with before each");
	}
	
	@AfterEach
	public void afterEach() {
		if(this.driver != null) {
			driver.quit();
		}
	}


	
	@Test
	@Order(1)
	public void createAndDeleteCredential(){
		System.out.println("start testing delete");
		
		Assertions.assertDoesNotThrow(() -> {
			this.driver.findElement(By.xpath("//table[@id='credentialTable']/tbody/tr/th[text()='www.google.com']"));

		});
		
		WebElement deleteButton = this.driver.findElement(
				By.xpath("//*[@id='credentialTable']/tbody/tr/td[1]/a"));
		this.webDriverWait.until(ExpectedConditions.elementToBeClickable(deleteButton));
		js.executeScript("arguments[0].click();", deleteButton);
		
		this.goToHomePage();

		Assertions.assertThrows(NoSuchElementException.class, () -> {
			this.driver.findElement(By.xpath("//th[text()='www.google.com']"));
		});
	}
	
	@Test
	@Order(2)
	public void updateCredential(){
		Assertions.assertDoesNotThrow(() -> {
			this.driver.findElement(By.xpath("//th[text()='www.google.com']"));
		});
		
		WebElement editButton = this.driver.findElement(
				By.xpath("//*[@id='credentialTable']/tbody/tr/td[1]/button"));
		this.webDriverWait.until(ExpectedConditions.elementToBeClickable(editButton));
		js.executeScript("arguments[0].click();", editButton);
		
		WebElement credentialUrl = this.driver.findElement(By.id("credential-url-update"));
		this.webDriverWait.until(ExpectedConditions.visibilityOf(credentialUrl));
		js.executeScript("arguments[0].value='" + "www.newurl.com" + "';", credentialUrl);
//		credentialUrl.clear();
//		credentialUrl.sendKeys("www.newurl.com");

		WebElement credentialUsername = this.driver.findElement(By.id("credential-username-update"));
		js.executeScript("arguments[0].value='" + "updateKey" + "';", credentialUsername);	
//		credentialUsername.clear();
//		credentialUsername.sendKeys("updateKey");		 

		WebElement credentialForm = this.driver.findElement(By.id("credential-update-form"));
		credentialForm.submit();
		this.goToHomePage();
		Assertions.assertDoesNotThrow(() -> {
			this.driver.findElement(By.xpath("//th[text()='www.newurl.com']"));
			this.driver.findElement(By.xpath("//td[text()='updateKey']"));
		});
		
	}
	
	private void goToHomePage(){
		this.driver.get("http://localhost:" + this.port + "/home");
		System.out.println("refresh home page");
		WebElement credentialTab = this.driver.findElement(By.id("nav-credentials-tab"));
		this.webDriverWait.until(ExpectedConditions.elementToBeClickable(credentialTab));
		js.executeScript("arguments[0].click();", credentialTab);
//		credentialTab.click();
	}
	
	public void insertNewCredential() {
		this.driver.get("http://localhost:" + this.port + "/home");
		WebElement credentialTab = this.driver.findElement(By.id("nav-credentials-tab"));
		this.webDriverWait.until(ExpectedConditions.elementToBeClickable(credentialTab));
		credentialTab.click();
		
		WebElement credentialCreateBtn = this.driver.findElement(By.id("credential-create"));	
		this.webDriverWait.until(ExpectedConditions.elementToBeClickable(credentialCreateBtn));
//		credentialCreateBtn.click();
		js.executeScript("arguments[0].click();", credentialCreateBtn);
		
		WebElement urlInput = this.driver.findElement(By.id("credential-url"));		
		this.webDriverWait.until(ExpectedConditions.visibilityOf(urlInput));
//		urlInput.clear();
//		urlInput.sendKeys("www.google.com");
		js.executeScript("arguments[0].value='" + "www.google.com" + "';", urlInput);
		
		WebElement credentialUsername = this.driver.findElement(By.id("credential-username"));		
//		credentialUsername.sendKeys("testUsername");
		js.executeScript("arguments[0].value='" + "testUsername" + "';", credentialUsername);
		
		WebElement passwordInput = this.driver.findElement(By.id("credential-password"));
//		passwordInput.sendKeys("testKey");
		js.executeScript("arguments[0].value='" + "testKey" + "';", passwordInput);

		WebElement credentialForm = this.driver.findElement(By.id("credential-form"));
		credentialForm.submit();
		
		this.goToHomePage();
	}
	
	private void signupAndLogin() throws InterruptedException{
		this.signup();
		this.login();
	}
	
	private void signup() throws InterruptedException{
		driver.get("http://localhost:" + this.port + "/signup");
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
//		inputFirstName.sendKeys("testFirstname");
		js.executeScript("arguments[0].value='" + "testFirstname" + "';", inputFirstName);
		

		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
//		inputLastName.sendKeys("testLastname");
		js.executeScript("arguments[0].value='" + "testLastname" + "';", inputLastName);
		
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
//		inputUsername.sendKeys("testUser1234");
		js.executeScript("arguments[0].value='" + "testUser1234" + "';", inputUsername);
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
//		inputPassword.sendKeys("testPassword1234");
		js.executeScript("arguments[0].value='" + "testPassword1234" + "';", inputPassword);
		
		WebElement signupBtn = driver.findElement(By.id("signupBtn"));
//		signupBtn.click();
		js.executeScript("arguments[0].click();", signupBtn);
		
//		Thread.sleep(2000);

	}
	private void login() throws InterruptedException{

		driver.get("http://localhost:" + this.port + "/login");
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
//		inputUsername.sendKeys("testUser1234");
		js.executeScript("arguments[0].value='" + "testUser1234" + "';", inputUsername);
		

		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
//		inputPassword.sendKeys("testPassword1234");
		js.executeScript("arguments[0].value='" + "testPassword1234" + "';", inputPassword);
		
		WebElement loginButton = driver.findElement(By.id("login-btn"));
//		loginButton.click();
		js.executeScript("arguments[0].click();", loginButton);
//		Thread.sleep(2000);
	}
	
}
