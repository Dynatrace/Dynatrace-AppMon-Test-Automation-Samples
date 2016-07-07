import java.util.Collection;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


@RunWith(Parameterized.class)
public class EasyTravelBookingTest {
	private WebDriver driver;
	private String baseUrl;
	private String browser;
	private String testRunId;

	public EasyTravelBookingTest(String browser) {
		this.browser = browser;
		this.testRunId = System.getProperty("dtTestrunID");
	}

	@Parameterized.Parameters
	public static Collection<String> browsers() {
		return SeleniumHelper.supportedBrowsers();
	}

	@Before
	public void setUp() throws Exception {
		driver = SeleniumHelper.getDriver(browser);
		baseUrl = "http://localhost:8079";
	}
	
	@Test
	public void testBookParis() throws Exception {

		// start browser with easyTravel base URL
		driver.get(baseUrl + "/");
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("iceform:destination")));

		// dynatrace: set test name
		String testName = "EasyTravel.BookingTest.BookParis";
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("sessionStorage.DT_TESTNAME = \"" + testName + "\";");
		if(testRunId != null) {
			js.executeScript("sessionStorage.DT_TESTRUNID = \"" + testRunId + "\";");
		}

		// search for trip to Paris
		driver.findElement(By.id("iceform:destination")).clear();
		
		// special handling for the ajax search form
		if (browser == "ie") {
			driver.findElement(By.id("iceform:destination")).sendKeys("P");
			driver.findElement(By.id("iceform:destination")).sendKeys(Keys.RIGHT);
			driver.findElement(By.id("iceform:destination")).sendKeys("aris");
			driver.findElement(By.id("iceform:destination")).sendKeys(Keys.RETURN);
		} else {
			driver.findElement(By.id("iceform:destination")).sendKeys("Paris");
			Thread.sleep(1000);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("iceform:search")));
		}
		
		wait.until(ExpectedConditions.elementToBeClickable(By.id("iceform:search")));
		driver.findElement(By.id("iceform:search")).click();
		
		// click on the offer
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("iceform:dataList:0:bookLink")));
		driver.findElement(By.id("iceform:dataList:0:bookLink")).click();
		
		// log-in with user michael
		wait.until(ExpectedConditions.elementToBeClickable(By.id("iceform:username")));
		driver.findElement(By.id("iceform:username")).clear();
		driver.findElement(By.id("iceform:username")).sendKeys("michael");
		driver.findElement(By.id("iceform:password")).clear();
		driver.findElement(By.id("iceform:password")).sendKeys("michael");
		
		wait.until(ExpectedConditions.elementToBeClickable(By.id("iceform:bookLoginSubmit")));
		driver.findElement(By.id("iceform:bookLoginSubmit")).click();
		
		// select 2 adults and 2 kids
		wait.until(ExpectedConditions.elementToBeClickable(By.id("iceform:travellers")));
		new Select(driver.findElement(By.id("iceform:travellers"))).selectByVisibleText("2 adults+2 kids");
		
		wait.until(ExpectedConditions.elementToBeClickable(By.id("iceform:bookReviewNext")));
		driver.findElement(By.id("iceform:bookReviewNext")).click();
		
		// fill credit card information
		wait.until(ExpectedConditions.elementToBeClickable(By.id("iceform:j_idt127")));
		driver.findElement(By.id("iceform:j_idt127")).click();
		
		wait.until(ExpectedConditions.textToBePresentInElementValue(By.id("iceform:creditCardOwner"), "Michael Knueppel"));
		driver.findElement(By.id("iceform:bookPaymentNext")).click();
		
		// click on finish
		wait.until(ExpectedConditions.elementToBeClickable(By.id("iceform:bookFinishFinish")));
		driver.findElement(By.id("iceform:bookFinishFinish")).click();
		
		// check for confirmation
		wait.until(ExpectedConditions.elementToBeClickable(By.id("iceform:j_idt79")));
				
		WebElement confirmation = driver.findElement(By.id("iceform:j_idt79"));
		String confirmationMsg = confirmation.getText();
		confirmationMsg = confirmationMsg.substring(0, 50);
		Assert.assertEquals("Thank you for using EasyTravel - Have a nice trip!", confirmationMsg);
	}

	@Test
	public void testBookSpecialOffers() throws Exception {

		// start browser with easyTravel base URL
		driver.get(baseUrl + "/");
		WebDriverWait wait = new WebDriverWait(driver, 5);

		// dynatrace: set test name
		String testName = "EasyTravel.BookingTest.BookSpecialOffers";
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("sessionStorage.DT_TESTNAME = \"" + testName + "\";");
		if(testRunId != null) {
			js.executeScript("sessionStorage.DT_TESTRUNID = \"" + testRunId + "\";");
		}

		// click on special offers
		wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Special Offers")));
		driver.findElement(By.linkText("Special Offers")).click();

		// click on the offer
		wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Book Now")));
		driver.findElement(By.linkText("Book Now")).click();

		// log-in with user maria
		wait.until(ExpectedConditions.elementToBeClickable(By.id("iceform:username")));
		driver.findElement(By.id("iceform:username")).clear();
		driver.findElement(By.id("iceform:username")).sendKeys("maria");
		driver.findElement(By.id("iceform:password")).clear();
		driver.findElement(By.id("iceform:password")).sendKeys("maria");

		wait.until(ExpectedConditions.elementToBeClickable(By.id("iceform:bookLoginSubmit")));
		driver.findElement(By.id("iceform:bookLoginSubmit")).click();

		// select 1 adult
		wait.until(ExpectedConditions.elementToBeClickable(By.id("iceform:travellers")));
		new Select(driver.findElement(By.id("iceform:travellers"))).selectByVisibleText("1 adult");

		wait.until(ExpectedConditions.elementToBeClickable(By.id("iceform:bookReviewNext")));
		driver.findElement(By.id("iceform:bookReviewNext")).click();

		// fill credit card information
		wait.until(ExpectedConditions.elementToBeClickable(By.id("iceform:j_idt127")));
		driver.findElement(By.id("iceform:j_idt127")).click();

		wait.until(
				ExpectedConditions.textToBePresentInElementValue(By.id("iceform:creditCardOwner"), "Maria O'Donnel"));
		driver.findElement(By.id("iceform:bookPaymentNext")).click();

		// click on finish
		wait.until(ExpectedConditions.elementToBeClickable(By.id("iceform:bookFinishFinish")));
		driver.findElement(By.id("iceform:bookFinishFinish")).click();
		
		// check for confirmation
		wait.until(ExpectedConditions.elementToBeClickable(By.id("iceform:j_idt79")));
				
		WebElement confirmation = driver.findElement(By.id("iceform:j_idt79"));
		String confirmationMsg = confirmation.getText();
		confirmationMsg = confirmationMsg.substring(0, 50);
		Assert.assertEquals("Thank you for using EasyTravel - Have a nice trip!", confirmationMsg);

		
	}

	@After
	public void tearDown() throws Exception {
		// dynatrace: end visit
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("dynaTrace.endVisit();");
		
		driver.quit();
	}

}
