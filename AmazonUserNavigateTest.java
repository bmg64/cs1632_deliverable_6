import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * As an Amazon customer, I want to be able to navigate the shopping cart, wishlist and
 * orders screens properly, so that I may manage my product viewing and purchasing.
 * @author Ben
 *
 */
public class AmazonUserNavigateTest {

	protected static WebDriver driver = new HtmlUnitDriver(true);

	@Before
	public void setUp() throws Exception {
		driver.get("https://www.amazon.com");
	}

	// Given that I am on the main page
	// When I view the topbar
	// Then I see a button that links to my cart
	@Test
	public void testHasCartButton(){
		try {
			driver.findElement(By.id("nav-cart"));
		} catch (NoSuchElementException nseex) {
			fail();
		}
		String elementText = driver.findElement(By.id("nav-cart")).getAttribute("innerHTML");
		assertTrue(elementText.contains("Cart"));
	}

	// Given that I am on the Cart page and my cart is empty
	// When I view the main section
	// Then I see a prompt to sign in
	@Test
	public void testSignInPrompt(){
		driver.get("https://www.amazon.com/gp/cart/view.html/ref=nav_cart");
		try {
			driver.findElement(By.className("sc-empty-cart"));
		} catch (NoSuchElementException nseex) {
			fail();
		}
		String elementText = driver.findElement(By.className("sc-empty-cart")).getAttribute("innerHTML");
		assertTrue(elementText.contains("Sign In"));
	}

	// Given that I'm in the cart page
	// When I click the Wish List link
	// Then I am taken to a page where I can create a list
	@Test
	public void testWishList(){
		driver.get("https://www.amazon.com/gp/cart/view.html/ref=nav_cart");
		driver.findElement(By.cssSelector("a[href*='wishlist']")).click();
		String elementText = "";
		try{
			elementText = driver.findElement(By.className("a-button-inner")).getAttribute("innerHTML");
		} catch (NoSuchElementException nseex) {
			fail();
		}
		assertTrue(elementText.contains("Create a List"));
	}

	// Given that my cart is empty
	// When I add an item to my cart
	// Then my cart is no longer empty
	@Test
	public void testAddToCart(){
		driver.get("https://www.amazon.com/gp/cart/view.html/ref=nav_cart");
		driver.findElement(By.cssSelector("input[name='submit.addToCart']")).click();
		String elementText = "";
		try{
			elementText = driver.findElement(By.className("sc-cart-header")).getAttribute("innerHTML");
		} catch (NoSuchElementException nseex) {
			fail();
		}
		assertTrue(!elementText.contains("Your Shopping Cart is empty."));
	}

	// Given that I am on the main page
	// When I click the link to the account page
	// Then I see a link to view my past orders
	@Test
	public void testAccountOrders(){
		driver.findElement(By.linkText("Your Account")).click();
		String elementText = "";
		try{
			elementText = driver.findElement(By.cssSelector("your-orders-button-announce")).getAttribute("innerHTML");
		} catch (NoSuchElementException nseex) {
			fail();
		}
		assertTrue(elementText.contains("Your Orders"));
	}
}
