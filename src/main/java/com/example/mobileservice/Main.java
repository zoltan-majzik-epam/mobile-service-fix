package com.example.mobileservice;


import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.mobileservice.supplier.Supplier;


/**
 *
 * @author vrg
 */
public class Main {
    
    private MobileService mobileService;
    private final List<Client> clients = new ArrayList<>();
    private final Stock stock = Stock.getInstance();
   // private TaskScheduler scheduler = TaskScheduler.getInstance();
    
    public static void main(String[] args) {
        Main main = new Main();
        main.sendMobilesToService();
        main.startWorkAtMobileService();
    }

    public Main() {
        mobileService = new MobileService();
        for (int i = 0; i < 10; i++) {
            Client client = new Client(mobileService);
            clients.add(client);
        }
    }
    
    public void sendMobilesToService() {
        for (Client client : clients) {
            SendMobileToServiceAndPollStatusTask task = new SendMobileToServiceAndPollStatusTask(client);
            new Thread(task).start();
        }
    }
    
    public void startWorkAtMobileService() {
        while(true) {
            mobileService.processWorksheets();
            stock.orderParts();
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
