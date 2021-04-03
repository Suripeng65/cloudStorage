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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import io.github.bonigarcia.wdm.WebDriverManager;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NoteTest {
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
		this.webDriverWait = new WebDriverWait(driver, 5);
		this.js = (JavascriptExecutor) driver;
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
	public void createAndDeleteNote(){
		Assertions.assertDoesNotThrow(() -> {
			this.driver.findElement(By.xpath("//th[text()='Test Title Created']"));
			this.driver.findElement(By.xpath("//td[text()='Test Description Created']"));
		});
		
		WebElement deleteButton = this.driver.findElement(
				By.xpath("//*[@id='userTable']/tbody/tr/td[1]/a"));
		this.webDriverWait.until(ExpectedConditions.elementToBeClickable(deleteButton));
		js.executeScript("arguments[0].click();", deleteButton);
		
		this.goToHomePage();
		
		Assertions.assertThrows(NoSuchElementException.class, () -> {
			this.driver.findElement(By.xpath("//th[text()='Test Title Created']"));
			this.driver.findElement(By.xpath("//td[text()='Test Description Created']"));
		});
	}
	
	@Test
	@Order(2)
	public void updateNote(){
		Assertions.assertDoesNotThrow(() -> {
			this.driver.findElement(By.xpath("//th[text()='Test Title Created']"));
			this.driver.findElement(By.xpath("//td[text()='Test Description Created']"));
		});
		
		WebElement editButton = this.driver.findElement(
				By.xpath("//*[@id='userTable']/tbody/tr/td[1]/button"));
		this.webDriverWait.until(ExpectedConditions.elementToBeClickable(editButton));
		js.executeScript("arguments[0].click();", editButton);
		
		WebElement noteDescriptionField = this.driver.findElement(By.id("note-update-description"));
		this.webDriverWait.until(ExpectedConditions.visibilityOf(noteDescriptionField));
		js.executeScript("arguments[0].value='" + "Test Updated Note" + "';", noteDescriptionField);
		
		WebElement noteForm = this.driver.findElement(By.id("note-update-form"));
		noteForm.submit();
		
		this.goToHomePage();
		
		Assertions.assertDoesNotThrow(() -> {
			this.driver.findElement(By.xpath("//td[text()='Test Updated Note']"));
		});
		
	}
	
	private void goToHomePage() {
		driver.get("http://localhost:" + this.port + "/home");
		WebElement noteTab = this.driver.findElement(By.id("nav-notes-tab"));
		this.webDriverWait.until(ExpectedConditions.elementToBeClickable(noteTab));
		js.executeScript("arguments[0].click();", noteTab);
	}
	
	private void insertNewNote() throws InterruptedException{
		this.driver.get("http://localhost:" + this.port + "/home");
		WebElement noteTab = this.driver.findElement(By.id("nav-notes-tab"));
		this.webDriverWait.until(ExpectedConditions.elementToBeClickable(noteTab));
		js.executeScript("arguments[0].click();", noteTab);
		
		WebElement noteCreationButton = this.driver.findElement(By.id("create-note"));	
//		Assertions.assertNotNull(noteCreationButton);
		this.webDriverWait.until(ExpectedConditions.elementToBeClickable(noteCreationButton));
		js.executeScript("arguments[0].click();", noteCreationButton);
		
		WebElement noteTitleField = this.driver.findElement(By.id("note-title"));		
		this.webDriverWait.until(ExpectedConditions.visibilityOf(noteTitleField));
		js.executeScript("arguments[0].value='" + "Test Title Created" + "';", noteTitleField);
		
		WebElement noteDescriptionField = this.driver.findElement(By.id("note-description"));
		this.webDriverWait.until(ExpectedConditions.visibilityOf(noteDescriptionField));
		js.executeScript("arguments[0].value='" + "Test Description Created" + "';", noteDescriptionField);
		
		WebElement noteForm = this.driver.findElement(By.id("note-form"));
		noteForm.submit();
		
	}
	
	@Test
	private void signupAndLogin() {
		this.signup();
		this.login();
	}
	
	private void signup() {
		driver.get("http://localhost:" + this.port + "/signup");

		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		js.executeScript("arguments[0].value='" + "testFirstname" + "';", inputFirstName);


		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		js.executeScript("arguments[0].value='" + "testLastname" + "';", inputLastName);


		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		js.executeScript("arguments[0].value='" + "testUser1234" + "';", inputUsername);

		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		js.executeScript("arguments[0].value='" + "testPassword1234" + "';", inputPassword);


		WebElement signupBtn = driver.findElement(By.id("signupBtn"));
		js.executeScript("arguments[0].click();", signupBtn);
	}
	
	private void login() {
		driver.get("http://localhost:" + this.port + "/login");

		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		js.executeScript("arguments[0].value='" + "testUser1234" + "';", inputUsername);

		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		js.executeScript("arguments[0].value='" + "testPassword1234" + "';", inputPassword);

		WebElement loginButton = driver.findElement(By.id("login-btn"));
		js.executeScript("arguments[0].click();", loginButton);
	}

}
