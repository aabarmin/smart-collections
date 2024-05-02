package dev.abarmin.smart.collections;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.util.function.Consumer;

import static com.codeborne.selenide.Selenide.$;

public class RegisterPage {
    private RegisterPage() {

    }

    public static RegisterPage current() {
        return new RegisterPage();
    }

    public RegisterPage fullName(Consumer<SelenideElement> withElement) {
        withElement.accept($(By.id("fullName")));
        return this;
    }

    public RegisterPage email(Consumer<SelenideElement> withElement) {
        withElement.accept($(By.id("email")));
        return this;
    }

    public RegisterPage password(Consumer<SelenideElement> withElement) {
        withElement.accept($(By.id("password")));
        return this;
    }

    public RegisterPage registerButton(Consumer<SelenideElement> withElement) {
        withElement.accept($(By.id("register-submit-button")));
        return this;
    }
}
