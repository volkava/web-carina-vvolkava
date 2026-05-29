package com.zebrunner.carina.demo.gui.components;

import com.zebrunner.carina.demo.gui.pages.ProductDetailsPage;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class ProductItem extends AbstractUIObject {

    @FindBy(xpath = ".//span[contains(@class, 'catalog-product__title')]//span[not(contains(@class, 'icon'))]")
    private ExtendedWebElement title;

    @FindBy(xpath = ".//div[contains(@class, 'catalog-product__image')]//img")
    private ExtendedWebElement photo;

    @FindBy(xpath = ".//a[contains(@href, '/prices') or contains(@class, 'catalog-product__price')]")
    private ExtendedWebElement price;

    @FindBy(xpath = ".//a[contains(@href, '/reviews') or contains(@class, 'catalog-product__rating')]")
    private ExtendedWebElement rating;

    public ProductItem(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public boolean isTitlePresent() {
        return title.isElementPresent(2);
    }

    public String getProductTitle() {
        return title.getText().trim();
    }

    public boolean isTitleMatch(String targetTitle) {
        return getProductTitle().toLowerCase().contains(targetTitle.toLowerCase());
    }

    public boolean isTemplateComplete() {
        return title.isElementPresent(2) &&
                photo.isElementPresent(2) &&
                price.isElementPresent(2);
    }

    public ProductDetailsPage clickProductTitle() {
        title.scrollTo();
        title.click();

        return new ProductDetailsPage(getDriver());
    }
}