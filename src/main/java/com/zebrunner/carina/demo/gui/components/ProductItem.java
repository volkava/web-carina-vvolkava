package com.zebrunner.carina.demo.gui.components;

import com.zebrunner.carina.demo.gui.pages.ProductDetailsPage;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class ProductItem extends AbstractUIObject {

    @FindBy(xpath = ".//h3[contains(@class, 'catalog-form__description')]//a[contains(@class, 'catalog-form__link')]")
    private ExtendedWebElement title;

    @FindBy(xpath = ".//img[contains(@class, 'catalog-form__image')]")
    private ExtendedWebElement photo;

    @FindBy(xpath = ".//a[contains(@href, '/prices')]")
    private ExtendedWebElement price;

    @FindBy(xpath = ".//a[contains(@class, 'catalog-form__rating')]")
    private ExtendedWebElement rating;

    @FindBy(xpath = ".//label[contains(@class, 'catalog-form__checkbox-label')]")
    private ExtendedWebElement compareCheckbox;

    @FindBy(xpath = ".//div[contains(@class, 'catalog-form__parameter-flex')]")
    private ExtendedWebElement descriptionSnippet;

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
        boolean hasTitle = title.isElementPresent(2);
        boolean hasPhoto = photo.isElementPresent(2);
        boolean hasPrice = price.isElementPresent(2);

        return hasTitle && hasPhoto && hasPrice;
    }

    public void toggleCompare() {
        compareCheckbox.clickByJs();
    }

    public ProductDetailsPage clickProductTitle() {
        title.scrollTo();
        title.click();
        return new ProductDetailsPage(getDriver());
    }

    public String getDescriptionText() {
        if(descriptionSnippet.isElementPresent(5)) {
            return descriptionSnippet.getText().trim();
        }
        return "";
    }
}