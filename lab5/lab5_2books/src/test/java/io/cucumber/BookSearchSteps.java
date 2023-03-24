package io.cucumber;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.cucumber.java.ParameterType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
 
public class BookSearchSteps {
	Library library = new Library();
	List<Book> result = new ArrayList<>();

    @ParameterType("([0-9]{2})-([0-9]{2})-([0-9]{4})")
    public Date date(String day, String month, String year) {
        LocalDateTime ldt = LocalDateTime.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day), 0, 0);
        return Date.from(ldt.toInstant(ZoneOffset.UTC));
    }

    @ParameterType("([0-9]{4})")
    public Date year(String year) {
        LocalDateTime ldt = LocalDateTime.of(Integer.parseInt(year),1,1,0,0);
        return Date.from(ldt.toInstant(ZoneOffset.UTC));
    }
 
	@Given("a book with the title {string}, written by {string}, published in {date}")
	public void addNewBook(final String title, final String author, final Date published) {
		Book book = new Book(title, author, published);
		library.addBook(book);
	}
    
    @And("another book with the title {string}, written by {string}, published in {date}")
    public void addAnotherBook(final String title, final String author, final Date published) {
		Book book = new Book(title, author, published);
		library.addBook(book);
	}
 
	@When("the customer searches for books published between {year} and {year}")
	public void setSearchParametersByDate(final Date from, final Date to) {
		result = library.findBooksByDate(from, to);
	}

    @When("the customer searches for books written by {string}")
	public void setSearchParameters(final String author) {
		result = library.findBooksByAuthor(author);
	}
 
	@Then("{int} books should have been found")
	public void verifyAmountOfBooksFound(final int booksFound) {
		assertEquals(result.size(), booksFound);
	}
 
	@And("Book {int} should have the title {string}")
	public void verifyBookAtPosition(final int position, final String title) {
		assertEquals(result.get(position - 1).getTitle(), title);
	}
}