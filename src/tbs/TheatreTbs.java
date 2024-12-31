package tbs;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import tbs.movie.Movie;
import tbs.theatre.Screen;
import tbs.theatre.Theatre;
import tbs.theatre.show.Show;
import tbs.theatre.show.Ticket;

public interface TheatreTbs {
    boolean addMovie(String name,String description,Duration minutes);
    Movie getMovie(int movieId);
    List<Movie> getMovies();
    Theatre getTheatre(int id);
    boolean addScreen(int theatreId,String name, int totalCapacity, List<Integer> seatPerRows);
    List<Screen> getScreens(int theatreId);
    Screen getScreen(int screenId);
    boolean addShow(int theatreId,int screenId,int movieId,LocalDate date, LocalTime startTime,LocalTime endTime,int premiumRows, String[] vipSeatsArray,int premiumPrice,int normalPrice,List<Integer> seatPerRows);
    List<Show> getShows(int theatreId,int screenId);
    Show getShow(int showId);
    List<Ticket> getBookings(int showId);

    // boolean addMovie(String name,String descriptions,Duration minutes);
    // boolean displayMovies();
    // boolean addScreen(int theatreId,String name, int totalCapacity, List<Integer> seatPerRows);
    // boolean displayScreens(int theatreId);
    // boolean addShow(int theatreId,int screenId,int movieId,LocalDate date, LocalTime startTime,int premiumRows, String[] vipSeatsArray,int premiumPrice,int normalPrice);
    // boolean displayShows(int theatreId,int screenId);
    // void displayShow(int showId);
}
