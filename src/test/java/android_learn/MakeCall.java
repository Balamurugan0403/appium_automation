package android_learn;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.URL;
import java.time.Duration;
import java.util.List;

public class MakeCall {

    AndroidDriver driver;

    @BeforeClass
    public void setup() throws Exception {

        UiAutomator2Options options = new UiAutomator2Options();

        options.setPlatformName("Android");
        options.setAutomationName("UiAutomator2");
        options.setDeviceName("85a8798b");
        options.setUdid("85a8798b");

        options.setNoReset(true);

        driver = new AndroidDriver(
                new URL("http://127.0.0.1:4723"),
                options);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Explicitly launch Phone app
        driver.activateApp("com.google.android.dialer");

        Thread.sleep(3000);

        System.out.println("Current package = " + driver.getCurrentPackage());
    }
    @Test
    public void callFirstRecentContact() throws InterruptedException {

        // Click the first recent contact's call button
        driver.findElement(
                By.xpath("(//android.widget.ImageView[@resource-id='com.google.android.dialer:id/call_button'])[1]"))
                .click();

        Thread.sleep(10000);
    }

    @AfterClass
    public void tearDown() {

        if (driver != null) {
            driver.quit();
        }
    }
}