package gd.EventModelStructure;

import java.util.Random;

public class Price {

    double price;

    public Price() {
        this.price = generatePrice();
    }

    private Double generatePrice() {
        Random random = new Random();
        Double randomPrice = random.nextGaussian() * 10000;

        if (randomPrice < 0) {
            randomPrice = randomPrice * -1;
        }

        return (double) Math.round(randomPrice*100)/100;
    }

    public double getPrice() {
        return price;
    }
}
