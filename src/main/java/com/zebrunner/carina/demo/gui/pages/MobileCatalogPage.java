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

    @FindBy(xpath = "//div[contains(@class, 'catalog-form__offers-unit')]")
    private List<ProductItem> products;

    @FindBy(xpath = "//a[contains(@class, 'catalog-interaction__sub')]")
    private ExtendedWebElement floatingCompareButton;

    @FindBy(xpath = "//div[contains(@class, 'auth-popup') or contains(@class, 'popover-style')]")
    private ExtendedWebElement authPopup;

    @FindBy(xpath = "//div[contains(@class, 'auth-popup-close') or contains(@class, 'popover-style__bottom')]//span")
    private ExtendedWebElement authPopupClose;

    public MobileCatalogPage(WebDriver driver) {
        super(driver);
        setPageAbsoluteURL("https://catalog.onliner.by/mobile");
    }

    public List<ProductItem> getProducts() {
        waitUntil(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.xpath("//div[contains(@class, 'catalog-form__offers-unit')]")), 15);
        return products;
    }

    public void selectBrand(String brandName) {
        dismissAuthPopupIfPresent();

        String dynamicBrandXpath = "(//*[normalize-space(text())='Производитель']"
                + "/following::*[normalize-space(.)='" + brandName + "' "
                + "and not(self::a) and not(ancestor::a)])[1]";

        ExtendedWebElement brandCheckbox = findExtendedWebElement(By.xpath(dynamicBrandXpath), 15);

        if (brandCheckbox != null && brandCheckbox.isElementPresent(5)) {
            brandCheckbox.scrollTo();
            try {
                brandCheckbox.click();
            } catch (Exception e) {
                brandCheckbox.clickByJs();
            }

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } else {
            throw new RuntimeException("Could not find the filter checkbox for brand: " + brandName);
        }
    }

    private void dismissAuthPopupIfPresent() {
        try {
            if (authPopup.isElementPresent(3)) {
                authPopupClose.clickIfPresent(2);
                authPopup.waitUntilElementDisappear(5);
            }
        } catch (Exception ignored) {

        }
    }


    public ProductDetailsPage openProductByTitle(String targetTitle) {
        List<ProductItem> activeCards = getProducts();

        for (ProductItem product : activeCards) {
            if (product.isTitleMatch(targetTitle)) {
                String catalogSnippet = product.getDescriptionText();

                ProductDetailsPage detailsPage = product.clickProductTitle();

                detailsPage.setExpectedDescriptionSnippet(catalogSnippet);
                return detailsPage;
            }
        }
        throw new RuntimeException("Could not find an active product card matching text layout target: " + targetTitle);
    }

    public ProductsComparePage clickCompareBar() {
        floatingCompareButton.scrollTo();
        floatingCompareButton.click();
        return new ProductsComparePage(driver);
    }
}