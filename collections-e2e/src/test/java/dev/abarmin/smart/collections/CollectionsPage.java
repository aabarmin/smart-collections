package dev.abarmin.smart.collections;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.util.function.Consumer;

import static com.codeborne.selenide.Selenide.$;

public class CollectionsPage {
    private CollectionsPage () {

    }

    public static CollectionsPage current() {
        return new CollectionsPage();
    }

    public CollectionsPage createNewButton(Consumer<SelenideElement> action) {
        action.accept($(By.id("collections-new-button")));
        return this;
    }

    public CollectionsPage addNewAlbum(String collection, Consumer<SelenideElement> action) {
//        $(By.id("")).
        return this;
    }
}
