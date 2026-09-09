package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends BasePage {

    // ---- PageFactory elements ----
    @FindBy(xpath = "//a[@href='#!/songs' and normalize-space(text())='All Songs']")
    private WebElement allSongsLink;

    @FindBy(css = "[data-testid='sidebar-create-playlist-btn']")
    private WebElement sidebarPlaylistBtn;

    @FindBy(css = "[data-testid='playlist-context-menu-create-simple']")
    private WebElement sidebarCreateBtn;

    @FindBy(css = "form[name='create-simple-playlist-form'] input[name='name']")
    private WebElement sidebarCreateInput;

    @FindBy(xpath = "//button[contains(@class, 'btn-delete-playlist')]")
    private WebElement deletePlaylistBtn;

    @FindBy(xpath = "//button[contains(@class, 'ok') or contains(text(), 'OK')]")
    private WebElement okBtn;

    // ---- dynamic locators ----
    private By playlistByName(String name) {
        return By.xpath("//section[@id='playlists']//a[normalize-space(text())='" + name + "']");
    }

    private static final By RENAME_INPUT = By.cssSelector("[data-testid='inline-playlist-name-input']");
    private static final By ALL_PLAYLIST_ITEMS = By.xpath("//section[@id='playlists']//a");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        try {
            waitVisible(allSongsLink);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    // ================== CORE ACTIONS ==================

    public void createPlaylist(String name) {
        safeClick(sidebarPlaylistBtn);
        safeClick(sidebarCreateBtn);
        type(sidebarCreateInput, name);
        sidebarCreateInput.sendKeys(Keys.ENTER);
        shortPause();
    }

    public void renamePlaylist(String oldName, String newName) {
        WebElement item = waitVisible(playlistByName(oldName));

        // Koel supports inline edit via double-click on playlist
        new Actions(driver)
                .moveToElement(item)
                .doubleClick(item)
                .perform();

        WebElement input = waitVisible(RENAME_INPUT);
        input.sendKeys(Keys.CONTROL, "a");
        input.sendKeys(Keys.BACK_SPACE);
        input.sendKeys(newName, Keys.ENTER);

        shortPause();
    }

    public void deletePlaylist(String name) {
        List<WebElement> list = driver.findElements(playlistByName(name));
        if (list.isEmpty()) {
            return; // nothing to delete, keep it simple
        }

        WebElement item = list.get(0);
        item.click();

        safeClick(deletePlaylistBtn);

        try {
            safeClick(okBtn);
        } catch (Exception ignored) {
        }

        shortPause();
    }

    // ================== SMALL HELPERS ==================

    public boolean playlistExists(String name) {
        return !driver.findElements(playlistByName(name)).isEmpty();
    }

    public List<String> listPlaylists() {
        List<String> out = new ArrayList<>();
        for (WebElement el : driver.findElements(ALL_PLAYLIST_ITEMS)) {
            out.add(el.getText().trim());
        }
        return out;
    }

    private void shortPause() {
        try {
            Thread.sleep(300); // tiny, just to let UI apply changes
        } catch (InterruptedException ignored) {}
    }
}
