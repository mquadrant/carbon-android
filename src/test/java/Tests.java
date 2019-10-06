import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.apache.http.util.Asserts;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Tests extends BaseClass {

    @Test
    public void sampleTest(){
        System.out.println("I am inside sample test");
    }

    @Test
    public void navigateToSignInTest(){
        // creates a toggle for the given test, adds all log events under it
        ExtentTest testSignIn = extent.createTest("Welcome Page", "Test Scenario for Navigation to Sign In");

        // log(Status, details)
        testSignIn.log(Status.INFO, "Navigation Test Started");

        driver.findElement(By.id("com.lenddo.mobile.paylater.staging:id/user_type_existing")).click();
        try{
            Assert.assertEquals(driver.findElementById("com.lenddo.mobile.paylater.staging:id/sign_in_next").getText(),"Sign in");
            testSignIn.log(Status.PASS, "Navigate to Login View");
        }catch(AssertionError ex){
            testSignIn.log(Status.FAIL, "Cant Sign in on this view");
        }

    }

    @Test
    public void signInTest(){
        // creates a toggle for the given test, adds all log events under it
        ExtentTest testSignIn = extent.createTest("SignIn", "Test Scenario when the user login");

        // log(Status, details)
        testSignIn.log(Status.INFO, "SignIn Test Started");

        driver.findElement(By.id("com.lenddo.mobile.paylater.staging:id/user_type_existing")).click();
        testSignIn.log(Status.PASS, "Navigate to Login View");
    }
}
