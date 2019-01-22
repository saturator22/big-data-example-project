package gd.EventModelStructure;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class PurchaseDate {

    private LocalDateTime date;

    public PurchaseDate() {
        this.date = generateDate();
    }

    private LocalDateTime generateDate() {
        Random random = new Random();
        int randomDay = random.nextInt(8) + 1;
        int randomHour = random.nextInt(23) + 1;
        int randomMinute = random.nextInt(59) + 1;
        int randomSecond = random.nextInt(59) + 1;

        return LocalDateTime.of(2019, 7, randomDay, randomHour, randomMinute, randomSecond);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return date.format(formatter);
    }
}
