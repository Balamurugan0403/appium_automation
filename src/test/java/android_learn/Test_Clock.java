package android_learn;

import java.net.URL;
import java.time.Duration;
import java.util.Collections;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

public class Test_Clock {
    AndroidDriver driver;

    @BeforeClass
    public void setup() throws Exception {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName("Android");
        options.setAutomationName("UiAutomator2");
        options.setDeviceName("85a8798b");
        options.setAppPackage("com.android.deskclock");
        options.setAppActivity("com.android.deskclock.DeskClockTabActivity");
        options.setNoReset(true);
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    public void swipe(int startX, int startY, int endX, int endY) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);
        swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(1000), PointerInput.Origin.viewport(), endX, endY));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Collections.singletonList(swipe));
    }

    public int readValue(String id) {
        WebElement el = driver.findElement(AppiumBy.id(id));
        return (int) Double.parseDouble(el.getAttribute("text"));
    }

    @Test
    public void setAlarm() throws InterruptedException {
        driver.findElement(AppiumBy.id("com.android.deskclock:id/end_btn2")).click();
        Thread.sleep(2000);

        int currentHour = readValue("com.android.deskclock:id/hour");
        int targetHour = currentHour == 12 ? 1 : currentHour + 1;
        while (readValue("com.android.deskclock:id/hour") != targetHour) {
            swipe(360, 620, 360, 500);
            Thread.sleep(1000);
        }

        int currentMinute = readValue("com.android.deskclock:id/minute");
        int targetMinute = currentMinute == 59 ? 0 : currentMinute + 1;
        while (readValue("com.android.deskclock:id/minute") != targetMinute) {
            swipe(586, 620, 586, 500);
            Thread.sleep(1000);
        }

        driver.findElement(AppiumBy.id("android:id/button2")).click();
    }
}
