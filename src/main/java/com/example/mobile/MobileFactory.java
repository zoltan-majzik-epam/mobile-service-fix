package com.example.mobile;

import java.util.Random;

import com.example.mobile.Manufacturers.Manufacturer;
import com.example.mobile.part.Display;
import com.example.mobile.part.Microphone;
import com.example.mobile.part.MotherBoard;
import com.example.mobile.part.PowerSwitch;
import com.example.mobile.part.Speaker;
import com.example.mobile.part.VolumeButtons;

public class MobileFactory {

    private static final Random random = new Random();

    public Mobile createRandomPhone() {
        Manufacturer m = Manufacturers.getRandomManufacturer();
        return createRandomPhoneForManufacturer(m);
    }

    private Mobile createRandomPhoneForManufacturer(Manufacturer manufacturer) {

        String manufacturerName = manufacturer.toString();
        String model = Manufacturers.getRandomModelForManufacturer(manufacturer);

        Mobile mobile = Mobile.getBuilder()
                .manufacturer(manufacturer)
                .model(model)
                .addPart(new Display(random.nextBoolean(), manufacturerName + " Display"))
                .addPart(new Microphone(random.nextBoolean(), manufacturerName + " Microphone"))
                .addPart(new MotherBoard(random.nextBoolean(), manufacturerName + " " + model + " Motherboard"))
                .addPart(new PowerSwitch(random.nextBoolean(), manufacturerName + " Power Switch"))
                .addPart(new Speaker(random.nextBoolean(), manufacturerName + " Speakers"))
                .addPart(new VolumeButtons(random.nextBoolean(), manufacturerName + " Volume Buttons"))
                .build();

        return mobile;
    }
}
