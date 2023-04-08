package io.cucumber;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
 
public class WebsiteSteps {

    @When("I navigate to {string}")
    public void i_navigate_to(String string) {
        // this.driver = new FirefoxDriver();
		//     // this.driver.get(url);
		//     // driver.manage().window().setSize(new Dimension(1920, 1001));
    }

    @And("I choose {string} as the country of the station I want to see")
	public void i_choose_as_the_country_of_the_station_i_want_to_see(String string) {
	}

    @And("I choose {string} as the station I want to see")
	public void i_choose_as_the_station_i_want_to_see(String string) {
	}
	@And("I click to request the air quality")
	public void i_click_to_request_the_air_quality() {
	}
	@Then("I should see the {string} station")
	public void i_should_see_the_station(String string) {
	}
	@And("I should see the air quality data provided by the station")
	public void i_should_see_the_air_quality_data_provided_by_the_station() {
	}

}