package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

public class Homework24 extends BaseTest {

    @Test
    public void testGridChrome() {
        String title = getDriver().getTitle();
        System.out.println("Page title from Grid: " + title);

        Assert.assertTrue(title.length() > 0, "Title should not be empty");
        System.out.println("Homework24 – Chrome successfully ran through Selenium Grid");
    }
}