Feature: Verify website usability
    Scenario: Navigate to the website and check air quality for a Portuguese's station
        When I navigate to 'http://localhost:3000/'
        And I choose 'Portugal' as the country of the station I want to see
        And I choose "Paços de Ferreira, Paços de Ferreira, Portugal" as the station I want to see
        And I click to request the air quality
        Then I should see the "Paços de Ferreira, Paços de Ferreira, Portugal" station
        And I should see the air quality data provided by the station