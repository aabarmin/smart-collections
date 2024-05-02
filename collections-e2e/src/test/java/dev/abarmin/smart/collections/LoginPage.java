package dev.abarmin.smart.collections;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.util.function.Consumer;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private LoginPage() {}

    public static LoginPage current() {
        return new LoginPage();
    }

    public LoginPage emailField(Consumer<SelenideElement> action) {
        action.accept($(By.id("email")));
        return this;
    }

    public LoginPage passwordField(Consumer<SelenideElement> action) {
        action.accept($(By.id("password")));
        return this;
    }

    public LoginPage registerLink(Consumer<SelenideElement> action) {
        action.accept($(By.id("register-link")));
        return this;
    }

    public LoginPage loginButton(Consumer<SelenideElement> action) {
        action.accept($(By.id("login-login-button")));
        return this;
    }
}
