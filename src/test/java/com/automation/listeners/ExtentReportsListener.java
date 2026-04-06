package com.automation.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ExtentReports TestNG Listener for generating beautiful HTML reports
 * Reports are generated in: target/extent-reports/
 */
public class ExtentReportsListener implements ITestListener {

    private static ExtentReports extentReports;
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    private static final String REPORTS_PATH = "target/extent-reports/";

    /**
     * Initialize Extent Reports
     */
    private static ExtentReports initializeExtentReports() {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date());
        String reportName = "ExtentReport_" + timeStamp + ".html";

        // Create reports directory if it doesn't exist
        File reportsDir = new File(REPORTS_PATH);
        if (!reportsDir.exists()) {
            reportsDir.mkdirs();
        }

        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(REPORTS_PATH + reportName);

        // Configure Spark Reporter
        sparkReporter.config().setReportName("Practice Software Testing - Automation Report");
        sparkReporter.config().setDocumentTitle("Test Automation Report");
        sparkReporter.config().setTheme(Theme.DARK);
        sparkReporter.config().setTimeStampFormat("yyyy-MM-dd HH:mm:ss");

        ExtentReports extentReports = new ExtentReports();
        extentReports.attachReporter(sparkReporter);

        // Set system information
        extentReports.setSystemInfo("OS", System.getProperty("os.name"));
        extentReports.setSystemInfo("Java Version", System.getProperty("java.version"));
        extentReports.setSystemInfo("User Name", System.getProperty("user.name"));
        extentReports.setSystemInfo("Application URL", "https://practicesoftwaretesting.com");
        extentReports.setSystemInfo("Framework", "Selenium 4 + TestNG");

        return extentReports;
    }

    /**
     * Called when test suite starts
     */
    @Override
    public void onStart(ITestContext context) {
        System.out.println("Test suite started: " + context.getName());
        if (extentReports == null) {
            extentReports = initializeExtentReports();
        }
    }

    /**
     * Called when a test method starts
     */
    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String description = result.getMethod().getDescription();

        ExtentTest test = extentReports.createTest(testName, description != null ? description : "");
        test.assignCategory("Smoke Test");
        test.assignAuthor("Automation Team");

        extentTest.set(test);
        System.out.println("Test started: " + testName);
    }

    /**
     * Called when a test method succeeds
     */
    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentTest test = extentTest.get();
        if (test != null) {
            test.log(Status.PASS, MarkupHelper.createLabel(
                    result.getMethod().getMethodName() + " PASSED",
                    ExtentColor.GREEN
            ));
        }
        System.out.println("Test passed: " + result.getMethod().getMethodName());
    }

    /**
     * Called when a test method fails
     */
    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest test = extentTest.get();
        if (test != null) {
            test.log(Status.FAIL, MarkupHelper.createLabel(
                    result.getMethod().getMethodName() + " FAILED",
                    ExtentColor.RED
            ));

            // Add exception details
            if (result.getThrowable() != null) {
                test.log(Status.FAIL, "Exception: " + result.getThrowable().getMessage());
                test.log(Status.FAIL, result.getThrowable());
            }
        }
        System.out.println("Test failed: " + result.getMethod().getMethodName());
    }

    /**
     * Called when a test method is skipped
     */
    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentTest test = extentTest.get();
        if (test != null) {
            test.log(Status.SKIP, MarkupHelper.createLabel(
                    result.getMethod().getMethodName() + " SKIPPED",
                    ExtentColor.YELLOW
            ));

            if (result.getThrowable() != null) {
                test.log(Status.SKIP, "Reason: " + result.getThrowable().getMessage());
            }
        }
        System.out.println("Test skipped: " + result.getMethod().getMethodName());
    }

    /**
     * Called when test suite finishes
     */
    @Override
    public void onFinish(ITestContext context) {
        System.out.println("Test suite finished: " + context.getName());
        if (extentReports != null) {
            extentReports.flush();
            System.out.println("Extent Report generated at: " + REPORTS_PATH);
        }
    }
}

