package com.example.worksheet;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;



import com.example.mobile.Mobile;
import com.example.mobile.part.Part;
import com.example.mobileservice.Stock;

/**
 *
 * @author vrg
 */
public class WorkSheet  {


    private Mobile mobile;

    private List<Part> partsToReplace;
    private List<Part> replacedParts;
    private Date receivedOn;
    private Date startedToRepairOn;
    private Date finishedToRepairOn;
    private Date givenBackOn;

    private WorkSheetState state;
    private Stock stock;

    WorkSheetReadyListener workSheetReadyListener;
    
    public Stock getStock() {
        return stock;
    }


    public void setStock(Stock stock) {
        this.stock = stock;
    }


    public WorkSheet(Mobile mobile, Date receivedDate) {
        this.mobile = mobile;
        this.receivedOn = receivedDate;
        this.state = new ReceivedWorkSheetState();
        this.partsToReplace = new ArrayList<>();
        this.replacedParts = new ArrayList<>();
        this.receivedOn = new Date();
        stock = Stock.getInstance();
    }

    
    public Mobile getMobile() {
        return mobile;
    }

    public void setMobile(Mobile mobile) {
        this.mobile = mobile;
    }

    public List<Part> getPartsToReplace() {
        return partsToReplace;
    }

    public void setPartsToReplace(List<Part> partsToReplace) {
        this.partsToReplace = partsToReplace;
    }

    public List<Part> getReplacedParts() {
        return replacedParts;
    }

    public void setReplacedParts(List<Part> replacedParts) {
        this.replacedParts = replacedParts;
    }

    public Date getReceivedOn() {
        return receivedOn;
    }

    public void setReceivedOn(Date receivedOn) {
        this.receivedOn = receivedOn;
    }

    public Date getStartedToRepairOn() {
        return startedToRepairOn;
    }

    public void setStartedToRepairOn(Date startedToRepairOn) {
        this.startedToRepairOn = startedToRepairOn;
    }

    public Date getFinishedToRepairOn() {
        return finishedToRepairOn;
    }

    public void setFinishedToRepairOn(Date finishedToRepairOn) {
        this.finishedToRepairOn = finishedToRepairOn;
    }

    public Date getGivenBackOn() {
        return givenBackOn;
    }

    public void setGivenBackOn(Date givenBackOn) {
        this.givenBackOn = givenBackOn;
    }

    
    public void registerWorkSheetReadyListener(WorkSheetReadyListener listener) {
        this.workSheetReadyListener = listener;
    }
    

    public synchronized void setState(WorkSheetState state) {
        this.state = state;
    }
    
    
    public synchronized void workOnSheet() {
        state.doWork(this);
    }
    
    
}
