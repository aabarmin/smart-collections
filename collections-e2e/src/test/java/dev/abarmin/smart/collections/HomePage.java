package dev.abarmin.smart.collections;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.util.function.Consumer;

import static com.codeborne.selenide.Selenide.$;

public class HomePage {
    private HomePage() {}

    public static HomePage open() {
        Selenide.open("http://localhost:8080/");
        return new HomePage();
    }

    public static HomePage current() {
        return new HomePage();
    }

    public HomePage loginLink(Consumer<SelenideElement> action) {
        action.accept($(By.id("home-link-login")));
        return this;
    }
}
