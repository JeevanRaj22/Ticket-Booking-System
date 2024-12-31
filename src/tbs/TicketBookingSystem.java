package tbs;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import tbs.movie.Movie;
import tbs.movie.MovieStatus;
import tbs.theatre.show.Show;
import tbs.theatre.show.Ticket;
import tbs.theatre.show.TicketStatus;
import tbs.users.Buyer;
import tbs.theatre.Screen;
import tbs.theatre.Theatre;

public class TicketBookingSystem implements LoginInterface,UserTbs,TheatreTbs{
    private List<Theatre> theatres;
    private List<Movie> movies;
    private List<Screen> screens;
    private List<Show> shows;
    private List<Ticket> tickets;
    private List<Buyer> users;
    private List<Integer> subscribedUsers;

    public TicketBookingSystem() {
        this.movies = new ArrayList<Movie>();
        this.theatres = new ArrayList<Theatre>();
        this.screens = new ArrayList<Screen>();
        this.shows = new ArrayList<Show>();
        this.tickets = new ArrayList<Ticket>();
        this.users = new ArrayList<Buyer>();
        this.subscribedUsers = new ArrayList<Integer>();

        this.movies.add(new Movie(611,"Harry Potter","hp description",Duration.ofMinutes(135),MovieStatus.SHOWING));
        this.movies.add(new Movie(612,"Iron Man", "part of MCU", Duration.ofMinutes(120) ,MovieStatus.SHOWING));
        this.movies.add(new Movie(613,"Captain America", "part of MCU", Duration.ofMinutes(150) ,MovieStatus.SHOWING));

        this.theatres.add(new Theatre(121,"123","pss","tirunelveli"));
        this.screens.add(new Screen(511,121,"screen1",10,Arrays.asList(5,5)));
        this.shows.add(new Show(711,121,511,611,LocalDate.of(2025, 1, 1),LocalTime.of(10,45),LocalTime.of(12,30),1,new String[]{"A1","B1"},200,150,Arrays.asList(5,5)));

        this.users.add(new Buyer(311,"123", "jeevan"));
    }
    
    private boolean isUnique(int id){
        for(Theatre x : this.theatres){
            if(x.getId() == id)
                return false;
        }
        for(Movie x : this.movies){
            if(x.getId() == id)
                return false;
        }
        for(Screen x : this.screens){
            if(x.getId() == id)
                return false;
        }
        for(Show x : this.shows){
            if(x.getId() == id)
                return false;
        }

        return true;
    }

    private int getUniqueId(){
        Random rand = new Random();
        int id;
        do{
            id = rand.nextInt(999);
        }while(!isUnique(id));
        return id;
    }

    private List<Theatre> theatreFilter(Predicate<Theatre> p){
        return this.theatres.stream()
                .filter(p)
                .collect(Collectors.toList());
    }
    
    private List<Show> showFilter(Predicate<Show> p){
        return this.shows.stream()
                .filter(p)
                .collect(Collectors.toList());
    }
    
    private List<Ticket> ticketFilter(Predicate<Ticket> p){
        return this.tickets.stream()
                .filter(p)
                .collect(Collectors.toList());
    }
  
    public Buyer userLogin(int userId,String password){
        for(Buyer b : this.users){
            if(b.getId()==userId){
                if(!b.getPassword().equals(password)){
                    System.out.println("Entered passwod is incorrect...");
                    return null;
                }
                return b;
            }
        }
        return null;
    }

    public Theatre theatreLogin(int userId,String password){
        for(Theatre t : this.theatres){
            if(t.getId()==userId){
                if(!t.getPassword().equals(password)){
                    System.out.println("Entered passwod is incorrect...");
                    return null;
                }
                return t;
            }
        }
        System.out.println("User id not found...");
        return null;
    }

    public List<Buyer> getUsers() {
        return users;
    }
    
    public boolean isSubscriber(int buyerId){
        return this.subscribedUsers.contains(buyerId);
    }

