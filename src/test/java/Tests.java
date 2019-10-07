import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import helpers.RandomString;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.apache.http.util.Asserts;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.FileAssert.fail;

public class Tests extends BaseClass {

    private String loginPhoneNumber = "08990001099";
    private String loginPin = "1234";
    private String wrongPhoneNumber = "080"+new RandomString(9).getString(8);
    private String wrongPin = new RandomString(9).getString(4);
    private String verificationCode = "123456";

    @Test
    public void sampleTest(){
        System.out.println("I am inside sample test");
    }

    @Test
    public void navigateToSignInTest(){
        // creates a toggle for the given test, adds all log events under it
        ExtentTest navigateSignIn = extent.createTest("Welcome Page", "Test Scenario for Navigation to Sign In");

        // log(Status, details)
        navigateSignIn.log(Status.INFO, "Navigation Test Started");

        driver.findElement(By.id("com.lenddo.mobile.paylater.staging:id/user_type_existing")).click();
        try{
            Assert.assertEquals(driver.findElementById("com.lenddo.mobile.paylater.staging:id/sign_in_next").getText(),"Sign in");
            navigateSignIn.log(Status.PASS, "Navigate to Login View");
        }catch(AssertionError ex){
            navigateSignIn.log(Status.FAIL, "Cant Sign in on this view");
        }

    }

    @Test
    public void signInTest(){
        // creates a toggle for the given test, adds all log events under it
        ExtentTest testSignIn = extent.createTest("SignIn", "Test Scenario when the user login");

        // log(Status, details)
        testSignIn.log(Status.INFO, "SignIn Test Started");

        driver.findElement(By.id("com.lenddo.mobile.paylater.staging:id/sign_in_next")).click();
        try{
            Assert.assertEquals(driver.findElementById("com.lenddo.mobile.paylater.staging:id/textinput_error").getText(),"Input your phone number");
            testSignIn.log(Status.PASS, "form validate is implemented");
        }catch(AssertionError ex){
            testSignIn.log(Status.FAIL, "form validate is not implemented");
        }

        driver.findElement(By.id("com.lenddo.mobile.paylater.staging:id/sign_in_phone")).click();
        driver.findElement(By.id("com.lenddo.mobile.paylater.staging:id/sign_in_phone")).sendKeys(wrongPhoneNumber);
        driver.findElement(By.id("com.lenddo.mobile.paylater.staging:id/sign_in_pin")).click();
        driver.findElement(By.id("com.lenddo.mobile.paylater.staging:id/sign_in_pin")).sendKeys(wrongPin);
        if(driver.isKeyboardShown()){
            driver.pressKey(new KeyEvent(AndroidKey.ENTER));
        }
        driver.findElement(By.id("com.lenddo.mobile.paylater.staging:id/sign_in_next")).click();

        try{
            Assert.assertEquals(driver.findElementById("com.lenddo.mobile.paylater.staging:id/alertTitle").getText(),"Error");
            testSignIn.log(Status.PASS, "Should not Login with incorrect credential");
        }catch(AssertionError ex){
            testSignIn.log(Status.FAIL, "Login with incorrect credential");
        }
        driver.findElement(By.id("android:id/button1")).click();

        driver.findElement(By.id("com.lenddo.mobile.paylater.staging:id/sign_in_phone")).click();
        driver.findElement(By.id("com.lenddo.mobile.paylater.staging:id/sign_in_phone")).sendKeys(loginPhoneNumber);
        driver.findElement(By.id("com.lenddo.mobile.paylater.staging:id/sign_in_pin")).click();
        driver.findElement(By.id("com.lenddo.mobile.paylater.staging:id/sign_in_pin")).sendKeys(loginPin);
        if(driver.isKeyboardShown()){
            driver.pressKey(new KeyEvent(AndroidKey.ENTER));
        }
        driver.findElement(By.id("com.lenddo.mobile.paylater.staging:id/sign_in_next")).click();
        testSignIn.log(Status.PASS, "Login to verification view");
    }

}
