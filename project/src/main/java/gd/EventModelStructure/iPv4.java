package gd.EventModelStructure;

import java.util.Random;

public class iPv4 {
    private String ipAddress;

    public iPv4() {
        this.ipAddress = generateIp();
    }

    private String generateIp() {
        Random random = new Random();

        return random.nextInt(255) + "." + random.nextInt(255) + "." +
                random.nextInt(255) + "." + random.nextInt(255);
    }

    public String getIpAddress() {
        return ipAddress;
    }
}
