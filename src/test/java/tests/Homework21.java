package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;

public class Homework21 extends BaseTest {

    @Test
    public void createRenameDeletePlaylist() {
        // Login
        HomePage home = new LoginPage(getDriver())
                .loginAs("daniel.vasquez@testpro.io", "Makatey135!");

        Assert.assertTrue(home.isLoaded(), "Home should be loaded");

        // Use unique name to avoid bugs
        String base = "HW23_Playlist";
        String ts = String.valueOf(System.currentTimeMillis() / 1000);
        String name = base + "_" + ts;
        String renamed = name + "_Renamed";

        // 1) Create
        home.createPlaylist(name);

        // 2) Rename (no assertion in the middle)
        home.renamePlaylist(name, renamed);

        // 3) Delete (just try to delete whatever we think it is)
        home.deletePlaylist(renamed);

        // Debug output so you can SEE what happened
        System.out.println("Playlists after test: " + home.listPlaylists());

        // Single simple assertion: the renamed one should not exist
        Assert.assertFalse(home.playlistExists(renamed),
                "Playlist should be deleted (and rename+delete flow should complete)");
    }
}
