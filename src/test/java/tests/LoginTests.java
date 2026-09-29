package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;

public class LoginTests extends BaseTest {

    @Test
    public void validLogin() {
        HomePage home = new LoginPage(getDriver())
                .loginAs("daniel.vasquez@testpro.io", "KoelTest123!");
        Assert.assertTrue(home.isLoaded(), "Home should load after valid login");
    }

    @Test
    public void invalidPasswordDoesNotOpenHome() {
        LoginPage login = new LoginPage(getDriver());
        login.provideEmail("daniel.vasquez@testpro.io");
        login.providePassword("wrongpassword");
        login.clickSubmit();

        Assert.assertFalse(
                new HomePage(getDriver()).isLoaded(),
                "Home should not load with a bad password"
        );
    }
}