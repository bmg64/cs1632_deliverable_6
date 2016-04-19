import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * As an Amazon manager, I want to be sure that our Prime service is understood and accessible,
 * so that users will be encouraged to sign up.
 * @author Ben
 *
 */
public class AmazonManagerPrimeTest {

	protected static WebDriver driver = new HtmlUnitDriver(true);

	@Before
	public void setUp() throws Exception {
		driver.get("https://www.amazon.com");
	}

	// Given that I am anywhere on the site
	// When I click the Try Prime link in the main logo
	// Then I am taken to the page to sign up for Prime
	@Test
	public void testMainLink(){
		driver.findElement(By.linkText("Try Prime")).click();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("plan-comparison")));
		String elementText = "";
		try{
			elementText = driver.findElement(By.id("plan-comparison")).getAttribute(("innerHTML"));
		} catch (NoSuchElementException nseex) {
			fail();
		}
		assertTrue(elementText.contains("Choose a membership"));
	}
	

	// Given that I am anywhere on the site
	// When I search for an item
	// Then I should see results with Prime benefits such as free shipping
	@Test
	public void testPrimeResults(){
		String desiredSearch = "kleenex";
		WebElement searchbar = driver.findElement(By.id("twotabsearchtextbox"));
		searchbar.sendKeys(desiredSearch);
		searchbar.sendKeys(Keys.RETURN);
		driver.findElement(By.name("sx-feedback-no")).click();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("atfResults")));
		String elementText = "";
		try{
			elementText = driver.findElement(By.id("atfResults")).getAttribute("innerHTML");
		} catch (NoSuchElementException nseex) {
			fail();
		}
		assertTrue(elementText.contains("Prime"));
		assertTrue(elementText.contains("FREE Shipping"));
	}

	// Given that I am viewing search results
	// When I click a specific product
	// Then I see an option for fast Prime shipping
	@Test
	public void testPrimeShipping(){
		String desiredSearch = "string";
		WebElement searchbar = driver.findElement(By.id("twotabsearchtextbox"));
		searchbar.sendKeys(desiredSearch);
		searchbar.sendKeys(Keys.RETURN);
		driver.findElement(By.name("sx-feedback-no")).click();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("result_1")));
		driver.findElement(By.id("result_1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fast-track-message")));
		String elementText = "";
		try{
			elementText = driver.findElement(By.id("fast-track-message")).getAttribute("innerHTML");
		} catch (NoSuchElementException nseex) {
			fail();
		}
		assertTrue(elementText.contains("Want it"));
		assertTrue(elementText.contains("Order within"));
	}

	// Given that I am viewing search results
	// When I view the sidebar on the left
	// Then I see an option to refine my search by Prime products
	@Test
	public void testRefineByPrime(){
		String desiredSearch = "ball";
		WebElement searchbar = driver.findElement(By.id("twotabsearchtextbox"));
		searchbar.sendKeys(desiredSearch);
		searchbar.sendKeys(Keys.RETURN);
		driver.findElement(By.name("sx-feedback-no")).click();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("refinements")));
		String elementText = "";
		try{
			elementText = driver.findElement(By.id("refinements")).getAttribute("innerHTML");
		} catch (NoSuchElementException nseex) {
			fail();
		}
		assertTrue(elementText.contains("Prime"));
	}

	// Given that I am anywhere on the site
	// When I hover over the Try Prime tab in the topbar
	// Then I see an offer for 50% off Prime for students
	@Test
	public void testPrimeStudent(){
		String elementText = "";
		try{
			elementText = driver.findElement(By.id("nav-student-free-two-day-college")).getAttribute("innerHTML");
		} catch (NoSuchElementException nseex) {
			fail();
		}
		assertTrue(elementText.contains("50% off Prime for College Students"));
	}
}
