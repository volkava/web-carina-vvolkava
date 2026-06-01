package com.zebrunner.carina.demo;

import com.zebrunner.carina.core.IAbstractTest;
import com.zebrunner.carina.core.registrar.ownership.MethodOwner;
import com.zebrunner.carina.demo.gui.components.ProductItem;
import com.zebrunner.carina.demo.gui.pages.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;

public class OnlinerFirstTaskTest implements IAbstractTest {

    @Test
    @MethodOwner(owner = "vvolkava")
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
                    String.format("Product template is incomplete for item: '%s'. Mandatory layouts are missing.",
                            product.getProductTitle()));
        }
    }

    @Test
    @MethodOwner(owner = "vvolkava")
    public void verifyProductDescriptionDetailsTest() {
        OnlinerHomePage homePage = new OnlinerHomePage(getDriver());
        homePage.open();

        CatalogPage catalogPage = homePage.openCatalog();
        MobileCatalogPage mobileCatalogPage = catalogPage.openMobileCategory();

        mobileCatalogPage.selectBrand("Apple");

        String targetPhone = "Apple iPhone 17 256GB";

        ProductDetailsPage detailsPage = mobileCatalogPage.openProductByTitle(targetPhone);

        String expectedDescription = detailsPage.getExpectedDescriptionSnippet().trim();
        String actualDescription = detailsPage.getDetailsDescriptionText().trim();

        System.out.println("Expected Description from Listing: " + expectedDescription);
        System.out.println("Actual Description inside Details:  " + actualDescription);

        Assert.assertFalse(expectedDescription.isEmpty(), "The extracted catalog list text snippet was empty!");

        String normalizedExpected = expectedDescription.replaceAll("[\\s,\\n]+", " ").trim();
        String normalizedActual = actualDescription.replaceAll("[\\s,\\n]+", " ").trim();

        Assert.assertEquals(normalizedActual, normalizedExpected,
                "The description details on the product page do not match the catalog layout overview!");
    }

    @Test
    @MethodOwner(owner = "vvolkava")
    public void verifyMobileComparisonTest() {
        OnlinerHomePage homePage = new OnlinerHomePage(getDriver());
        homePage.open();
        Assert.assertTrue(homePage.isPageOpened(), "Onliner home page did not open!");

        homePage.acceptCookiesIfPresent();

        CatalogPage catalogPage = homePage.openCatalog();
        MobileCatalogPage mobilePage = catalogPage.openMobileCategory();

        mobilePage.selectBrand("Apple");

        List<ProductItem> productItems = mobilePage.getProducts();
        Assert.assertTrue(productItems.size() >= 2, "Not enough items in the grid to perform a comparison test!");

        ProductItem firstProduct = productItems.get(0);
        ProductItem secondProduct = productItems.get(1);

        String firstProductTitle = firstProduct.getProductTitle();
        String secondProductTitle = secondProduct.getProductTitle();

        firstProduct.toggleCompare();
        secondProduct.toggleCompare();

        ProductsComparePage comparePage = mobilePage.clickCompareBar();

        Assert.assertTrue(comparePage.isProductInComparison(firstProductTitle),
                "First selected product is missing from the comparison matrix screen: " + firstProductTitle);
        Assert.assertTrue(comparePage.isProductInComparison(secondProductTitle),
                "Second selected product is missing from the comparison matrix screen: " + secondProductTitle);
    }
}