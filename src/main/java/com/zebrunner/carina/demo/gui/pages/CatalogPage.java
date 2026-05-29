package com.zebrunner.carina.demo.gui.pages;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class CatalogPage extends AbstractPage {

    @FindBy(xpath = "//div[contains(@class, 'catalog-form__description') and normalize-space(text())='Телефоны']")
    private ExtendedWebElement mobileLink;

    @FindBy(xpath = "//div[contains(@class, 'auth-popup')]//*[contains(@class, 'close')]")
    private ExtendedWebElement authPopupClose;

    public CatalogPage(WebDriver driver) {
        super(driver);
    }

    public MobileCatalogPage openMobileCategory() {
        mobileLink.scrollTo();
        mobileLink.clickByJs();
        return new MobileCatalogPage(getDriver());
    }
}