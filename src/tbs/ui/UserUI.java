package tbs.ui;

import java.io.BufferedReader;
import java.util.Arrays;
import java.util.List;

import tbs.UserService;
import tbs.users.Buyer;

public class UserUI {
    private UserService tbs;
    private Buyer buyer;

    public UserUI(UserService tbs,Buyer buyer) {
        this.tbs = tbs;
        this.buyer = buyer;
    }

    public void dashboard(BufferedReader br)throws Exception{
        boolean menu = true;
        while(menu){
            System.out.println("\n\nMenu:");
            System.out.println("1.Find Theatre");
            System.out.println("2.Find Movies");
            System.out.println("3.View Tickets");
            System.out.println("4.Cancel Ticket");
            System.out.println("5.Vip Subscription");
            System.out.println("6.Exit");

            System.out.print("\nEnter an option:");
            int choice = Integer.parseInt(br.readLine());

            switch(choice){
                case 1:
                    if(this.bookTicketsByTheatre(br))
                        System.out.println("Ticket Booked Successfully...");
                    else
                        System.out.println("Couldn't book tickets...");
                    break;
                case 2:
                    if(this.bookTicketsByMovie(br))
                        System.out.println("Ticket Booked Successfully...");
                    else
                        System.out.println("Couldn't book tickets...");
                    break;
                case 3:
                    this.tbs.displayTickets(this.buyer.getId());
                    break;
                case 4:
                    if(this.cancelTicket(br))
                        System.out.println("Ticket Cancelled Successfully...");
                    else
                        System.out.println("Couldn't cancel ticket...");
                    break;
                case 5:
                    if(this.subscribe(br))
                        System.out.println("VIP subscription Successfully...");
                    else
                        System.out.println("Couldn't enable subscription...");
                    break;
                case 6:
                    menu = false;
                    break;
                default:
                    System.out.println("Invlaid option...");
            }
        }
    }

    private boolean bookTicketsByTheatre(BufferedReader br)throws Exception{
        List<String> locations = this.tbs.getLocations();
        if(locations.isEmpty()){
            System.out.println("No locations available...");
            return false;
        }
        System.out.println("\n\nSno\tLocation");
        for(int i=0;i<locations.size();i++){
            System.out.println((i+1)+".\t"+locations.get(i));
        }
        
        System.out.print("\nChoose a location:");
        int locationIndex = Integer.parseInt(br.readLine())-1;

        if(locationIndex<0 || locationIndex>=locations.size()){
            System.out.println("Invalid Location index...");
            return false;
        }
        String location = locations.get(locationIndex);

        if(!this.tbs.displayTheatres(location))
            return false;
        
        System.out.print("\nEnter Theatre ID:");
        int theatreId = Integer.parseInt(br.readLine());
        
        if(!this.tbs.displayMovies(theatreId))
            return false;
        System.out.print("\nEnter movie id:");
        int movieId = Integer.parseInt(br.readLine());

        this.tbs.displayShows(theatreId,movieId);

        System.out.print("\nEnter show id to book tickets:");
        int showId = Integer.parseInt(br.readLine());

        if(!this.tbs.displayShow(showId,this.buyer.getId()))
            return false;
        
        System.out.println("Choose Your Seats (maximum 5 seats):");
        System.out.print("Enter seat number(comma seperated(A1,B1)):");
        String selectedSeatsArray[] = br.readLine().strip().split(",");

        
        return this.tbs.bookTickets(showId,this.buyer.getId(),Arrays.asList(selectedSeatsArray) );
    }

    private boolean bookTicketsByMovie(BufferedReader br)throws Exception{
        if(!this.tbs.displayMovies())
            return false;
        System.out.print("\nEnter movie id:");
        int movieId = Integer.parseInt(br.readLine());

        List<String> locations = this.tbs.getLocations();
        if(locations.isEmpty()){
            System.out.println("No locations available...");
            return false;
        }
        System.out.println("\n\nSno\tLocation");
        for(int i=0;i<locations.size();i++){
            System.out.println((i+1)+".\t"+locations.get(i));
        }
        
        System.out.print("\nChoose a location:");
        int locationIndex = Integer.parseInt(br.readLine())-1;

        if(locationIndex<0 || locationIndex>=locations.size()){
            System.out.println("Invalid Location index...");
            return false;
        }
        String location = locations.get(locationIndex);

        if(!this.tbs.displayTheatres(movieId,location))
            return false;
        
        System.out.print("\nEnter Theatre ID:");
        int theatreId = Integer.parseInt(br.readLine());

        this.tbs.displayShows(theatreId,movieId);

        System.out.print("\nEnter show id to book tickets:");
        int showId = Integer.parseInt(br.readLine());

        if(!this.tbs.displayShow(showId,this.buyer.getId()))
            return false;
        
        System.out.println("\n\nChoose Your Seats (maximum 5 seats):");
        System.out.print("Enter seat number(comma seperated(A1,B1)):");
        String selectedSeatsArray[] = br.readLine().strip().split(",");

        
        return this.tbs.bookTickets(showId,this.buyer.getId(),Arrays.asList(selectedSeatsArray) );
    }

    private boolean cancelTicket(BufferedReader br)throws Exception{
        if(!this.tbs.displayTickets(this.buyer.getId()))
            return false;
        System.out.print("\nEnter ticket id to cancel:");
        int ticketId = Integer.parseInt(br.readLine());
        return this.tbs.cancelTicket(ticketId);
    }

    private boolean subscribe(BufferedReader br)throws Exception{
        System.out.println("\n\nDo you want to enable for VIP subscription?");
        System.out.println("1.Yes");
        System.out.println("2.No");
        System.out.print("\nChoose an option:");
        int option = Integer.parseInt(br.readLine());

        if(option == 1)
            return this.tbs.subscribe(this.buyer.getId());
        return false;
    }
}