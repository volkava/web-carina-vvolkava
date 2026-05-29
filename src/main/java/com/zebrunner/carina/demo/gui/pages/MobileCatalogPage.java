package com.zebrunner.carina.demo.gui.pages;

import com.zebrunner.carina.demo.gui.components.ProductItem;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class MobileCatalogPage extends AbstractPage {

    @FindBy(xpath = "//div[contains(@class, 'catalog-product')]")
    private List<ProductItem> products;

    public MobileCatalogPage(WebDriver driver) {
        super(driver);
        setPageAbsoluteURL("https://catalog.onliner.by/mobile");
    }

    public List<ProductItem> getProducts() {
        waitUntil(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.xpath("//div[contains(@class, 'catalog-form__offers')]//div[contains(@class, 'catalog-product')]")), 15);
        return products;
    }

        public void selectBrand(String brandName) {
            String brandXpath = "//div[@id='schema-filter']//span[contains(@class, 'checkbox__text') and text()='" + brandName + "']";
            ExtendedWebElement brandCheckbox = findExtendedWebElement(By.xpath(brandXpath), 15);

            if (brandCheckbox != null) {
                brandCheckbox.scrollTo();
                brandCheckbox.click();

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            } else {
                throw new RuntimeException("Could not find the filter checkbox for brand: " + brandName);
            }
        }
    }
