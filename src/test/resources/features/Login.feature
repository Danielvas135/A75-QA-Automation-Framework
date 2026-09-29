Feature: Login feature

  Scenario: Login and play a song
    Given I open browser
    And I open Login page
    When I enter email "daniel.vasquez@testpro.io"
    And I enter password "KoelTest123!"
    And I submit
    Then I am logged in
    When I open All Songs
    And I play the first song