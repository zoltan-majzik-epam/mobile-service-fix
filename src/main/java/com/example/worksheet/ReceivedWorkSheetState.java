package com.example.worksheet;

import java.util.Date;

public class ReceivedWorkSheetState implements WorkSheetState {

    @Override
    public void doWork(WorkSheet sheet) {
        sheet.setState(new StartedToRepairWorkSheetState());
        sheet.setStartedToRepairOn(new Date());
        sheet.workOnSheet();
    }

}
