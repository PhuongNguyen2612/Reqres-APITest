package core;

import core.manager.ChromeDriverManager;
import org.openqa.selenium.WebDriver;

public class DriverWrapper {
    private static WebDriver driver = null;

    private DriverWrapper() {
        this.driver = ChromeDriverManager.getChromeManagerInstance().createDriver();
    }

    public static WebDriver getDriver() {
        if (driver == null) {
            new DriverWrapper();
        }
        return driver;
    }
}
