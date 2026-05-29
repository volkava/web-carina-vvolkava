package com.zebrunner.carina.demo;

import com.zebrunner.carina.core.IAbstractTest;
import com.zebrunner.carina.demo.gui.components.ProductItem;
import com.zebrunner.carina.demo.gui.pages.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;

public class OnlinerFirstTaskTest implements IAbstractTest {

    @Test
    public void verifyMobileProductTemplateTest() {
        OnlinerHomePage homePage = new OnlinerHomePage(getDriver());
        homePage.open();
        Assert.assertTrue(homePage.isPageOpened(), "Onliner home page did not open!");

        CatalogPage catalogPage = homePage.openCatalog();
        MobileCatalogPage mobilePage = catalogPage.openMobileCategory();

        List<ProductItem> products = mobilePage.getProducts();
        Assert.assertFalse(products.isEmpty(), "No mobile products were found on the page grid!");

        for (ProductItem product : products) {
            if (!product.isTitlePresent()) {
                continue;
            }
            Assert.assertTrue(product.isTemplateComplete(),
                    String.format("Product template is incomplete for item: '%s'. Check if title, photo, price, or rating is missing.",
                            product.getProductTitle()));
        }
    }

    @Test
    public void verifyProductDescriptionDetailsTest() {
        OnlinerHomePage homePage = new OnlinerHomePage(getDriver());
        homePage.open();
        Assert.assertTrue(homePage.isPageOpened(), "Onliner home page did not open!");

        homePage.acceptCookiesIfPresent();

        CatalogPage catalogPage = homePage.openCatalog();
        MobileCatalogPage mobilePage = catalogPage.openMobileCategory();

        mobilePage.selectBrand("Apple");

        String currentUrl = getDriver().getCurrentUrl();
        if (currentUrl.contains("/mobile/apple/") && !currentUrl.contains("?")) {
            System.out.println("DEBUG - Test routed straight to a product page view. URL: " + currentUrl);
            ProductDetailsPage productDetailsPage = new ProductDetailsPage(getDriver());

            String actualHeaderTitle = productDetailsPage.getProductHeaderTitle();
            Assert.assertTrue(actualHeaderTitle.toLowerCase().contains("apple iphone 17"),
                    "Expected page title to reference iPhone 17, but found: " + actualHeaderTitle);
            return;
        }

        List<ProductItem> productItems = mobilePage.getProducts();
        ProductItem targetCard = null;

        System.out.println("DEBUG - Number of product cards found in list: " + productItems.size());

        for (ProductItem product : productItems) {
            if (product.isTitleMatch("iPhone 17")) {
                targetCard = product;
                break;
            }
        }

        Assert.assertNotNull(targetCard, "Could not find a product card matching 'iPhone 17' on the catalog grid!");

        ProductDetailsPage productDetailsPage = targetCard.clickProductTitle();

        String actualHeaderTitle = productDetailsPage.getProductHeaderTitle();
        String actualSpecsText = productDetailsPage.getProductSpecsText();

        System.out.println("DEBUG - Opened Product Page Header: " + actualHeaderTitle);
        System.out.println("DEBUG - Opened Product Specs: " + actualSpecsText);

        Assert.assertTrue(actualHeaderTitle.toLowerCase().contains("apple iphone 17"),
                "The product page title does not look like an iPhone 17 page! Found: " + actualHeaderTitle);

        Assert.assertTrue(actualSpecsText.contains("256 ГБ"),
                "The product specifications do not mention '256 ГБ'. Found: " + actualSpecsText);
    }
}