package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;

import java.time.Duration;

public class Homework25 extends BaseTest{
    @Test
    public void LoginTest(){
        HomePage home = new LoginPage(getDriver()).loginAs("daniel.vasquez@testpro.io", "Makatey135!");
    }
    @Test
    public void PlaylistTest(){
        HomePage home = new LoginPage(getDriver()).loginAs("daniel.vasquez@testpro.io", "Makatey135!");

        // Use unique name to avoid bugs
        String base = "HW25_Playlist";
        String ts = String.valueOf(System.currentTimeMillis() / 1000);
        String name = base + "_" + ts;
        String renamed = name + "_Renamed";

        home.createPlaylist(name);
        home.renamePlaylist(name, renamed);
        home.deletePlaylist(renamed);
        System.out.println("Playlists after test: " + home.listPlaylists());

        Assert.assertFalse(home.playlistExists(renamed),
                "Playlist should be deleted (and rename+delete flow should complete)");
    }
    @Test
    public void themeTest(){
        HomePage home = new LoginPage(getDriver()).loginAs("daniel.vasquez@testpro.io", "Makatey135!");

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(15));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-testid='view-profile-link']"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(" div[data-testid= 'theme-card-pines']"))).click();
        System.out.println("Theme changed to In the Pines");
    }
}
