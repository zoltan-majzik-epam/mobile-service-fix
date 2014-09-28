package com.example.mobileservice;

import com.example.mobile.Mobile;
import com.example.mobileservice.supplier.Supplier;
import com.example.worksheet.WorkSheet;
import com.example.worksheet.WorkSheetReadyListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MobileService implements WorkSheetReadyListener {

    private volatile List<WorkSheet> worksheetsToProcess;
    private Map<WorkSheet, WorkSheetReadyListener> mobileReadyListeners;

    public MobileService() {
        worksheetsToProcess = new ArrayList<>();
        mobileReadyListeners = new HashMap<>();
    }

    public synchronized WorkSheet sendIn(Mobile mobile) {
        WorkSheet worksheet = createWorkSheet(mobile);
        worksheetsToProcess.add(worksheet);
        return worksheet;
    }

    public synchronized void processWorksheets() {
        for (Iterator<WorkSheet> it = worksheetsToProcess.iterator(); it.hasNext();) {
            WorkSheet workSheet = it.next();
            workSheet.registerWorkSheetReadyListener(this);
            workSheet.workOnSheet();
        }
    }

    public synchronized void addMobileReadyListeners(WorkSheet sheet, WorkSheetReadyListener listener) {
        mobileReadyListeners.put(sheet, listener);
    }

    public WorkSheet createWorkSheet(Mobile mobile) {
        WorkSheet ws = new WorkSheet(mobile, new Date());
        return ws;
    }

    @Override
    public synchronized void workSheetReady(WorkSheet workSheet) {
        WorkSheetReadyListener listener = mobileReadyListeners.get(workSheet);
        if (listener != null) {
            listener.workSheetReady(workSheet);
            worksheetsToProcess.remove(workSheet);

        }
    }
}
