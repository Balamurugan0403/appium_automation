package android_learn;

import java.net.URL;
import java.time.Duration;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

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
        driver.activateApp("com.google.android.dialer");

        Thread.sleep(3000);

        System.out.println("Current package = " + driver.getCurrentPackage());
    }

    @Test
    public void callFirstRecentContact() throws InterruptedException {
        driver.findElement(
                AppiumBy.xpath("(//android.widget.ImageView[@resource-id='com.google.android.dialer:id/call_button'])[1]"))
                .click();

        Thread.sleep(10000);
    }

    private void clickDigit(String digitName) {
        driver.findElement(AppiumBy.id("com.google.android.dialer:id/" + digitName)).click();
    }

    @Test
    public void callfromkeypad() throws InterruptedException {
        driver.findElement(AppiumBy.xpath(
                "(//android.widget.ImageView[@resource-id='com.google.android.dialer:id/navigation_bar_item_icon_view'])[2]"))
                .click();
        Thread.sleep(1000);

        clickDigit("seven");
        clickDigit("zero");
        clickDigit("one");
        clickDigit("zero");
        clickDigit("seven");
        clickDigit("two");
        clickDigit("eight");
        clickDigit("two");
        clickDigit("three");
        clickDigit("six");

        Thread.sleep(1000);

        driver.findElement(AppiumBy.xpath("//android.widget.Button[@content-desc='dial']")).click();

        Thread.sleep(10000);
    }

    @AfterClass
    public void tearDown() {

        if (driver != null) {
            driver.quit();
        }
    }
}