package com.example.mobileservice;


import java.util.concurrent.atomic.AtomicLong;


import com.example.mobile.Mobile;
import com.example.mobile.MobileFactory;
import com.example.worksheet.WorkSheet;
import com.example.worksheet.WorkSheetReadyListener;

/**
 *
 * @author vrg
 */
public final class Client implements WorkSheetReadyListener {

    private final static AtomicLong counter = new AtomicLong();

    private final long id;
    private MobileService service;
    private final Mobile mobile;
   

    public Client(MobileService service) {
        this.id = counter.incrementAndGet();
        this.service = service;
        MobileFactory mobileFactory = new MobileFactory();
        this.mobile = mobileFactory.createRandomPhone();
    }

    
    
    public void log(String message) {
        System.out.println("Client #" + id + ": " + message);
    }

    public void sendMobileToService() {
        log("Sending in mobile: " + mobile);
        WorkSheet workSheet = service.sendIn(mobile);
        service.addMobileReadyListeners(workSheet, this);
    }



    @Override
    public void workSheetReady(WorkSheet workSheet) {
        log("Mobile returned to user.");
        workSheet.getMobile();
    }

}
