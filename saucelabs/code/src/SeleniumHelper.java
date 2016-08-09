import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class SeleniumHelper {
	
	// TODO: enter user name and access key from saucelabs (https://saucelabs.com/)
	private static final String USERNAME = "";
	private static final String ACCESS_KEY = "";
	private static final String URL = "http://" + USERNAME + ":" + ACCESS_KEY + "@ondemand.saucelabs.com:80/wd/hub";
	
	private static String buildNumber;

	public static Collection<String> supportedPlatform() {
		return Arrays.asList(new String[] {"firefox", "chrome", "ie", "safari", "safari-mobile", "chrome-mobile" });
	}
	
	public static WebDriver getDriver(String platform) throws Exception {
		WebDriver driver;
		DesiredCapabilities caps = null;
		
		buildNumber = System.getProperty("buildNumber");

		// see Saucelabs Platform configurator for more options:
		// https://wiki.saucelabs.com/display/DOCS/Platform+Configurator#/
		
		switch (platform) {
		case "firefox":
			caps = DesiredCapabilities.firefox();
			caps.setCapability("platform", "Windows 8.1");
			caps.setCapability("version", "47.0");
			break;
			
		case "chrome":
			caps = DesiredCapabilities.chrome();
			caps.setCapability("platform", "Windows 8.1");
		    caps.setCapability("version", "43.0");
		    break;
		    
		case "ie":
			caps = DesiredCapabilities.internetExplorer();
			caps.setCapability("platform", "Windows 10");
			caps.setCapability("version", "11.103");
			break;
			
		case "safari":
			caps = DesiredCapabilities.safari();
			caps.setCapability("platform", "OS X 10.11");
			caps.setCapability("version", "9.0");
			break;
			
		case "safari-mobile":
			caps = DesiredCapabilities.iphone();
			caps.setCapability("appiumVersion", "1.5.3");
			caps.setCapability("deviceName","iPhone 6 Simulator");
			caps.setCapability("deviceOrientation", "portrait");
			caps.setCapability("platformVersion","9.3");
			caps.setCapability("platformName", "iOS");
			caps.setCapability("browserName", "Safari");
			break;
			
		case "chrome-mobile":
			caps = DesiredCapabilities.android();
			caps.setCapability("appiumVersion", "1.5.3");
			caps.setCapability("deviceName","Samsung Galaxy S4 Emulator");
			caps.setCapability("deviceOrientation", "portrait");
			caps.setCapability("browserName", "Browser");
			caps.setCapability("platformVersion", "4.4");
			caps.setCapability("platformName","Android");
			break;

		default:
			throw new Exception("platform not supported");
			
		}
		
		
		// test name for saucelabs
		caps.setCapability("name", "EasyTravel.SmokeTest");

		// build number for saucelabs
		if(buildNumber != null) {
			caps.setCapability("build", buildNumber);
		}
		
		driver = new RemoteWebDriver(new URL(URL), caps);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		return driver;
	}
	
	public static String getUserName() {
		return USERNAME;
	}
	
	public static String getAccessKey() {
		return ACCESS_KEY;
	}
}
