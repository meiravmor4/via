package com.via.tests;

import com.via.ui.AppiumServer;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import static com.via.ui.Elements.*;
import static java.lang.Thread.sleep;

public abstract class BaseTest {

    private static AppiumDriverLocalService service;
    public AndroidDriver<AndroidElement> driver;
    public final String PACKAGE = "io.appium.android.apis";
    public final String ACTIVITY = "io.appium.android.apis.ApiDemos";

    @BeforeSuite
    public void globalSetup () {
        AppiumServer.stopAppiumServer();
        AppiumServer.startAppiumServer();
        service = AppiumDriverLocalService.buildDefaultService();
        service.start();
    }

    @BeforeClass
    public void setUp() throws IOException {
        File classpathRoot = new File(System.getProperty("user.dir"));
        File appDir = new File(classpathRoot, ".");
        File app = new File(appDir.getCanonicalPath(), "ApiDemos-debug.apk");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
        capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, PACKAGE);
        capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ACTIVITY);
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UIAutomator2");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "13");
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 6000);
        capabilities.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS,true);
        capabilities.setCapability(MobileCapabilityType.UDID, "emulator-5554"); //adb devices -l
        capabilities.setCapability(MobileCapabilityType.NO_RESET, "False");;
        capabilities.setCapability(MobileCapabilityType.FULL_RESET, "True");;
        driver = new AndroidDriver<>(getServiceUrl(), capabilities);
    }

    public URL getServiceUrl () {
        return service.getUrl();
    }

    public void goToViews() throws InterruptedException {
        driver.startActivity(new Activity(PACKAGE, ACTIVITY));
        List<AndroidElement> buttons = driver.findElements(By.xpath(BUTTON));
        if (0 < buttons.size()) {
            click(OK);
        }
        click(VIEWS);
    }

    public void click(String element) throws InterruptedException {
        int retries = 5;
        while (driver.findElements(By.xpath(element)).size() == 0 && 0 < retries--) {
            sleep(1000);
        }
        driver.findElement(By.xpath(element)).click();
    }

    public void type(String element, String text) throws InterruptedException {
        int retries = 5;
        while (driver.findElements(By.xpath(element)).size() == 0 && 0 < retries--) {
            sleep(1000);
        }
        driver.findElement(By.xpath(element)).sendKeys(text);
    }

    public void scrollUpAndClick(String element) throws InterruptedException {
        while (driver.findElements(By.xpath(element)).size() == 0) {
            driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollBackward()"));
        }
        click(element);
    }

    public void scrollDownAndClick(String element) throws InterruptedException {
        while (driver.findElements(By.xpath(element)).size() == 0) {
            driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollForward()"));
        }
        click(element);
    }

    public void countIcons() {
        int count = driver.findElements(By.xpath(ICON)).size();
        System.out.println("*********************** " + count + " Icons ***********************");
    }

    @AfterSuite
    public void globalTearDown () {
        if (service != null) {
            service.stop();
        }
    }
}