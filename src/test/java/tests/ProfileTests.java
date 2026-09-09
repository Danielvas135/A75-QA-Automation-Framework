package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import pages.LoginPage;

import java.time.Duration;

public class ProfileTests extends BaseTest {

    @Test
    public void changeThemeToPines() {
        new LoginPage(getDriver())
                .loginAs("daniel.vasquez@testpro.io", "Makatey135!");

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(15));
        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("[data-testid='view-profile-link']"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("div[data-testid='theme-card-pines']"))).click();
    }
}