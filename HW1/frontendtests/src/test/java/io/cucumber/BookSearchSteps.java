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

    @When("I navigate to {string}")
    public void i_navigate_to(String string) {
        // Write code here that turns the phrase above into concrete actions
    }

	// @When("I navigate to {string}")
    // void whenNavigateTo(String url){
    //     // this.driver = new FirefoxDriver();
    //     // this.driver.get(url);
    //     // driver.manage().window().setSize(new Dimension(1920, 1001));
    // }

    @When("I choose {string} as the country of the station I want to see")
	public void i_choose_as_the_country_of_the_station_i_want_to_see(String string) {
		// Write code here that turns the phrase above into concrete actions
		// throw new io.cucumber.java.PendingException();
	}


    @When("I choose {string} as the station I want to see")
	public void i_choose_as_the_station_i_want_to_see(String string) {
		// Write code here that turns the phrase above into concrete actions
	}
	@When("I click to request the air quality")
	public void i_click_to_request_the_air_quality() {
		// Write code here that turns the phrase above into concrete actions
	}
	@Then("I should see the {string} station")
	public void i_should_see_the_station(String string) {
		// Write code here that turns the phrase above into concrete actions
	}
	@Then("I should see the air quality data provided by the station")
	public void i_should_see_the_air_quality_data_provided_by_the_station() {
		// Write code here that turns the phrase above into concrete actions
	}

}