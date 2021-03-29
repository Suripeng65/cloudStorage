package com.udacity.jwdnd.course1.cloudstorage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.web.server.LocalServerPort;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

public class NoteTest {
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
		this.insertNewNote();
		this.goToHomePage();
	}
	
	@AfterEach
	public void afterEach() {
		if(this.driver != null) {
			driver.quit();
		}
	}
	
	@Test
	@Order(1)
	public void createAndDeleteNote() throws InterruptedException{
		Assertions.assertDoesNotThrow(() -> {
			this.driver.findElement(By.xpath("//th[text()='Test Created']"));
			this.driver.findElement(By.xpath("//td[text()='this is a test']"));
		});
		WebElement deleteButton = this.driver.findElement(
				By.xpath("//*[@id='userTable]/tbody/tr/td[1]/a"));
		this.webDriverWait.until(ExpectedConditions.elementToBeClickable(deleteButton));
		deleteButton.click();
		this.goToHomePage();
		Assertions.assertThrows(NoSuchElementException.class, () -> {
			this.driver.findElement(By.xpath("//th[text()='Test Created']"));
			this.driver.findElement(By.xpath("//td[text()='this is a test']"));
		});
	}
	
	@Test
	@Order(2)
	public void updateNote() throws InterruptedException{
		Assertions.assertDoesNotThrow(() -> {
			this.driver.findElement(By.xpath("//th[text()='Test Created']"));
			this.driver.findElement(By.xpath("//td[text()='this is a test']"));
		});
		
		WebElement editButton = this.driver.findElement(
				By.xpath("//*[@id='userTable]/tbody/tr/td[1]/button"));
		this.webDriverWait.until(ExpectedConditions.elementToBeClickable(editButton));
		editButton.click();
		
		WebElement noteTitleField = this.driver.findElement(By.id("note-title"));		
		this.webDriverWait.until(ExpectedConditions.visibilityOf(noteTitleField));
		noteTitleField.clear();
		noteTitleField.sendKeys("test 2");
		
		WebElement noteDescriptionField = this.driver.findElement(By.id("note-description"));		
		noteDescriptionField.clear();
		noteDescriptionField.sendKeys("test 2 description");
		
		WebElement noteForm = this.driver.findElement(By.id("note-form"));
		noteForm.submit();
		this.goToHomePage();
		Assertions.assertDoesNotThrow(() -> {
			this.driver.findElement(By.xpath("//th[text()='test 2']"));
			this.driver.findElement(By.xpath("//td[text()='test 2 description']"));
		});
		
	}
	
	private void goToHomePage() {
		driver.get("http://localhost:" + this.port + "/home");
		WebElement noteTab = this.driver.findElement(By.id("nav-notes-tab"));
		this.webDriverWait.until(ExpectedConditions.elementToBeClickable(noteTab));
		noteTab.click();
	}
	
	private void insertNewNote() throws InterruptedException{
		this.driver.get("http://localhost:" + this.port + "/home");
		WebElement noteTab = this.driver.findElement(By.id("nav-notes-tab"));
		this.webDriverWait.until(ExpectedConditions.elementToBeClickable(noteTab));
		noteTab.click();
		
		WebElement noteCreationButton = this.driver.findElement(By.id("create-note"));	
		Assertions.assertNotNull(noteCreationButton);
		this.webDriverWait.until(ExpectedConditions.elementToBeClickable(noteCreationButton));
		noteCreationButton.click();
		
		WebElement noteTitleField = this.driver.findElement(By.id("note-title"));		
		this.webDriverWait.until(ExpectedConditions.visibilityOf(noteTitleField));
		noteTitleField.clear();
		noteTitleField.sendKeys("test 2");
		
		WebElement noteDescriptionField = this.driver.findElement(By.id("note-description"));		
		noteDescriptionField.clear();
		noteDescriptionField.sendKeys("test 2 description");
		
		WebElement noteForm = this.driver.findElement(By.id("note-form"));
		Thread.sleep(1000);
		noteForm.submit();
		
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
