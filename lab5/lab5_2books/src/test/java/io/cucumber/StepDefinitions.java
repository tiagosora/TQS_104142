package io.cucumber;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinitions {

    Belly belly = new Belly();

    @Given("I have {int} cukes in my belly")
    public void I_have_cukes_in_my_belly(int cukes) {
        
        this.belly.eat(cukes);
    }
    @When("I wait {int} hour")
    public void i_wait_hour(int hours) {
        this.belly.wait(hours);
    }
    @Then("my belly should growl")
    public void my_belly_should_growl() {
        // Write code here that turns the phrase above into concrete actions
        assertEquals(this.belly.getCukesInBelly(), 0);
        System.out.println("My belly growled");
    }

}
