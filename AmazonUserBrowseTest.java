import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

/**
 * As an Amazon customer, I want to be able to browse products in multiple ways,
 * so that I may find what I am looking for.
 * @author Ben
 *
 */
public class AmazonUserBrowseTest {

	protected static WebDriver driver = new HtmlUnitDriver(true);

	@Before
	public void setUp() throws Exception {
		driver.get("https://www.amazon.com");
	}

	// Given that I am on the main page
	// When I click Today's Deals
	// Then I am taken to the relevant page
	@Test
	public void testTodaysDeals(){
		driver.findElement(By.linkText("Today's Deals")).click();
		String elementText = "";
		try {
			elementText = driver.findElement(By.id("gbox-item-0.0.0")).getAttribute("innerHTML");
		} catch (NoSuchElementException nseex) {
			fail();
		}
		assertTrue(elementText.contains("Today's Deals"));
	}

	// Given that I am anywhere on the site
	// When I enter a term into the search bar
	// Then I see relevant results
	@Test
	public void testSearch(){
		String desiredSearch = "potatoes";
		WebElement searchbar = driver.findElement(By.id("twotabsearchtextbox"));
		searchbar.sendKeys(desiredSearch);
		searchbar.sendKeys(Keys.RETURN);
		String elementText = "";
		try{
			elementText = driver.findElement(By.id("s-results-list-atf")).getAttribute("innerHTML");
		} catch (NoSuchElementException nseex) {
			fail();
		}
		assertTrue(elementText.toLowerCase().contains(desiredSearch));
	}

	// Given that I am anywhere on the site
	// When I search for an item
	// Then I see suggested refinements to the search in the sidebar
	@Test
	public void testRefinements(){
		String desiredSearch = "lamp";
		WebElement searchbar = driver.findElement(By.id("twotabsearchtextbox"));
		searchbar.sendKeys(desiredSearch);
		searchbar.sendKeys(Keys.RETURN);
		String elementText = "";
		try{
			elementText = driver.findElement(By.id("refinements")).getAttribute(("innerHTML"));
		} catch (NoSuchElementException nseex) {
			fail();
		}
		assertTrue(elementText.toLowerCase().contains(desiredSearch));
	}

	// Given that I am anywhere on the site
	// When I search for an item
	// Then I see a sorting option above the search results
	@Test
	public void testSortBy(){
		String desiredSearch = "cat";
		WebElement searchbar = driver.findElement(By.id("twotabsearchtextbox"));
		searchbar.sendKeys(desiredSearch);
		searchbar.sendKeys(Keys.RETURN);
		String elementText = "";
		try{
			elementText = driver.findElement(By.id("searchSortForm")).getAttribute(("innerHTML"));
		} catch (NoSuchElementException nseex) {
			fail();
		}
		assertTrue(elementText.contains("Relevance"));
	}

	// Given that I am viewing search results
	// When I click No under the feedback section at the bottom
	// Then I am prompted to give a description of my issue
	@Test
	public void testFeedback(){
		String desiredSearch = "large hadron collider";
		WebElement searchbar = driver.findElement(By.id("twotabsearchtextbox"));
		searchbar.sendKeys(desiredSearch);
		searchbar.sendKeys(Keys.RETURN);
		driver.findElement(By.name("sx-feedback-no")).click();
		String elementText = "";
		try{
			elementText = driver.findElement(By.className("a-dropdown-prompt")).getAttribute(("innerHTML"));
		} catch (NoSuchElementException nseex) {
			fail();
		}
		assertTrue(elementText.contains("Choose a category"));
	}
}
