package com.tqs;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SeleniumJupiter.class)
public class Search_c_Test {

    @Test
    void testSearchHarryPotter(FirefoxDriver driver) {
        driver.get("https://cover-bookstore.onrender.com/");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(By.name("search")));
        searchBox.sendKeys("Harry Potter");
        searchBox.submit();

        WebElement resultTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("[data-testid='book-title']")
        ));
        assertTrue(resultTitle.getText().contains("Harry Potter and the Sorcerer"), "O título do livro esperado não foi encontrado!");

        WebElement resultAuthor = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("[data-testid='book-author']")
        ));
        assertTrue(resultAuthor.getText().contains("J.K. Rowling"), "O autor do livro esperado não foi encontrado!");
    }
}
