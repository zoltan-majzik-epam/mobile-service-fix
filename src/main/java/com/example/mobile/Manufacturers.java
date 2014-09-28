package com.example.mobile;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Manufacturers {

    private static final Random rand = new Random();
    private static Map<Manufacturer, String[]> models = new HashMap<>();

    public static enum Manufacturer {
        SAMSUNG("Samsung"), APPLE("Apple"), HTC("HTC"), HUAWEI("Huawei");

        private String printName;

        Manufacturer(String s) {
            this.printName = s;
        }

        @Override
        public String toString() {
            return this.printName;
        }
    }

    // static init block for the model names
    static {
        
        String[] samsungModels = { "Galaxy S3", "Galaxy S4", "Galaxy Note" };
        String[] htcModels = { "One", "Desire 610", "One Mini" };
        String[] huaweiModels = { "Ascend P7", "Ascend P2", "Ascend G630" };
        String[] appleModels = { "iPhone 4s", "iPhone 5s", "iPhone5c" };

        models.put(Manufacturer.SAMSUNG, samsungModels);
        models.put(Manufacturer.HTC, htcModels);
        models.put(Manufacturer.HUAWEI, huaweiModels);
        models.put(Manufacturer.APPLE, appleModels);
    }

    public static String[] getManufacturersModels(Manufacturer manufacturer) {
        return models.get(manufacturer);
    }

    public static String getRandomModelForManufacturer(Manufacturer manufacturer) {
        String[] currentModels = models.get(manufacturer);
        return currentModels[rand.nextInt(currentModels.length)];
    }

    public static Manufacturer getRandomManufacturer() {
        Manufacturer[] m = Manufacturer.values();
        return m[rand.nextInt(m.length)];
    }
}
