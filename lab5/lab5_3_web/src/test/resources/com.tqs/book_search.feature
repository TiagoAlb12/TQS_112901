Feature: Book search in online library
  As a customer, I want to search for books in the online library
  so that I can find the ones I am interested in.

  Scenario: Search for a book by title
    Given I am on the library homepage
    When I search for "Harry Potter"
    Then I should see a book with the title "Harry Potter and the Sorcerer's Stone"
    And I should see an author named "J.K. Rowling"

  Scenario: Search for books by author
    Given I am on the library homepage
    When I search for "George Orwell"
    Then I should see at least 1 book by "George Orwell"

  Scenario: Search for books that do not exist
    Given I am on the library homepage
    When I search for "A Book That Does Not Exist"
    Then I should see a message "No results found"
