package gd.EventModelStructure;

public class EventModel {

    private String productName;
    private Price productPrice;
    private PurchaseDate purchaseDate;
    private ProductCategory productCategory;
    private iPv4 clientIpAddress;

    @Override
    public String toString() {
        return productName + ',' + productPrice.getPrice() + ',' + purchaseDate.toString() +
                ',' + productCategory.getProductCategory() + ',' + clientIpAddress.getIpAddress() +
                "\n";
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductPrice(Price productPrice) {
        this.productPrice = productPrice;
    }

    public void setPurchaseDate(PurchaseDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public void setClientIpAddress(iPv4 clientIpAddress) {
        this.clientIpAddress = clientIpAddress;
    }
}
