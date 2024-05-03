package dev.abarmin.smart.collections;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.util.function.Consumer;

import static com.codeborne.selenide.Selenide.$;

public class CollectionAddPage {
    public static CollectionAddPage current() {
        return new CollectionAddPage();
    }

    public CollectionAddPage name(Consumer<SelenideElement> action) {
        action.accept($(By.id("name")));
        return this;
    }

    public CollectionAddPage saveButton(Consumer<SelenideElement> action) {
        action.accept($(By.id("collection-form-save-button")));
        return this;
    }
}
