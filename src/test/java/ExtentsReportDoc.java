import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class ExtentsReportDoc {
    private static ExtentHtmlReporter htmlReporter;
    static ExtentReports extent;

    @BeforeSuite
    public static void reportSetup() {
        // start reporters
        htmlReporter = new ExtentHtmlReporter("extent.html");

        // create ExtentReports and attach reporter(s)
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }


    @AfterSuite
    public static void reportTearDown() {
        // calling flush writes everything to the log file
        extent.flush();
    }
}
