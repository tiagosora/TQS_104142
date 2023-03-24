Feature: Reserve flights

  Scenario: Successful Reserve
    When I navigate to 'https://blazedemo.com/'
    And I choose 'Boston' for the departure
    And I choose 'Berlin' for the destination
    When I find flights
    Then I should be see the message 'Flights from Boston to Berlin:'
