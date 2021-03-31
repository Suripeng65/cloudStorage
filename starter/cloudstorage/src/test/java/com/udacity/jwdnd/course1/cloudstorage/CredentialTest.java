package com.udacity.jwdnd.course1.cloudstorage;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
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
	
	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}
	
	@BeforeEach
	public void beforeEach() throws InterruptedException{
		this.driver = new ChromeDriver();
		this.webDriverWait = new WebDriverWait(driver, 500);
		this.signupAndLogin();
		this.insertNewCredential();
		Thread.sleep(1000);
	}
	
	@AfterEach
	public void afterEach() {
		if(this.driver != null) {
			driver.quit();
		}
	}
	
	@Test
	@Order(1)
	public void createAndDeleteCredential() throws InterruptedException{
		Assertions.assertDoesNotThrow(() -> {
			this.driver.findElement(By.xpath("//th[text()='www.google.com']"));
			
		});
		
		WebElement deleteButton = this.driver.findElement(
				By.xpath("//*[@id='credentialTable]/tbody/tr/td[1]/a"));
		this.webDriverWait.until(ExpectedConditions.elementToBeClickable(deleteButton));
		deleteButton.click();
		
		this.goToHomePage();
		
		Assertions.assertThrows(NoSuchElementException.class, () -> {
			this.driver.findElement(By.xpath("//th[text()='www.google.com']"));
		});
	}
	
	@Test
	@Order(2)
	public void updateCredential() throws InterruptedException{
		Assertions.assertDoesNotThrow(() -> {
			this.driver.findElement(By.xpath("//th[text()='www.google.com']"));
		});
		
		WebElement editButton = this.driver.findElement(
				By.xpath("//*[@id='credentialTable]/tbody/tr/td[1]/button"));
		this.webDriverWait.until(ExpectedConditions.elementToBeClickable(editButton));
		editButton.click();
		
		WebElement credentialUrl = this.driver.findElement(By.id("credential-url-update"));		
		this.webDriverWait.until(ExpectedConditions.visibilityOf(credentialUrl));
		credentialUrl.clear();
		credentialUrl.sendKeys("abc");
		
		WebElement credentialUsername = this.driver.findElement(By.id("credential-username-update"));		
		credentialUsername.clear();
		credentialUsername.sendKeys("test 2 description");
		
		WebElement credentialForm = this.driver.findElement(By.id("credential-update-form"));
		credentialForm.submit();
		this.goToHomePage();
		Assertions.assertDoesNotThrow(() -> {
			this.driver.findElement(By.xpath("//th[text()='test 2']"));
			this.driver.findElement(By.xpath("//td[text()='test 2 description']"));
		});
		
	}
	
	private void goToHomePage() {
		driver.get("http://localhost:" + this.port + "/home");
		WebElement credentialTab = this.driver.findElement(By.id("nav-credentials-tab"));
		this.webDriverWait.until(ExpectedConditions.elementToBeClickable(credentialTab));
		credentialTab.click();
	}
	
	public void insertNewCredential() {
		this.driver.get("http://localhost:" + this.port + "/home");
		WebElement credentialTab = this.driver.findElement(By.id("nav-credentials-tab"));
		this.webDriverWait.until(ExpectedConditions.elementToBeClickable(credentialTab));
		credentialTab.click();
		
		WebElement credentialCreateBtn = this.driver.findElement(By.id("credential-create"));	
		this.webDriverWait.until(ExpectedConditions.elementToBeClickable(credentialCreateBtn));
		credentialCreateBtn.click();
		
		WebElement urlInput = this.driver.findElement(By.id("credential-url"));		
		this.webDriverWait.until(ExpectedConditions.visibilityOf(urlInput));
		urlInput.clear();
		urlInput.sendKeys("www.test.com");
		
		WebElement credentialUsername = this.driver.findElement(By.id("credential-username"));		
		credentialUsername.sendKeys("test username");
		
		WebElement passwordInput = this.driver.findElement(By.id("credential-password"));
		passwordInput.sendKeys("testkey");
		
		WebElement credentialForm = this.driver.findElement(By.id("credential-form"));
		credentialForm.submit();
		
		this.goToHomePage();
	}
	
	@Test
	private void signupAndLogin() throws InterruptedException{
		this.signup();
		this.login();
	}
	
	private void signup() throws InterruptedException{
		//test signup
		driver.get("http://localhost:" + this.port + "/signup");
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.sendKeys("test");
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.sendKeys("test");
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.sendKeys("test1234");
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.sendKeys("test1234");
		WebElement signupBtn = driver.findElement(By.id("signupBtn"));
		signupBtn.click();
		Thread.sleep(1000);

	}
	private void login() throws InterruptedException{
		//test login
		driver.get("http://localhost:" + this.port + "/login");
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.sendKeys("test");
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.sendKeys("test1234");
		WebElement loginButton = driver.findElement(By.id("login-btn"));
		loginButton.click();
		Assertions.assertEquals("Login", driver.getTitle());
		Thread.sleep(1000);
	}
}
