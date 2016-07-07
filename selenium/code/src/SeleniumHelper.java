import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class SeleniumHelper {

	public static Collection<String> supportedBrowsers() {
		return Arrays.asList(new String[] { "firefox", "chrome", "ie" });
	}
	
	public static WebDriver getDriver(String browser) throws Exception {
		WebDriver driver = null;
		
		if (browser == "firefox") {
			driver = new FirefoxDriver();
			
		} else if (browser == "chrome") {
			// this property is set in build.xml
			// System.setProperty("webdriver.chrome.driver", "path to chromedriver.exe");
			
			driver = new ChromeDriver();
			
		} else if (browser == "ie") {
			// this property is set in build.xml
			// System.setProperty("webdriver.ie.driver", "path to IEDriverServer.exe");
			
			// if you get an exception with IE 11 "Unable to get browser", check your Internet / Security settings:
			// (a) Add http://localhost:8079 to the list of trusted web site
			// (b) Make sure Enhanced Protected Mode is turned off.
			
			driver = new InternetExplorerDriver();
			
		} else {
			throw new Exception("browser not supported");
		}
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		return driver;
	}
}
