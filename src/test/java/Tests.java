import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import helpers.CurrencyConverter;
import helpers.RandomString;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.apache.http.util.Asserts;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;

import static org.testng.FileAssert.fail;

public class Tests extends BaseClass {

    private String loginPhoneNumber = "08990001101,";
    private String loginPin = "1234";
    private String wrongPhoneNumber = "080"+new RandomString(9).getString(8);
    private String wrongPin = new RandomString(9).getString(4);

    @Test(priority = 1)
    public void sampleTest(){
        System.out.println("This is a sample test");
    }

    @Test(priority = 2)
    public void navigateToSignInTest(){
        // creates a toggle for the given test, adds all log events under it
        ExtentTest navigateSignIn = extent.createTest("Welcome Page", "Test Scenario for Navigation to Sign In");

        // log(Status, details)
        navigateSignIn.log(Status.INFO, "Navigation Test Started");

        driver.findElement(By.id("com.lenddo.mobile.paylater.staging:id/user_type_existing")).click();
        try{
            Assert.assertEquals(driver.findElementById("com.lenddo.mobile.paylater.staging:id/sign_in_next").getText(),"Sign in");
            navigateSignIn.log(Status.PASS, "Navigate to Login View");
        }catch(Exception ex){
            navigateSignIn.log(Status.FAIL, "Cant Sign in on this view");
        }

    }

