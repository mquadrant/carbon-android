import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeTest;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class BaseClass extends ExtentsReportDoc {

    AndroidDriver<AndroidElement> driver;

    @BeforeTest
    public void setUp(){
        try{
            DesiredCapabilities caps = new DesiredCapabilities();

            caps.setCapability(MobileCapabilityType.DEVICE_NAME, DeviceSpec.DEVICE_NAME);
            caps.setCapability(MobileCapabilityType.UDID, DeviceSpec.UDID);
            caps.setCapability(MobileCapabilityType.PLATFORM_NAME, DeviceSpec.PLATFORM_NAME);
            caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, DeviceSpec.PLATFORM_VERSION);
            caps.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 60);

            caps.setCapability("appPackage", DeviceSpec.APP_PACKAGE);
            caps.setCapability("appActivity", DeviceSpec.APP_ENTRY_POINT);
            caps.setCapability("noReset", true);

            caps.setCapability("unicodeKeyboard", "true");
            caps.setCapability("resetKeyboard", "true");


            URL url = new URL("http://127.0.0.1:4723/wd/hub");

            //Instantiate Appium Driver
            driver = new AndroidDriver<>(url,caps);
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            System.out.println("Application Started....");

        }
        catch(MalformedURLException exp){
            System.out.println("Cause is : "+exp.getCause());
            System.out.println("Message is : "+exp.getMessage());
        }

//        Wait  about 4 seconds
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AfterSuite
    public void tearDown() {
//        driver.quit();
    }
}
