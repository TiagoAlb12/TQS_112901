package com.tqs;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SeleniumJupiter.class)
public class LoginTest {

    @Test
    public void testLogin(WebDriver driver) {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("admin", "password");

        assertTrue(loginPage.isLoginSuccessful());

        driver.quit();
    }
}