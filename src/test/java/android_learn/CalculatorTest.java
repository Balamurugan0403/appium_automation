package android_learn;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.URL;
import java.time.Duration;
import java.util.List;

public class CalculatorTest {

    AndroidDriver driver;

    @BeforeClass
    public void setUp() throws Exception {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Android");
        caps.setCapability("appium:deviceName", "Redmi");
        caps.setCapability("appium:automationName", "UiAutomator2");
        caps.setCapability("appium:appPackage", "com.miui.calculator");
        caps.setCapability("appium:appActivity", "com.miui.calculator.cal.CalculatorActivity");
        caps.setCapability("appium:noReset", true);

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), caps);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @BeforeMethod
    public void clearCalculator() {
        WebElement clr = driver.findElement(AppiumBy.id("com.miui.calculator:id/btn_c_s"));
        clr.click();
    }

    private void clickDigit(int digit) {
        WebElement btn = driver.findElement(AppiumBy.id("com.miui.calculator:id/btn_" + digit + "_s"));
        btn.click();
    }

    private String getResult() {
        List<WebElement> results = driver.findElements(AppiumBy.id("com.miui.calculator:id/result"));
        WebElement lastResult = results.get(results.size() - 1);
        String text = lastResult.getText().trim();
        if (text.startsWith("=")) {
            text = text.substring(1).trim();
        }
        return text;
    }

    @Test
    public void testAddition() throws InterruptedException {
        clickDigit(9);
        driver.findElement(AppiumBy.id("com.miui.calculator:id/btn_plus_s")).click();
        clickDigit(3);
        driver.findElement(AppiumBy.id("com.miui.calculator:id/btn_equal_s")).click();
        Thread.sleep(1000);

        System.out.println("Addition Result: " + getResult());
        Assert.assertEquals(getResult(), "12");
    }

    @Test
    public void testSubtraction() throws InterruptedException {
        clickDigit(9);
        driver.findElement(AppiumBy.id("com.miui.calculator:id/btn_minus_s")).click();
        clickDigit(3);
        driver.findElement(AppiumBy.id("com.miui.calculator:id/btn_equal_s")).click();
        Thread.sleep(1000);

        System.out.println("Subtraction Result: " + getResult());
        Assert.assertEquals(getResult(), "6");
    }

    @Test
    public void testMultiplication() throws InterruptedException {
        clickDigit(9);
        driver.findElement(AppiumBy.id("com.miui.calculator:id/btn_mul_s")).click();
        clickDigit(3);
        driver.findElement(AppiumBy.id("com.miui.calculator:id/btn_equal_s")).click();
        Thread.sleep(1000);

        System.out.println("Multiplication Result: " + getResult());
        Assert.assertEquals(getResult(), "27");
    }

    @Test
    public void testDivision() throws InterruptedException {
        clickDigit(9);
        driver.findElement(AppiumBy.id("com.miui.calculator:id/btn_div_s")).click();
        clickDigit(3);
        driver.findElement(AppiumBy.id("com.miui.calculator:id/btn_equal_s")).click();
        Thread.sleep(1000);

        System.out.println("Division Result: " + getResult());
        Assert.assertEquals(getResult(), "3");
    }

    @Test
    public void testDecimalAddition() throws InterruptedException {
        clickDigit(1);
        driver.findElement(AppiumBy.id("com.miui.calculator:id/btn_dot_s")).click();
        clickDigit(5);
        driver.findElement(AppiumBy.id("com.miui.calculator:id/btn_plus_s")).click();
        clickDigit(2);
        driver.findElement(AppiumBy.id("com.miui.calculator:id/btn_dot_s")).click();
        clickDigit(5);
        driver.findElement(AppiumBy.id("com.miui.calculator:id/btn_equal_s")).click();
        Thread.sleep(1000);

        System.out.println("Decimal Addition Result: " + getResult());
        Assert.assertEquals(getResult(), "4");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}