    public boolean addSubscriber(int buyerId){
        boolean isBuyer= this.users.stream()
                        .filter(B -> B.getId() == buyerId)
                        .findFirst().isPresent();
        if(this.subscribedUsers.contains(buyerId)){
            System.out.println("Already subscribed...");
            return false;
        }
        if(isBuyer)
            return this.subscribedUsers.add(buyerId);
        else{
            System.out.println("User id not found or Invalid");
            return false;
        }
    }
    
    // methods for movies
    public boolean addMovie(String name,String description,Duration minutes){
        int id = getUniqueId();
        return this.movies.add(new Movie(id,name, description, minutes, MovieStatus.SHOWING));
    }

    public List<Movie> getMovies(){
        return this.movies.stream()
                .filter(M -> M.getStatus() == MovieStatus.SHOWING)
                .collect(Collectors.toList());
    }

    public Movie getMovie(int movieId){
        return this.movies.stream().filter(M -> M.getId() == movieId).findFirst().orElse(null);
    }

    // methods for theatres
    public int addTheatre(String password,String name,String location){
        int id = getUniqueId();
        this.theatres.add(new Theatre(id,password,name,location));
        return id;
    }

    public Theatre getTheatre(int id){
        return this.theatres.stream()
            .filter(T -> T.getId() == id).findFirst()
            .orElse(null);
    }
    
    public List<Theatre> getTheatres(){
        return this.theatres;
    }

    public List<Theatre> getTheatres(String location){
        return this.theatreFilter(T->T.getLocation().equals(location));
    }

    public List<Theatre> getTheatres(int movieId,String location){
        List<Integer> theatreIds = this.showFilter(S->S.getMovieId() == movieId).stream()
                                    .map(S -> S.getTheatreId())
                                    .collect(Collectors.toList());

        return this.theatreFilter(T-> theatreIds.contains(T.getId()) && T.getLocation().equals(location));
    }

    // methods for screens
    public boolean addScreen(int theatreId,String name, int totalCapacity, List<Integer> seatPerRows){
        int id = getUniqueId();
        Screen s = new Screen(id,theatreId,name, totalCapacity, seatPerRows);
        return this.screens.add(s);
    }

    public List<Screen> getScreens(int theatreId){
        return this.screens.stream()
                .filter(S-> S.getTheatreId() == theatreId)
                .collect(Collectors.toList());
    }

    public Screen getScreen(int screenId){
        return this.screens.stream()
                .filter(S->S.getId() == screenId)
                .findFirst().orElse(null);
    }

    // methods for shows
    public boolean addShow(int theatreId,int screenId,int movieId,LocalDate date, LocalTime startTime,LocalTime endTime,int premiumRows, String[] vipSeatsArray,int premiumPrice,int normalPrice,List<Integer> seatPerRows){
        int id = getUniqueId();
        return this.shows.add(new Show(id,theatreId,screenId,movieId,date,startTime,endTime,premiumRows, vipSeatsArray,premiumPrice,normalPrice,seatPerRows));
    }

    public List<Show> getShows(int theatreId,int screenId){
        return this.showFilter(S -> S.getTheatreId() == theatreId && S.getScreenId() == screenId);
    }

    public List<Show> getShows(int theatreId){
        return this.showFilter(S -> S.getTheatreId() == theatreId);
    }

    public Show getShow(int showId){
        return this.shows.stream()
                .filter(S -> S.getId() == showId)
                .findFirst().orElse(null);
    }

    // methods for tickets
    public int addTicket(int theatreId,int screenId,int showId,int buyerId,List<String> seatNo,int ticketPrice){
        int id = this.getUniqueId();
        Ticket t = new Ticket(id, theatreId, screenId, showId, buyerId, seatNo,ticketPrice);
        this.tickets.add(t);
        return id;
    }

    public List<Ticket> getBookings(int showId) {
        return this.ticketFilter(T-> T.getStatus() == TicketStatus.BOOKED && T.getShowId() == showId);
    }

    public List<Ticket> getTickets(int buyerId){
        return this.ticketFilter(T-> T.getBuyerId() == buyerId && T.getStatus() == TicketStatus.BOOKED);
    }

    public Ticket getTicket(int ticketId){
        return this.tickets.stream()
                .filter(T-> T.getId() == ticketId  && T.getStatus() == TicketStatus.BOOKED)
                .findFirst().orElse(null);
    }

}