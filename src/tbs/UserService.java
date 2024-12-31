package tbs;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tbs.common.DisplayUtitlities;
import tbs.movie.Movie;
import tbs.theatre.Screen;
import tbs.theatre.Theatre;
import tbs.theatre.show.Seat;
import tbs.theatre.show.SeatStatus;
import tbs.theatre.show.SeatType;
import tbs.theatre.show.Show;
import tbs.theatre.show.Ticket;
import tbs.theatre.show.TicketStatus;

public class UserService{
    private UserTbs tbs;

    public UserService(UserTbs tbs) {
        this.tbs = tbs;
    }

    private void printShows(List<Show> shows){
        System.out.println("\n\nID\tMovie\tDate\tStart Time\tEnd Time\tScreen");
        System.out.println("----------------------------------------------------------------");
        for(int i=0;i<shows.size();i++){
            Show s = shows.get(i);
            StringBuilder str = new StringBuilder();
            str.append(s.getId())
                .append("\t"+this.tbs.getMovie(s.getMovieId()).getName())
                .append("\t"+s.getDate())
                .append("\t"+s.getStartTime())
                .append("\t"+s.getEndTime())
                .append("\t"+this.tbs.getScreen(s.getScreenId()).getName());
            System.out.println(str.toString());
        }
    }
    
    private void printTheatres(List<Theatre> theatres){
        System.out.println("ID\tTheatre Name");
        for(int i=0;i<theatres.size();i++){
            System.out.println(theatres.get(i).getId()+"\t"+theatres.get(i).getName());
        }
    }

    public boolean displayShow(int showId,int buyerId){
        Show s = this.tbs.getShow(showId);
        System.out.println("Show Information:");
        System.out.println("Screen: "+this.tbs.getScreen(s.getScreenId()).getName());
        System.out.println("Show Time: "+s.getDate()+" "+s.getStartTime()+" - "+s.getEndTime());
        System.out.println("Total Seats: "+s.getTotalCapacity(true));
        System.out.println("Available Seats: "+s.getAvailableSeatsCount(true));
        System.out.println("Premium seat price: "+s.getPremiumPrice());
        System.out.println("Normal Seat price:"+s.getNormalPrice());

        System.out.println("\nAvailable Seats:");
        List<List<Seat>> avialableSeats = s.getAvailableSeats(this.tbs.isSubscriber(buyerId));
        if(avialableSeats.isEmpty()){
            System.out.println("There are no avaialable seats,All are booked");
            return false;
        }
        DisplayUtitlities.displaySeats(avialableSeats);
        return true;
    }

    public void displayShows(int theatreId,int movieId){
        List<Show> shows = this.tbs.getShows(theatreId).stream()
                            .filter(S -> S.getMovieId() == movieId)
                            .collect(Collectors.toList());
        if(shows.isEmpty()){
            System.out.println("No show is available...");
            return;
        }
        this.printShows(shows);
   }
   
    public List<String> getLocations(){
        return this.tbs.getTheatres().stream().map(T -> T.getLocation()).collect(Collectors.toList());
    }

    public boolean displayTheatres(String location){
        List<Theatre> theatres = this.tbs.getTheatres(location);
        if(theatres.isEmpty()){
            System.out.println("No Theatres available at this location...");
            return false;
        }
        
        this.printTheatres(theatres);
        return true;
    }

    public boolean displayTheatres(int movieId,String location){
        List<Theatre> theatres = this.tbs.getTheatres(movieId,location);
        if(theatres.isEmpty()){
            System.out.println("No Theatres available at this location...");
            return false;
        }
      
        this.printTheatres(theatres);
        return true;
    }
    
    public boolean displayMovies(){
        List<Movie> movies = this.tbs.getMovies();
        if(movies.isEmpty()){
            System.out.println("No movie is available...");
            return false;
         }
        DisplayUtitlities.printMovies(movies);
        return true;
    }

    public boolean displayMovies(int theatreId){
        List<Movie> movies = this.tbs.getShows(theatreId).stream()
                                        .filter(S -> S.getTheatreId() == theatreId)
                                        .map(S -> this.tbs.getMovie(S.getMovieId()))
                                        .collect(Collectors.toList());
        if(movies.isEmpty())
            return false;
        
       
        DisplayUtitlities.printMovies(movies);
        return true;
    }

    public boolean bookTickets(int showId,int buyerId,List<String> selectedSeatsArray){
        Show s = this.tbs.getShow(showId);
        List<List<Seat>> seats = s.getSeats();
        int premiumPrice = s.getPremiumPrice();
        int normalPrice = s.getNormalPrice();
        int ticketPrice = 0;
        List<Seat> selectedSeats = new ArrayList<Seat>();
        for(String x : selectedSeatsArray){
            char row = x.charAt(0);
            int rowIndex = row-65;
            try{
                Seat seat = seats.get(rowIndex).get(Integer.parseInt(x.substring(1))-1);
                if(seat.getStatus() == SeatStatus.BOOKED){
                    System.out.println("Entered Seat no is booked already:"+x);
                    return false;
                }
                if(seat.getType() == SeatType.PREMIUM || seat.getType() == SeatType.VIP)
                    ticketPrice += premiumPrice;
                else
                    ticketPrice += normalPrice;
                selectedSeats.add(seat);
            }catch(IndexOutOfBoundsException e){
                System.out.println("Entered seat no is invalid:"+x);
                return false;
            }
        }

        int id = this.tbs.addTicket(s.getTheatreId(),s.getScreenId(),s.getId(),buyerId,selectedSeatsArray,ticketPrice); 
        for(Seat seat : selectedSeats){
            seat.setStatus(SeatStatus.BOOKED);
        }
        System.out.println("Your ticket id is:"+id);
        return true;
    }

    public boolean displayTickets(int buyerId){
        List<Ticket> tickets = this.tbs.getTickets(buyerId);
        if(tickets.isEmpty()){
            System.out.println("No tickets available...");
            return false;
        }
        System.out.println("\n\nID\tSeats\tTheatre\tScreen\tDate\tTime\tPrice");
        for(Ticket t: tickets){
            Theatre theatre = this.tbs.getTheatre(t.getTheatreId());
            Screen screen = this.tbs.getScreen(t.getScreenId());
            Show s = this.tbs.getShow(t.getShowId());
            System.out.println(t.getId()+"\t"+t.getSeatNo().toString()+"\t"+theatre.getName()+"\t"+screen.getName()+"\t"+s.getDate()+"\t"+s.getStartTime()+"\t"+t.getTicketPrice());
        }

        return true;
    }

    public boolean cancelTicket(int ticketId){
        Ticket t = this.tbs.getTicket(ticketId);

        if(t == null){
            System.out.println("ticket id is invlaid or not avaialable");
            return false;
        }

        List<String> seatNo = t.getSeatNo();
        Show s = this.tbs.getShow(t.getShowId());
        List<List<Seat>> seats = s.getSeats();
        for(String x : seatNo){
            char row = x.charAt(0);
            int rowIndex = row-65;
            seats.get(rowIndex).get(Integer.parseInt(x.substring(1))-1).setStatus(SeatStatus.AVAILABLE);
        }
        t.setStatus(TicketStatus.CANCELLED);
        return true;
    }

    public boolean subscribe(int buyerId){
        return this.tbs.addSubscriber(buyerId);
    }
}
