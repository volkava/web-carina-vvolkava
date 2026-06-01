package com.zebrunner.carina.demo.gui.pages;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import java.util.List;

public class ProductsComparePage extends AbstractPage {

    @FindBy(xpath = "//span[contains(@class, 'product-summary__item_title')]")
    private List<ExtendedWebElement> comparedProductTitles;

    public ProductsComparePage(WebDriver driver) {
        super(driver);
    }

    public boolean isProductInComparison(String expectedTitle) {
        for (ExtendedWebElement titleEl : comparedProductTitles) {
            if (titleEl.getText().toLowerCase().contains(expectedTitle.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}