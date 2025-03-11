package com.tqs;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SeleniumJupiter.class)
public class Search_a_Test {

    @Test
    void testSearchHarryPotter(FirefoxDriver driver) {
        driver.get("https://cover-bookstore.onrender.com/");

        WebElement searchBox = driver.findElement(By.name("search"));
        searchBox.sendKeys("Harry Potter");
        searchBox.submit();

        WebElement resultTitle = driver.findElement(By.xpath("//h2[contains(text(), 'Harry Potter and the Sorcerer')]"));
        assertNotNull(resultTitle, "The expected book title was not found!");

        WebElement resultAuthor = driver.findElement(By.xpath("//p[contains(text(), 'J.K. Rowling')]"));
        assertNotNull(resultAuthor, "The expected book author was not found!");
    }
}
