package com.example.mobileservice;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vrg
 */
public final class Client {

    private final static AtomicLong counter = new AtomicLong();

    private final long id = counter.incrementAndGet();

    private MobileService service;
    private final Mobile mobile;
    private Random random = new Random();
    private static final Manufacturer[] manufacturers = Manufacturer.values();
    private static final String[] samsungModels = {"Galaxy S3", "Galaxy S4", "Galaxy Note"};
    private static final String[] htcModels = {"One", "Desire 610", "One Mini"};
    private static final String[] huaweiModels = {"Ascend P7", "Ascend P2", "Ascend G630"};
    private static final String[] appleModels = {"iPhone 4s", "iPhone 5s", "iPhone5c"};

    public Client(MobileService service) {
        this.service = service;
        this.mobile = createRandomPhone();
    }

    public Mobile createRandomPhone() {
        Manufacturer m = manufacturers[random.nextInt(manufacturers.length)];
        switch (m) {
            case APPLE:
                return createApplePhone();
            case HTC:
                return createHTCPhone();
            case HUAWEI:
                return createHuaweiPhone();
            case SAMSUNG:
                return createSamsungPhone();
            default:
                throw new AssertionError("Unexpected manufacturer: " + m);
        }
    }

    private Mobile createSamsungPhone() {
        Mobile mobile = new Mobile();
        mobile.manufacturer = Manufacturer.SAMSUNG;
        mobile.model = samsungModels[random.nextInt(samsungModels.length)];
        mobile.display = new Part(PartType.DISPLAY, random.nextBoolean(), "Samsung Display");
        mobile.microphone = new Part(PartType.MICROPHONE, random.nextBoolean(), "Samsung Microphone");
        mobile.motherBoard = new Part(PartType.MOTHERBOARD, random.nextBoolean(), "Samsung " + mobile.model + " Motherboard");
        mobile.powerSwitch = new Part(PartType.POWER_SWITCH, random.nextBoolean(), "Samsung Power Switch");
        mobile.speaker = new Part(PartType.SPEAKER, random.nextBoolean(), "Samsung Speaker");
        mobile.volumeButtons = new Part(PartType.VOLUME_BUTTONS, random.nextBoolean(), "Samsung Volume Buttons");
        return mobile;
    }

    private Mobile createApplePhone() {
        Mobile mobile = new Mobile();
        mobile.manufacturer = Manufacturer.APPLE;
        mobile.model = appleModels[random.nextInt(appleModels.length)];
        mobile.display = new Part(PartType.DISPLAY, random.nextBoolean(), "Apple Display");
        mobile.microphone = new Part(PartType.MICROPHONE, random.nextBoolean(), "Apple Microphone");
        mobile.motherBoard = new Part(PartType.MOTHERBOARD, random.nextBoolean(), "Apple " + mobile.model + " Motherboard");
        mobile.powerSwitch = new Part(PartType.POWER_SWITCH, random.nextBoolean(), "Apple Power Switch");
        mobile.speaker = new Part(PartType.SPEAKER, random.nextBoolean(), "Apple Speaker");
        mobile.volumeButtons = new Part(PartType.VOLUME_BUTTONS, random.nextBoolean(), "Apple Volume Buttons");
        return mobile;
    }

    private Mobile createHTCPhone() {
        Mobile mobile = new Mobile();
        mobile.manufacturer = Manufacturer.HTC;
        mobile.model = htcModels[random.nextInt(htcModels.length)];
        mobile.display = new Part(PartType.DISPLAY, random.nextBoolean(), "HTC Display");
        mobile.microphone = new Part(PartType.MICROPHONE, random.nextBoolean(), "HTC Microphone");
        mobile.motherBoard = new Part(PartType.MOTHERBOARD, random.nextBoolean(), "HTC " + mobile.model + " Motherboard");
        mobile.powerSwitch = new Part(PartType.POWER_SWITCH, random.nextBoolean(), "HTC Power Switch");
        mobile.speaker = new Part(PartType.SPEAKER, random.nextBoolean(), "HTC Speaker");
        mobile.volumeButtons = new Part(PartType.VOLUME_BUTTONS, random.nextBoolean(), "HTC Volume Buttons");
        return mobile;
    }

    private Mobile createHuaweiPhone() {
        Mobile mobile = new Mobile();
        mobile.manufacturer = Manufacturer.HUAWEI;
        mobile.model = huaweiModels[random.nextInt(huaweiModels.length)];
        mobile.display = new Part(PartType.DISPLAY, random.nextBoolean(), "Huawei Display");
        mobile.microphone = new Part(PartType.MICROPHONE, random.nextBoolean(), "Huawei Microphone");
        mobile.motherBoard = new Part(PartType.MOTHERBOARD, random.nextBoolean(), "Huawei " + mobile.model + " Motherboard");
        mobile.powerSwitch = new Part(PartType.POWER_SWITCH, random.nextBoolean(), "Huawei Power Switch");
        mobile.speaker = new Part(PartType.SPEAKER, random.nextBoolean(), "Huawei Speaker");
        mobile.volumeButtons = new Part(PartType.VOLUME_BUTTONS, random.nextBoolean(), "Huawei Volume Buttons");
        return mobile;
    }

    public void log(String message) {
        System.out.println("Client #" + id + ": " + message);
    }

    public void sendMobileToService() {
        log("Sending in mobile: " + mobile);
        WorkSheet workSheet = service.sendIn(mobile);
        WorkSheet.Status lastKnownStatus = workSheet.status;
        while (lastKnownStatus != WorkSheet.Status.FINISHED) {
            while (workSheet.status == lastKnownStatus) {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            log("Status changed to " + workSheet.status);
            lastKnownStatus = workSheet.status;
        }
    }

}
