package dev.abarmin.smart.collections;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.util.function.Consumer;

import static com.codeborne.selenide.Selenide.$;

public class RegisteredPage {
    private RegisteredPage() {

    }

    public static RegisteredPage current() {
        return new RegisteredPage();
    }

    public RegisteredPage toLoginLink(Consumer<SelenideElement> action) {
        action.accept($(By.id("registered-link-to-login")));
        return this;
    }
}
