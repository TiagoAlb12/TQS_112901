package com.tqs;

import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SearchBooksSteps {

    private Library library = new Library(new ArrayList<>());
    private List<Book> result;

    @ParameterType("([0-9]{4})-([0-9]{2})-([0-9]{2})")
    public LocalDate iso8601Date(String year, String month, String day) {
        return LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
    }

    @Given("a book with the title {string}, written by {string}, published in {iso8601Date}")
    public void aBookWithTheTitleWrittenByPublishedIn(String title, String author, LocalDate publishedDate) {
        Book book = new Book(publishedDate.atStartOfDay(), author, title);
        library.addBook(book);
    }

    @When("the customer searches for books by author {string}")
    public void theCustomerSearchesForBooksByAuthor(String author) {
        result = library.findBooksByAuthor(author);
    }

    @When("the customer searches for books published between {iso8601Date} and {iso8601Date}")
    public void theCustomerSearchesForBooksPublishedBetween(LocalDate from, LocalDate to) {
        result = library.findBooks(from.atStartOfDay(), to.atTime(23, 59, 59));
    }

    @Then("{int} books should have been found")
    public void booksShouldHaveBeenFound(int expectedCount) {
        assertThat(result).hasSize(expectedCount);
    }

    @Then("Book {int} should have the title {string}")
    public void bookShouldHaveTheTitle(int bookIndex, String expectedTitle) {
        assertThat(result.get(bookIndex - 1).getTitle()).isEqualTo(expectedTitle);
    }
}
