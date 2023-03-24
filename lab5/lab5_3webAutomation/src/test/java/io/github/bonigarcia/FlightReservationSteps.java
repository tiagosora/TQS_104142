package io.github.bonigarcia;

import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;

public class FlightReservationSteps {

    private WebDriver driver;

    @When("I navigate to {string}")
    public void navigate(String url) {
        this.driver = WebDriverManager.firefoxdriver().create();
        this.driver.get(url);
    }

    @And("I choose {string} for the departure")
    public void departure(String departure) {
        driver.findElement(By.name("fromPort")).sendKeys(departure);
    }

    @And("I choose {string} for the destination")
    public void destination(String destination) {
        driver.findElement(By.name("toPort")).sendKeys(destination);
    }

    @When("I find flights")
    public void find() {
        driver.findElement(By.cssSelector(".btn-primary")).click();
    }

    @Then("I should be see the message {string}")
    public void shouldSee(String result) {
        try {
            assertThat(driver.findElement(By.xpath("/html/body/div[2]/h3")).getText(), containsString(result));
        } catch (NoSuchElementException e) {
            throw new AssertionError("\"" + result + "\" not available in results");
        } finally {
            driver.quit();
        }
    }

}
