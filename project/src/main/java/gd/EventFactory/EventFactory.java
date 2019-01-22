package gd.EventFactory;

import gd.EventModelStructure.*;
import java.util.Random;

public class EventFactory {

    public Event getRandomEvent() {
        Event event = new Event();

        Random random = new Random();
        String randomName = "product" + (random.nextInt(4) + 1);

        event.setProductName(randomName);
        event.setClientIpAddress(new iPv4());
        event.setProductCategory(new ProductCategory());
        event.setProductPrice(new Price());
        event.setPurchaseDate(new PurchaseDate());

        return event;
    }
}
