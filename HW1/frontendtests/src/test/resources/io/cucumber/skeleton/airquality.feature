Feature: Verify website usability
    Scenario: Navigate to the website and check air quality for a Portuguese's station
        When I navigate to 'http://localhost:3000/'
        And I choose 'Portugal' as the country of the station I want to see
        And I choose "Paços de Ferreira, Paços de Ferreira, Portugal" as the station I want to see
        And I click to request the air quality
        Then I should see the "Paços de Ferreira, Paços de Ferreira, Portugal" station
        And I should see the air quality data provided by the station
        Then I close the site

    Scenario: Navigate to the website and check air quality using geolocation
        When I navigate to 'http://localhost:3000/'
        And I enter '40.756666666667' as latitude
        And I enter '-8.5727777777778' as longitude
        And I click to request that location air quality
        Then I should see the "Teixugueira, Estarreja, Portugal" station
        And I should see the air quality data provided by the station
        Then I close the site