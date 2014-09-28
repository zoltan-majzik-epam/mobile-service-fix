package com.example.worksheet;

import java.util.Date;

public class FinishedWorkSheetState implements WorkSheetState {

    @Override
    public void doWork(WorkSheet sheet) {
        sheet.setFinishedToRepairOn(new Date());
        sheet.workSheetReadyListener.workSheetReady(sheet);
    }

}
