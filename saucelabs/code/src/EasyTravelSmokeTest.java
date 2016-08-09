import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.remote.RemoteWebDriver;


import com.saucelabs.saucerest.*;

@RunWith(Parameterized.class)
public class EasyTravelSmokeTest {
	
	private WebDriver driver;
	private SauceREST sauceClient;
	
	private String baseUrl;
	private String platform;
	
	private String testRunId;
	private boolean testSuccess;
	

	public EasyTravelSmokeTest(String platform) {
		this.platform = platform;
		this.testRunId = System.getProperty("dtTestrunID");
		sauceClient = new SauceREST(SeleniumHelper.getUserName(), SeleniumHelper.getAccessKey());
	}

	@Parameterized.Parameters
	public static Collection<String> browsers() {
		return SeleniumHelper.supportedPlatform();
	}

	@Before
	public void setUp() throws Exception {
		driver = SeleniumHelper.getDriver(platform);
		// TODO: replace easytravel URL by an easytravel reachable from the Internet 
		// or configure Sauce Lab Tunnel in your account https://saucelabs.com/beta/tunnels
		baseUrl = "http://localhost:8079";
	}

	@Test
	public void testClickAllPages() throws Exception {
		
		testSuccess = false;
		
		// start browser with easyTravel base URL
		driver.get(baseUrl + "/");
		WebDriverWait wait = new WebDriverWait(driver, 5);
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		
		wait.until(ExpectedConditions.elementToBeClickable(By.id("loginForm:loginLink")));

		// dynatrace: set test name
		String testName = "EasyTravel.SmokeTest.ClickAllPages";
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("sessionStorage.DT_TESTNAME = \"" + testName + "\";");
		if(testRunId != null) {
			js.executeScript("sessionStorage.DT_TESTRUNID = \"" + testRunId + "\";");
		}

		driver.findElement(By.id("loginForm:loginLink")).click();
		driver.findElement(By.id("loginForm:username")).clear();
		driver.findElement(By.id("loginForm:username")).sendKeys("greta");
		driver.findElement(By.id("loginForm:password")).clear();
		driver.findElement(By.id("loginForm:password")).sendKeys("greta");
		driver.findElement(By.id("loginForm:loginSubmit")).click();
		
		wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Home")));
		driver.findElement(By.linkText("Home")).click();
	
		wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Special Offers")));
		driver.findElement(By.linkText("Special Offers")).click();
		
		wait.until(ExpectedConditions.elementToBeClickable(By.linkText("About")));
		driver.findElement(By.linkText("About")).click();
		
		wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Contact")));
		driver.findElement(By.linkText("Contact")).click();
		
		wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Terms of Use")));
		driver.findElement(By.linkText("Terms of Use")).click();
		
		wait.until(ExpectedConditions.elementToBeClickable(By.id("loginForm:logoutLink")));
		driver.findElement(By.id("loginForm:logoutLink")).click();
		
		wait.until(ExpectedConditions.elementToBeClickable(By.id("loginForm:loginLink")));
		WebElement confirmation = driver.findElement(By.id("loginForm:loginLink"));
		String confirmationMsg = confirmation.getText();
		Assert.assertEquals("Login", confirmationMsg);

		testSuccess = true;
		
	}

	@After
	public void tearDown() throws Exception {
		String sessionId = ((RemoteWebDriver) driver).getSessionId().toString();
		if(testSuccess){
			sauceClient.jobPassed(sessionId);
		}
		else {
			sauceClient.jobFailed(sessionId);
		}
		
		// dynatrace: end visit
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("dynaTrace.endVisit();");

		driver.quit();
	}

}
