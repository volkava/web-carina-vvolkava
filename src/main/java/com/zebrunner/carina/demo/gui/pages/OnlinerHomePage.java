package com.zebrunner.carina.demo.gui.pages;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class OnlinerHomePage extends AbstractPage {

    @FindBy(xpath = "//span[contains(@class, 'b-main-navigation__text') and text()='Каталог']")
    private ExtendedWebElement catalogLink;

    public OnlinerHomePage(WebDriver driver) {
        super(driver);
        setPageAbsoluteURL("https://www.onliner.by/");
    }

    public CatalogPage openCatalog() {
        catalogLink.click();
        return new CatalogPage(getDriver());
    }

    public void acceptCookiesIfPresent() {
        ExtendedWebElement acceptCookies = findExtendedWebElement(By.xpath("//button[contains(@class, 'button') and contains(text(), 'Принять')]"), 3);
        if (acceptCookies != null && acceptCookies.isElementPresent(2)) {
            acceptCookies.click();
        }
    }
}