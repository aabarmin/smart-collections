package dev.abarmin.smart.collections;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class HappyPathTest {
    private String userEmail;
    private String userPassword;
    private String userFullName;

    @BeforeEach
    void setUp() {
        userEmail = String.format("%s@test.com", RandomStringUtils.randomAlphabetic(10));
        userPassword = RandomStringUtils.randomAlphanumeric(32);
        userFullName = "Test User " + RandomStringUtils.randomAlphabetic(10);
    }

    @Test
    @Disabled
    void registerAndCreateCollection() {
        HomePage.open()
                .loginLink(el -> el.click());

        LoginPage.current()
                .registerLink(el -> el.click());

        RegisterPage.current()
                .fullName(el -> el.val(userFullName))
                .email(el -> el.val(userEmail))
                .password(el -> el.val(userPassword))
                .registerButton(el -> el.click());

        RegisteredPage.current()
                .toLoginLink(el -> el.click());

        LoginPage.current()
                .emailField(el -> el.val(userEmail))
                .passwordField(el -> el.val(userPassword))
                .loginButton(el -> el.click());

        CollectionsPage.current()
                .createNewButton(el -> el.click());

        CollectionAddPage.current()
                .name(el -> el.val("Collection " + RandomStringUtils.randomAlphabetic(32)))
                .saveButton(el -> el.click());


    }
}
