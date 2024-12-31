package tbs;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import tbs.common.DisplayUtitlities;
import tbs.movie.Movie;
import tbs.theatre.Screen;
import tbs.theatre.show.Seat;
import tbs.theatre.show.Show;
import tbs.theatre.show.Ticket;

public class TheatreService {
    private TheatreTbs tbs;

    public TheatreService(TheatreTbs tbs) {
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
    
    public boolean addMovie(String name,String descriptions,Duration minutes){
    List<Movie> movies = this.tbs.getMovies();
    for(Movie m : movies){
        if(m.getName().toLowerCase().equals(name.toLowerCase())){
            System.out.println("Movie name is already availabel...");
            return false;
        }
    }
    return this.tbs.addMovie(name, descriptions, minutes);
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

    public boolean addScreen(int theatreId,String name, int totalCapacity, List<Integer> seatPerRows){
        if(this.tbs.getTheatre(theatreId) == null){
            System.out.println("Invalid Theatre Id:Theatre not found...");
            return false;
        }
        return tbs.addScreen(theatreId,name,totalCapacity,seatPerRows);
    }

    public boolean displayScreens(int theatreId){
        List<Screen> screens = this.tbs.getScreens(theatreId);
        if(screens.isEmpty()){
            System.out.println("\n\nNo screen available...");
            return false;
        }
        System.out.println("\n\nID\tScreen\tTotal Seats");
        System.out.println("--------------------------------------");
        for(int i=0;i<screens.size();i++){
            System.out.println(screens.get(i).getId()+"\t"+screens.get(i).getName()+"\t"+screens.get(i).getTotalCapacity());
        }
        return true;
    }

    public boolean addShow(int theatreId,int screenId,int movieId,LocalDate date, LocalTime startTime,int premiumRows,String[] vipSeatsArray,int premiumPrice,int normalPrice){
        Movie movie = this.tbs.getMovie(movieId);
        if(movie == null){
            System.out.println("No movie found with the movieId:"+movieId);
            return false;
        }

        Screen screen = this.tbs.getScreen(screenId);
        if(screen == null){
            System.out.println("No screen found with the Id:"+screenId);
            return false;
        }

        LocalTime endTime = startTime.plusMinutes(movie.getRuntime().toMinutes()).plusMinutes(15);

        if(premiumRows<0 && premiumRows> screen.getSeatPerRows().size()){
            System.out.println("Invlaid input for premium rows...");
            return false;
        }

        return this.tbs.addShow(theatreId, screenId, movieId, date, startTime, endTime, premiumRows,  vipSeatsArray,premiumPrice,normalPrice, screen.getSeatPerRows());
    }

    public boolean displayShows(int theatreId,int screenId){
        List<Show> shows = this.tbs.getShows(theatreId,screenId);
        if(shows.isEmpty()){
            System.out.println("No show is available...");
            return false;
        }
        this.printShows(shows);
        return true;
    }

    public void displayShow(int showId){
        Show s = this.tbs.getShow(showId);
        System.out.println("Show Information:");
        System.out.println("Screen: "+this.tbs.getScreen(s.getScreenId()).getName());
        System.out.println("Show Time: "+s.getDate()+" "+s.getStartTime()+" - "+s.getEndTime());
        System.out.println("Total Seats: "+s.getTotalCapacity(true));
        System.out.println("Booked Seats: "+s.getBookedSeatsCount());
        System.out.println("Available Seats: "+s.getAvailableSeatsCount(true));

        System.out.println("\nBookings:");
        List<Ticket> tickets = this.tbs.getBookings(showId);
        if(tickets.isEmpty())
            System.out.println("There are no bookngs");
        
        for(Ticket x: tickets){
            System.out.println("\n\nTicked No:"+tickets.indexOf(x)+1);
            for(String seat : x.getSeatNo()){
                System.out.print(seat+" ");
            }
        }

        System.out.println("\nAvailable Seats:");
        List<List<Seat>> avialableSeats = s.getAvailableSeats(true);
        if(avialableSeats.isEmpty())
            System.out.println("There are no avaialable seats,All are booked");
        DisplayUtitlities.displaySeats(avialableSeats);

        System.out.println("\nBooked Seats:");
        List<List<Seat>> bookedSeats = s.getBookedSeats();
        if(bookedSeats.isEmpty())
            System.out.println("There are no booked seats");

        DisplayUtitlities.displaySeats(bookedSeats);
    }
    
    
}
