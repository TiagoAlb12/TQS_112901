package com.tqs;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;
import java.util.List;

    public class BookSearchSteps {

    private WebDriver driver;

    @Before
    public void setUp() {
        System.setProperty("webdriver.gecko.driver", "src/test/resources/geckodriver");
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Given("I am on the library homepage")
    public void iAmOnTheLibraryHomepage() {
        driver.get("https://cover-bookstore.onrender.com/");
    }

    @When("I search for {string}")
    public void iSearchFor(String query) {
        WebElement searchBox = driver.findElement(By.name("search"));
        searchBox.sendKeys(query);
        searchBox.submit();
    }

    @Then("I should see a book with the title {string}")
    public void iShouldSeeABookWithTheTitle(String expectedTitle) {
        List<WebElement> books = driver.findElements(By.xpath("//h2[contains(text(), '" + expectedTitle + "')]"));
        boolean found = !books.isEmpty();
        Assertions.assertTrue(found, "The expected book title '" + expectedTitle + "' was not found!");
    }

    @Then("I should see an author named {string}")
    public void iShouldSeeAnAuthorNamed(String expectedAuthor) {
        List<WebElement> authors = driver.findElements(By.xpath("//p[contains(text(), '" + expectedAuthor + "')]"));
        boolean found = !authors.isEmpty();
        Assertions.assertTrue(found, "The expected author '" + expectedAuthor + "' was not found!");
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
