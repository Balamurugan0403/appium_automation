package android_learn;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

public class CallTest {

    private AndroidDriver driver;

    @BeforeClass
    public void setUp() throws MalformedURLException {

        System.out.println("Setting up device capabilities...");

        UiAutomator2Options options = new UiAutomator2Options();

        options.setPlatformName("Android");
        options.setAutomationName("UiAutomator2");
        options.setUdid("85a8798b");
        options.setAppPackage("com.google.android.dialer");
        options.setAppActivity("com.google.android.dialer.extensions.GoogleDialtactsActivity");
        options.setNoReset(true);

        driver = new AndroidDriver(
                new URL("http://127.0.0.1:4723/"),
                options);

        driver.manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(10));

        System.out.println("Appium session created and Phone opened!");
    }

    @Test(priority = 1)
    public void callRecentContact() {

        driver.findElement(
                AppiumBy.xpath("//android.widget.TextView[@text='Recents']"))
                .click();

        driver.findElement(
                AppiumBy.xpath("(//android.view.ViewGroup[contains(@resource-id,'primary_action_button')])[1]"))
                .click();

        WebElement calling = driver.findElement(
                AppiumBy.xpath("//android.widget.TextView[contains(@resource-id,'call_state')]"));

        Assert.assertTrue(calling.isDisplayed());

        System.out.println("Successfully initiated the recent call.");
    }

    @Test(priority = 2)
    public void dialSpecificNumber() throws InterruptedException {

        String phoneNumber = "9876543210";

        driver.findElement(
                AppiumBy.xpath("//android.widget.ImageButton[contains(@content-desc,'dial')]"))
                .click();

        driver.findElement(
                AppiumBy.xpath("//android.widget.EditText[contains(@resource-id,'digits')]"))
                .sendKeys(phoneNumber);

        driver.findElement(
                AppiumBy.xpath("//android.widget.ImageButton[contains(@content-desc,'Call')]"))
                .click();

        WebElement callStatus = driver.findElement(
                AppiumBy.xpath("//android.widget.TextView[contains(@resource-id,'call_state')]"));

        Assert.assertTrue(callStatus.isDisplayed());

        System.out.println("Successfully dialed " + phoneNumber);
    }

    @AfterClass
    public void tearDown() {

        if (driver != null) {

            System.out.println("Closing the Appium test session...");

            driver.quit();
        }
    }
}