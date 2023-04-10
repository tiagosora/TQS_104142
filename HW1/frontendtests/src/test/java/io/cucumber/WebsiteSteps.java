package io.cucumber;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.lang3.math.NumberUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
 
public class WebsiteSteps {

	WebDriver driver;

    @When("I navigate to {string}")
    public void i_navigate_to(String url) {
        this.driver = new FirefoxDriver();
		this.driver.get(url);
		driver.manage().window().setSize(new Dimension(1920, 1001));
    }

    @And("I choose {string} as the country of the station I want to see")
	public void i_choose_as_the_country_of_the_station_i_want_to_see(String string) throws InterruptedException {
		Thread.sleep(6000);
		WebElement element = driver.findElement(By.id("select-1"));
		Actions builder = new Actions(driver);
		builder.moveToElement(element).clickAndHold().perform();
		element = driver.findElement(By.id("db1-Brazil"));
		builder.moveToElement(element).release().perform();
		driver.findElement(By.cssSelector("body")).click();
		driver.findElement(By.id("db1-Portugal")).click();
	}

    @And("I choose {string} as the station I want to see")
	public void i_choose_as_the_station_i_want_to_see(String string) throws InterruptedException {
		Thread.sleep(6000);
		WebElement element = driver.findElement(By.id("select-2"));
		Actions builder = new Actions(driver);
		builder.moveToElement(element).clickAndHold().perform();
		element = driver.findElement(By.id("db2-Entrecampos, Lisboa, Portugal"));
		builder = new Actions(driver);
		builder.moveToElement(element).release().perform();
		driver.findElement(By.cssSelector("body")).click();
		driver.findElement(By.id("db2-"+string)).click();
	}

	@And("I click to request the air quality")
	public void i_click_to_request_the_air_quality() {
		driver.findElement(By.id("button1")).click();
	}

	@Then("I should see the {string} station")
	public void i_should_see_the_station(String string) throws InterruptedException {
		Thread.sleep(6000);
		WebElement element = driver.findElement(By.id("paperContent"));
		assertTrue(element.getText().contains(string));
	}

	@And("I should see the air quality data provided by the station")
	public void i_should_see_the_air_quality_data_provided_by_the_station() {
		WebElement element = driver.findElement(By.id("paperContent"));
		String replaced = element.getText().replaceAll("[^A-Za-z0-9-]", "");
		replaced = replaced.substring(replaced.indexOf("AirQualityIndex") + 15);
		String index = replaced.substring(0, replaced.indexOf("PM25"));
		assertTrue(NumberUtils.isCreatable(index) || index.equals("-"));
	}

	@Then("I close the site")
	public void i_close_the_site(){
		driver.quit();
	}

	@And("I enter {string} as latitude")
	public void i_enter_as_latitude(String string){
		driver.findElement(By.id("input1")).click();
		driver.findElement(By.id("input1")).sendKeys(string);
	}

	@And("I enter {string} as longitude")
	public void i_enter_as_longitude(String string){
		driver.findElement(By.id("input2")).sendKeys(string);
	}

	@And("I click to request that location air quality")
	public void i_click_to_request_that_location_air_quality(){
		driver.findElement(By.id("button2")).click();
	}
}