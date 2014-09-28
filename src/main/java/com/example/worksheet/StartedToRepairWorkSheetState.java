package com.example.worksheet;

import com.example.mobile.part.Part;

public class StartedToRepairWorkSheetState implements WorkSheetState {

    
    @Override
    public void doWork(WorkSheet sheet) {
        System.out.println("Start working on worksheet...");

        for (Part part : sheet.getMobile().getParts()) {
            if (part.isFailing()) {
                sheet.getPartsToReplace().add(part);
            }
        }
        sheet.setState(new WaitingForPartsWorkSheetStatus());
        sheet.workOnSheet();
    }

}
