package tbs;

import java.util.List;

import tbs.movie.Movie;
import tbs.theatre.Screen;
import tbs.theatre.Theatre;
import tbs.theatre.show.Show;
import tbs.theatre.show.Ticket;

public interface UserTbs {
    Movie getMovie(int movieId);
    List<Movie> getMovies();
    List<Theatre> getTheatres();
    Theatre getTheatre(int id);
    List<Theatre> getTheatres(String location);
    List<Theatre> getTheatres(int movieId,String location);
    Screen getScreen(int screenId);
    List<Screen> getScreens(int theatreId);
    Show getShow(int showId);
    List<Show> getShows(int theatreId);
    int addTicket(int theatreId,int screenId,int showId,int buyerId,List<String> seatNo,int ticketPrice);
    List<Ticket> getTickets(int buyerId);
    Ticket getTicket(int ticketId);
    boolean isSubscriber(int buyerId);
    boolean addSubscriber(int buyerId);
    
    // List<String> getLocations();
    // boolean displayTheatres(String location);
    // boolean displayTheatres(int movieId,String location);
    // boolean displayMovies();
    // boolean displayMovies(int theatreId);
    // void displayShowsByTheatreMovie(int theareId,int movieId);
    // boolean displayShowUser(int showId,int buyerId);
    // boolean bookTickets(int showId,int buyerId,List<String> selectedSeatsArray);
    // boolean displayTickets(int buyerId);
    // boolean cancelTicket(int ticketId);
    // boolean subscribe(int buyerId);
} 
