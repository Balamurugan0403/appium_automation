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
        options.setUdid("RZCY82554JT");
        options.setAppPackage("com.samsung.android.dialer");
        options.setAppActivity("com.samsung.android.dialer.DialtactsActivity");
        options.setNoReset(true);

        driver = new AndroidDriver(
                new URL("http://127.0.0.1:4723/"),
                options);

        driver.manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(10));

        System.out.println("Appium session created and Phone opened!");
    }

    @Test
    public void makeCall() {

        driver.findElement(
                AppiumBy.xpath("(//android.widget.ImageView[@resource-id='com.samsung.android.dialer:id/tab_icon'])[2]"))
                .click();

        driver.findElement(
                AppiumBy.xpath("(//android.widget.LinearLayout[@resource-id='com.samsung.android.dialer:id/container_call_log_text'])[1]"))
                .click();

        driver.findElement(
                AppiumBy.id("com.samsung.android.dialer:id/image_view_expand_one"))
                .click();

        WebElement calling = driver.findElement(
                AppiumBy.id("com.samsung.android.incallui:id/call_state_label"));

        Assert.assertTrue(calling.isDisplayed());

        System.out.println("Successfully initiated the recent call.");
    }

    @AfterClass
    public void tearDown() {

        if (driver != null) {

            System.out.println("Closing the Appium test session...");

            driver.quit();
        }
    }
}