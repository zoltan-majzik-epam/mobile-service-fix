package com.example.worksheet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.example.mobile.part.Display;
import com.example.mobile.part.Keyboard;
import com.example.mobile.part.Microphone;
import com.example.mobile.part.MotherBoard;
import com.example.mobile.part.Part;
import com.example.mobile.part.PartType;
import com.example.mobile.part.PowerSwitch;
import com.example.mobile.part.Speaker;
import com.example.mobile.part.VolumeButtons;
import com.example.mobileservice.PartTypeInStockListener;
import com.example.mobileservice.Stock;

public class WaitingForPartsWorkSheetStatus implements WorkSheetState, PartTypeInStockListener {

    private WorkSheet sheet;

    @Override
    public void doWork(WorkSheet sheet) {
        this.sheet = sheet;
        Stock stock = sheet.getStock();

        if (sheet.getPartsToReplace() == null) {
            sheet.setState(new FinishedWorkSheetState());
            stock.removePartInStockListener(this);
            sheet.workOnSheet();
        } else {
            List<Part> replacedParts = sheet.getReplacedParts();
            List<Part> partsToReplace = sheet.getPartsToReplace();

            for (Iterator<Part> it = partsToReplace.iterator(); it.hasNext();) {
                Part partToReplace = it.next();
                PartType type = partToReplace.getType();

                synchronized (stock.getClass()) {
                    if (stock.inStock(type)) {
                        stock.decrementStock(type);
                        if (replacedParts == null) {
                            replacedParts = new ArrayList<Part>();
                            sheet.setReplacedParts(replacedParts);
                        }
                        Part replacePart = createPartOfType(type, partToReplace.getName());
                        replacedParts.add(replacePart);
                        partToReplace.setReplaced(true);
                        // part replaced, so remove from the list
                        it.remove();
                    } else {
                        stock.addOrderForMissingPart(type);
                      
                        stock.addPartInStockListener(this);
                    }
                }
            }
            if (partsToReplace.isEmpty()) {
                sheet.setState(new FinishedWorkSheetState());
                stock.removePartInStockListener(this);
                sheet.workOnSheet();
            }
        }
        
    }

    public Part createPartOfType(PartType type, String name) {
        switch (type) {
        case DISPLAY:
            return new Display(false, name);
        case KEYBOARD:
            return new Keyboard(false, name);
        case MICROPHONE:
            return new Microphone(false, name);
        case MOTHERBOARD:
            return new MotherBoard(false, name);
        case POWER_SWITCH:
            return new PowerSwitch(false, name);
        case SPEAKER:
            return new Speaker(false, name);
        case VOLUME_BUTTONS:
            return new VolumeButtons(false, name);

        default:
            throw new IllegalArgumentException("Unkown type: " + type);
        }
    }

    @Override
    public void partTypeInStockListener(PartType type) {
        for (Part partToReplace : sheet.getPartsToReplace()) {
            if (partToReplace.getType() == type) {
                doWork(sheet);
            }
        }
    }

}
