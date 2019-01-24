package gd.EventFactory;

import gd.EventModelStructure.*;
import java.util.Random;

public class EventFactory {

    public EventModel getRandomEvent() {
        EventModel eventModel = new EventModel();

        Random random = new Random();
        String randomName = "product" + (random.nextInt(4) + 1);

        eventModel.setProductName(randomName);
        eventModel.setClientIpAddress(new iPv4());
        eventModel.setProductCategory(new ProductCategory());
        eventModel.setProductPrice(new Price());
        eventModel.setPurchaseDate(new PurchaseDate());

        return eventModel;
    }
}
