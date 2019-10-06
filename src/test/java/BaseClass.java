import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeTest;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class BaseClass {

    private AppiumDriver<MobileElement> driver;

    @BeforeTest
    public void setUp(){
        try{
            DesiredCapabilities caps = new DesiredCapabilities();

            caps.setCapability(MobileCapabilityType.DEVICE_NAME, "Nexus_5_API_24");
            caps.setCapability(MobileCapabilityType.UDID, "emulator-5554");
            caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "ANDROID");
            caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, "6.0");
            caps.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 60);

            caps.setCapability("appPackage", "com.lenddo.mobile.paylater.staging");
            caps.setCapability("appActivity", "com.lenddo.mobile.paylater.home.activity.HomeActivity");
            caps.setCapability("noReset", true);


            URL url = new URL("http://127.0.0.1:4723/wd/hub");

            //Instantiate Appium Driver
            driver = new AppiumDriver<>(url,caps);
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
        driver.quit();
    }
}
