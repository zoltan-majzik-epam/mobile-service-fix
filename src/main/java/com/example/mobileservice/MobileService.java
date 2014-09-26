package com.example.mobileservice;

import com.example.mobileservice.supplier.Supplier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author vrg
 */
public class MobileService {

    private HashMap<PartType, Integer> stock = new HashMap<>();
    private List<WorkSheet>worksheetsToProcess = new ArrayList<>();
    private Map<PartType,Long>orderIdByPartType = new HashMap<>();
    private Supplier supplier;

    public MobileService(Supplier supplier) {
        this.supplier = supplier;
    }
    
    public synchronized WorkSheet sendIn(Mobile mobile) {
        WorkSheet worksheet = createWorkSheet(mobile);
        collectFailingParts(worksheet);
        worksheetsToProcess.add(worksheet);
        return worksheet;
    }

    public synchronized boolean replaceAllPossibleFailingParts(WorkSheet ws) {
        if (ws.partsToReplace == null) {
            //nothing to replace
            ws.status = WorkSheet.Status.FINISHED;
            return true;
        }
        for (Iterator<Part> it = ws.partsToReplace.iterator(); it.hasNext();) {
            Part partToReplace = it.next();
            PartType type = partToReplace.type;
            Integer inStockQuantity = stock.get(type);
            if (inStockQuantity != null && inStockQuantity > 0) {
                inStockQuantity = inStockQuantity - 1;
                stock.put(type, inStockQuantity);
                if (ws.replacedParts == null) {
                    ws.replacedParts = new ArrayList<>();
                }
                ws.replacedParts.add(new ReplacedPart(type, partToReplace.name));
                it.remove();
            } else {
                addOrderForMissingPart(type);
                ws.status = WorkSheet.Status.WAITING_FOR_PARTS;
            }
        }
        
        if (ws.partsToReplace.isEmpty()) {
            ws.status = WorkSheet.Status.FINISHED;
            return true;
        } else {
            return false;
        }
    }
    
    public synchronized void addOrderForMissingPart(PartType type) {
        if (!orderIdByPartType.containsKey(type)) {
            orderIdByPartType.put(type, null);
        }
    }
    
    public synchronized void processWorksheets() {
        
        for (Iterator<WorkSheet> it = worksheetsToProcess.iterator(); it.hasNext();) {
            WorkSheet workSheet = it.next();
            boolean finished = replaceAllPossibleFailingParts(workSheet);
            if (finished) {
                it.remove();
            }
        }
    }

    public synchronized boolean replacePart(WorkSheet ws, Part part) {
        PartType type = part.type;
        Integer inStockQuantity = stock.get(type);
        if (inStockQuantity != null && inStockQuantity > 0) {
            inStockQuantity = inStockQuantity - 1;
            stock.put(type, inStockQuantity);
            if (ws.replacedParts == null) {
                ws.replacedParts = new ArrayList<>();
            }
            ws.replacedParts.add(new ReplacedPart(type, part.name));
            ws.partsToReplace.remove(part);
            return true;
        } else {
            return false;
        }

    }

    public synchronized WorkSheet createWorkSheet(Mobile mobile) {
        WorkSheet ws = new WorkSheet();
        ws.mobile = mobile;
        ws.receivedOn = new Date();
        ws.status = WorkSheet.Status.RECEIVED;
        return ws;
    }

    public synchronized void collectFailingParts(WorkSheet sheet) {
        
        sheet.status = WorkSheet.Status.STARTED_TO_REPAIR;
        
        Mobile mobile = sheet.mobile;

        if (mobile.display.failing) {
            addPartToReplace(sheet, mobile.display);
        }
        if (mobile.keyboard != null && mobile.keyboard.failing) {
            addPartToReplace(sheet, mobile.keyboard);
        }
        if (mobile.microphone.failing) {
            addPartToReplace(sheet, mobile.microphone);
        }
        if (mobile.motherBoard.failing) {
            addPartToReplace(sheet, mobile.motherBoard);
        }
        if (mobile.powerSwitch.failing) {
            addPartToReplace(sheet, mobile.powerSwitch);
        }
        if (mobile.speaker.failing) {
            addPartToReplace(sheet, mobile.speaker);
        }
        if (mobile.volumeButtons.failing) {
            addPartToReplace(sheet, mobile.volumeButtons);
        }
    }

    public synchronized void addPartToReplace(WorkSheet sheet, Part part) {
        if (sheet.partsToReplace == null) {
            sheet.partsToReplace = new ArrayList<>();
        }
        sheet.partsToReplace.add(part);
    }
    
    public synchronized void orderParts() {
        if (!orderIdByPartType.isEmpty()) {
            for (Map.Entry<PartType, Long> entry : orderIdByPartType.entrySet()) {
                Long orderId = entry.getValue();
                if (orderId == null) {
                    orderId = supplier.orderPart(entry.getKey(), 5);
                    entry.setValue(orderId);
                } 
            }
        }
    }
    
    public synchronized void pollSupplier() {
        if (!orderIdByPartType.isEmpty()) {
            List<Long> orderIds = new ArrayList<>(orderIdByPartType.values());
            for (Long orderId : orderIds) {
                if (orderId != null) {
                    if (supplier.isReadyForShipment(orderId)) {
                        Order shippedOrder = supplier.shipOrder(orderId);
                        stock.put(shippedOrder.getType(), shippedOrder.getQuantity());
                        orderIdByPartType.remove(shippedOrder.getType());
                    }
                }
            }
        }
    }
}
