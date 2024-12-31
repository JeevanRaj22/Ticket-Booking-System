package tbs;

import java.io.BufferedReader;

import tbs.theatre.Theatre;
import tbs.ui.TheatreUI;
import tbs.ui.UserUI;
import tbs.users.Buyer;

public class TbsApp {

    private TicketBookingSystem tbs;
    public TbsApp(){
        this.tbs = new TicketBookingSystem();
    }

    public LoginInterface getLoginInterface(){
        return this.tbs;
    }
    
    public void runInterface(Buyer buyer,BufferedReader br)throws Exception{
        UserService tbsService = new UserService(tbs);
        UserUI ui = new UserUI(tbsService,buyer);
        ui.dashboard(br);
        
    }
    public void runInterface(Theatre theatre,BufferedReader br)throws Exception{
        TheatreService tbsService = new TheatreService(tbs);
        TheatreUI ui = new TheatreUI(tbsService,theatre);
        ui.dashboard(br);
    }
}
