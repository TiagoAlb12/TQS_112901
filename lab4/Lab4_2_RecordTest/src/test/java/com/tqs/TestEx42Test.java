package com.tqs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SeleniumJupiter.class)
public class TestEx42Test {

  private WebDriver driver;

  @BeforeEach
  void setup(FirefoxDriver driver) {
    this.driver = driver;
  }

  @Test
  void testFlightBooking() {
    // Test name: TestEx4_2
    // Step # | name | target | value

    // 1 | open | / |
    driver.get("https://blazedemo.com/");

    // 2 | setWindowSize | 1854x1048 |
    driver.manage().window().maximize();

    // 3 | click | css=.btn-primary |
    driver.findElement(By.cssSelector(".btn-primary")).click();

    // 4 | click | css=tr:nth-child(3) .btn |
    driver.findElement(By.cssSelector("tr:nth-child(3) .btn")).click();

    // 5 | click | id=inputName |
    driver.findElement(By.id("inputName")).click();

    // 6 | type | id=inputName | Tiago
    driver.findElement(By.id("inputName")).sendKeys("Tiago");

    // 7 | type | id=address | Viseu, 1234
    driver.findElement(By.id("address")).sendKeys("Viseu, 1234");

    // 8 | click | id=city |
    driver.findElement(By.id("city")).click();

    // 9 | type | id=city | Viseu
    driver.findElement(By.id("city")).sendKeys("Viseu");

    // 10 | type | id=zipCode | 1234
    driver.findElement(By.id("zipCode")).sendKeys("1234");

    // 11 | click | id=creditCardNumber |
    driver.findElement(By.id("creditCardNumber")).click();

    // 12 | type | id=creditCardNumber | 12345
    driver.findElement(By.id("creditCardNumber")).sendKeys("12345");

    // 13 | click | id=nameOnCard |
    driver.findElement(By.id("nameOnCard")).click();

    // 14 | type | id=nameOnCard | Tiago A
    driver.findElement(By.id("nameOnCard")).sendKeys("Tiago A");

    // 15 | click | css=.btn-primary |
    driver.findElement(By.cssSelector(".btn-primary")).click();

    // 16 | Verificar se a compra foi concluída corretamente
    WebElement confirmationText = driver.findElement(By.tagName("h1"));
    assertEquals("BlazeDemo Confirmation", confirmationText.getText(), "A compra não foi confirmada corretamente!");
  }
}
