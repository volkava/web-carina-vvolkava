package com.zebrunner.carina.demo.gui.pages;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class ProductDetailsPage extends AbstractPage {

    @FindBy(xpath = "//h1[contains(@class, 'catalog-masthead__title') or contains(@class, 'catalog-title')]")
    private ExtendedWebElement productHeaderTitle;

    @FindBy(xpath = "//div[contains(@class, 'offers-description__specs') or contains(@class, 'catalog-masthead__description')]")
    private ExtendedWebElement productDescriptionSpecs;

    public ProductDetailsPage(WebDriver driver) {
        super(driver);
    }

    public String getProductHeaderTitle() {
        return productHeaderTitle.getText().trim();
    }

    public String getProductSpecsText() {
        return productDescriptionSpecs.getText().trim();
    }

    public String getDetailsDescriptionText() {
        return getProductSpecsText();
    }

    private String expectedDescriptionSnippet = "";

    public void setExpectedDescriptionSnippet(String snippet) {
        this.expectedDescriptionSnippet = snippet;
    }

    public String getExpectedDescriptionSnippet() {
        return this.expectedDescriptionSnippet;
    }

}