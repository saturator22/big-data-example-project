package gd.EventModelStructure;

import java.util.Random;

public class ProductCategory {

    private String[] productsCategories = {"FMCG", "RTV", "AGD"};
    private String productCategory;

    public ProductCategory() {
        this.productCategory = generateProduct();
    }

    private String generateProduct() {
        Random random = new Random();
        return this.productsCategories[random.nextInt(productsCategories.length)];
    }

    public String getProductCategory() {
        return productCategory;
    }
}