    @Test(priority = 3)
    public void signInTest(){
        // creates a toggle for the given test, adds all log events under it
        ExtentTest testSignIn = extent.createTest("SignIn", "Test Scenario when the user login");

        // log(Status, details)
        testSignIn.log(Status.INFO, "SignIn Test Started");

        driver.findElement(By.id("com.lenddo.mobile.paylater.staging:id/sign_in_next")).click();
        try{
            Assert.assertEquals(driver.findElementById("com.lenddo.mobile.paylater.staging:id/textinput_error").getText(),"Input your phone number");
            testSignIn.log(Status.PASS, "form validate is implemented");
        }catch(Exception ex){
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
        }catch(Exception ex){
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

        try{
            Assert.assertTrue(driver.findElementByXPath("//android.widget.TextView[@text='Verify your phone number']").isDisplayed());
            //explicit wait for input field
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("android.widget.FrameLayout")));
            driver.findElements(By.className("android.widget.FrameLayout")).get(9).click();

            driver.pressKey(new KeyEvent(AndroidKey.DIGIT_1));
            driver.pressKey(new KeyEvent(AndroidKey.DIGIT_2));
            driver.pressKey(new KeyEvent(AndroidKey.DIGIT_3));
            driver.pressKey(new KeyEvent(AndroidKey.DIGIT_4));
            driver.pressKey(new KeyEvent(AndroidKey.DIGIT_5));
            driver.pressKey(new KeyEvent(AndroidKey.DIGIT_6));

            testSignIn.log(Status.PASS, "Verification successful");
        }catch(Exception ex){
            testSignIn.log(Status.PASS, "Already been verified");
            System.out.println("Already verified");
        }
    }

    @Test(priority = 4)
    public void airtimeRechargeTest(){

        // creates a toggle for the given test, adds all log events under it
        ExtentTest testAirtimeRecharge = extent.createTest("Airtime Recharge", "Test Scenario when the user is about to recharge airtime");

        // log(Status, details)
        testAirtimeRecharge.log(Status.INFO, "Airtime Recharge Test Started");

        try{
            Assert.assertTrue(driver.findElementById("com.lenddo.mobile.paylater.staging:id/tvRightNow").isDisplayed());
            driver.findElementById("com.lenddo.mobile.paylater.staging:id/tvRightNow").click();
            testAirtimeRecharge.log(Status.PASS, "Clear Call to action");
        }catch(Exception ex){
            testAirtimeRecharge.log(Status.PASS, "CTA didnt popup");
        }

        String availableBalance  = driver.findElementById("com.lenddo.mobile.paylater.staging:id/walletBalanceView").getText();
        BigDecimal balance1 = new BigDecimal("0");
        try {
            balance1 = new CurrencyConverter("NGN").parseConvert(availableBalance);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try{
        driver.findElementByXPath("//android.widget.TextView[@text='Buy Airtime']").click();
        driver.findElementById("com.lenddo.mobile.paylater.staging:id/edit_text_phone_number").click();
        driver.findElementById("com.lenddo.mobile.paylater.staging:id/edit_text_phone_number").sendKeys("08031112000");
        driver.findElementById("com.lenddo.mobile.paylater.staging:id/edit_text_airtime_price").click();
        driver.findElementById("com.lenddo.mobile.paylater.staging:id/edit_text_airtime_price").sendKeys("100");
        testAirtimeRecharge.log(Status.PASS, "User should enter amount");

        driver.findElementByAndroidUIAutomator("new UiScrollable("
                        + "new UiSelector().scrollable(true)).scrollIntoView("
                        + "new UiSelector().textContains(\"Save this payment\"));");
        testAirtimeRecharge.log(Status.PASS, "User should be able to scroll down");
        driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\"com.lenddo.mobile.paylater.staging:id/card_mobile_network\").instance(3)").click();
        testAirtimeRecharge.log(Status.WARNING, "User should'nt be able to select network");

        driver.findElementById("com.lenddo.mobile.paylater.staging:id/switch_toggle").click();
        driver.findElementById("com.lenddo.mobile.paylater.staging:id/edit_text_payment_name").click();
            driver.findElementById("com.lenddo.mobile.paylater.staging:id/edit_text_payment_name").clear();
        driver.findElementById("com.lenddo.mobile.paylater.staging:id/edit_text_payment_name").sendKeys("Airtime test"+new RandomString(9).getString(4));
        testAirtimeRecharge.log(Status.PASS, "User should be able to save payment");
        driver.findElementById("com.lenddo.mobile.paylater.staging:id/button_next").click();

        driver.findElementByXPath("//android.widget.TextView[@text='Available balance']").click();
        testAirtimeRecharge.log(Status.PASS, "User should be able to select wallet");
        driver.findElementById("com.lenddo.mobile.paylater.staging:id/button_confirm_payment").click();
        driver.findElementById("com.lenddo.mobile.paylater.staging:id/text_view_edit_button").click();

        driver.findElementByAndroidUIAutomator("new UiScrollable("
                    + "new UiSelector().scrollable(true)).scrollIntoView("
                    + "new UiSelector().textContains(\"How much airtime do you want to buy?\"));");
        driver.findElementById("com.lenddo.mobile.paylater.staging:id/edit_text_airtime_price").clear();
        driver.findElementById("com.lenddo.mobile.paylater.staging:id/edit_text_airtime_price").sendKeys("200");
        driver.findElementById("com.lenddo.mobile.paylater.staging:id/button_next").click();

        driver.findElementByXPath("//android.widget.TextView[@text='Available balance']").click();
        driver.findElementById("com.lenddo.mobile.paylater.staging:id/button_confirm_payment").click();

        try{
            Assert.assertEquals(driver.findElementById("com.lenddo.mobile.paylater.staging:id/amount_view_amount").getText(),"200.00");
            testAirtimeRecharge.log(Status.PASS, "User should be able to edit airtime price");
        }catch(Exception ex){
            testAirtimeRecharge.log(Status.FAIL, "User could not edit airtime price");
        }
        driver.findElementById("com.lenddo.mobile.paylater.staging:id/button_pay").click();
        //explicit wait for input field
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("android.widget.FrameLayout")));
        driver.findElements(By.className("android.widget.FrameLayout")).get(8).click();

        driver.pressKey(new KeyEvent(AndroidKey.DIGIT_1));
        driver.pressKey(new KeyEvent(AndroidKey.DIGIT_2));
        driver.pressKey(new KeyEvent(AndroidKey.DIGIT_3));
        driver.pressKey(new KeyEvent(AndroidKey.DIGIT_4));
        testAirtimeRecharge.log(Status.PASS, "User entered pin to verify payment");
            try{
                Assert.assertTrue(driver.findElementByXPath("//android.widget.TextView[@text='Saved Plan Already Exists']").isDisplayed());
                driver.findElement(By.id("com.lenddo.mobile.paylater.staging:id/no_button")).click();
                testAirtimeRecharge.log(Status.PASS, "Plan not replaced if exist");
            }catch(Exception ex){
                testAirtimeRecharge.log(Status.PASS, "New saved plan");
            }

            try{
                Assert.assertTrue(driver.findElementByXPath("//android.widget.TextView[@text='Your airtime purchase was successful!']").isDisplayed());
                testAirtimeRecharge.log(Status.PASS, "Airtime has been purchased successfully");
                driver.findElementByAndroidUIAutomator("new UiScrollable("
                        + "new UiSelector().scrollable(true)).scrollIntoView("
                        + "new UiSelector().textContains(\"Go back home\"));");
                driver.findElement(By.id("com.lenddo.mobile.paylater.staging:id/success_home_button")).click();
                String availableBalance2  = driver.findElementById("com.lenddo.mobile.paylater.staging:id/walletBalanceView").getText();
                BigDecimal balance2 = new CurrencyConverter("NGN").parseConvert(availableBalance2);
                String amt = ""+balance1.subtract(balance2);
                if(amt.equals("200.00")){
                    testAirtimeRecharge.log(Status.PASS, "Wallet has been debited");
                }else{
                    testAirtimeRecharge.log(Status.FAIL, "Wallet was not debited");
                }
            }catch(Exception ex){
                testAirtimeRecharge.log(Status.FAIL, "Airtime purchase failed");
            }

        }catch (Exception ex){
            testAirtimeRecharge.log(Status.FAIL, "Test Failed");
        }
    }

    @Test(priority = 5)
    public void fundWalletTest(){
        // creates a toggle for the given test, adds all log events under it
        ExtentTest testFundWallet = extent.createTest("Fund Wallet", "Test Scenario when the user is about to fund the wallet");

        // log(Status, details)
        testFundWallet.log(Status.INFO, "Fund Wallet Test Started");

        String availableBalance  = driver.findElementById("com.lenddo.mobile.paylater.staging:id/walletBalanceView").getText();
        BigDecimal balance1 = new BigDecimal("0");
        try {
            balance1 = new CurrencyConverter("NGN").parseConvert(availableBalance);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//How would you like to fund your wallet?
        try {
            driver.findElementByXPath("//android.widget.TextView[@text='Fund Wallet']").click();
            driver.findElementByXPath("//android.widget.TextView[@text='Fund with debit/ATM card']").click();
            testFundWallet.log(Status.PASS, "Click on Card Option");

            driver.findElementById("com.lenddo.mobile.paylater.staging:id/walletAmountToFund").click();
            driver.findElementById("com.lenddo.mobile.paylater.staging:id/walletAmountToFund").sendKeys("1000");
            driver.findElementById("com.lenddo.mobile.paylater.staging:id/proceedWalletFunding").click();
            testFundWallet.log(Status.PASS, "Click on proceed button");

            List<AndroidElement> elements = driver.findElements(By.id("com.lenddo.mobile.paylater.staging:id/card_number"));
            for (AndroidElement element : elements) {
                String word = element.getText();
                if (word.contains("****")) {
                    element.click();
                    break;
                }
            }
            testFundWallet.log(Status.PASS, "Select a Card");
            driver.findElementById("com.lenddo.mobile.paylater.staging:id/button_confirm_payment").click();
            driver.findElementById("com.lenddo.mobile.paylater.staging:id/secure_pay_button").click();
            testFundWallet.log(Status.PASS, "Click on securely pay button");

            //explicit wait for input field
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("android.widget.FrameLayout")));
            driver.findElements(By.className("android.widget.FrameLayout")).get(8).click();

            driver.pressKey(new KeyEvent(AndroidKey.DIGIT_1));
            driver.pressKey(new KeyEvent(AndroidKey.DIGIT_2));
            driver.pressKey(new KeyEvent(AndroidKey.DIGIT_3));
            driver.pressKey(new KeyEvent(AndroidKey.DIGIT_4));
            testFundWallet.log(Status.PASS, "User entered pin to verify funding");

            try {
                Assert.assertTrue(driver.findElementByXPath("//android.widget.TextView[@text='Your wallet top-up was successful']").isDisplayed());
                testFundWallet.log(Status.PASS, "Successful funding message shown");
            }catch(Exception ex){
                testFundWallet.log(Status.FAIL, "Successful funding message not shown");
            }
            driver.findElementById("com.lenddo.mobile.paylater.staging:id/success_home_button").click();
            String availableBalance2  = driver.findElementById("com.lenddo.mobile.paylater.staging:id/walletBalanceView").getText();
            BigDecimal balance2 = new CurrencyConverter("NGN").parseConvert(availableBalance2);
            String amt = ""+balance2.subtract(balance1);
            if(amt.equals("1000.00")){
                testFundWallet.log(Status.PASS, "Wallet has been funded");
            }else{
                testFundWallet.log(Status.FAIL, "Wallet was not funded");
            }
            try{
                Assert.assertTrue(driver.findElementByXPath("//android.widget.TextView[@text='Transaction Alert']").isDisplayed());
                driver.findElementById("ccom.lenddo.mobile.paylater.staging:id/okayButton").click();
                testFundWallet.log(Status.PASS, "Clear Transaction alert");
            }catch(Exception ex){
                testFundWallet.log(Status.PASS, "Transaction alert didnt popup");
            }
        }catch(Exception ex){
            testFundWallet.log(Status.FAIL, "Test Failed");
        }
    }



}
