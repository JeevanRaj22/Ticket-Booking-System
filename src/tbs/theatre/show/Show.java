package tbs.theatre.show;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Show{
    private int id;
    private int theatreId;
    private int screenId;
    private int movieId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private List<List<Seat>> seats;
    private int premiumPrice;
    private int normalPrice;

    public Show(int showId,int theatreId,int screenId,int movieId,LocalDate date, LocalTime startTime,LocalTime endTime,int premiumRows,String[] vipSeatsArray,int premiumPrice,int normalPrice,List<Integer> seatPerRows){
        this.id = showId;
        this.theatreId = theatreId;
        this.screenId = screenId;
        this.movieId = movieId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.premiumPrice = premiumPrice;
        this.normalPrice = normalPrice;

        this.seats = new ArrayList<List<Seat>>();

        for(int i=0;i<seatPerRows.size();i++){
            List<Seat> row = new ArrayList<Seat>();
            for(int j=0;j<seatPerRows.get(i);j++){
                row.add(new Seat(getCharForNumber(i)+(j+1),SeatStatus.AVAILABLE,SeatType.NORMAL));
            }
            this.seats.add(row);
        }
        for(int i=0;i<premiumRows;i++){
            List<Seat> row = this.seats.get(i);
            for(Seat x: row){
                x.setType(SeatType.PREMIUM);
            }
        }
        
        for(String x : vipSeatsArray){
            char row = x.charAt(0);
            int rowIndex = row-65;
            try{
                Seat seat = seats.get(rowIndex).get(Integer.parseInt(x.substring(1))-1);
                seat.setType(SeatType.VIP);
            }catch(IndexOutOfBoundsException e){
                System.out.println("Entered vip seat no is invalid:"+x);
            }
        }
    }
    public int getId() {
        return id;
    }

    public int getTheatreId() {
        return theatreId;
    }

    public int getScreenId() {
        return screenId;
    }
    public int getMovieId() {
        return movieId;
    }

    private String getCharForNumber(int i) {
        return i >= 0 && i < 26 ? String.valueOf((char)(i + 65)) : null;
    }

    public String getDate() {
        return date.toString();
    }

    public String getStartTime() {
        return startTime.toString();
    }

    public String getEndTime() {
        return endTime.toString();
    }

    public List<List<Seat>> getAvailableSeats(boolean subscribedUser) {
        List<List<Seat>> availableSeats = new ArrayList<List<Seat>>();

        for(List<Seat> x : this.seats){
            List<Seat> row = new ArrayList<Seat>();
            for(Seat s: x){
                if(subscribedUser){
                    if(s.getStatus() == SeatStatus.AVAILABLE)
                        row.add(s);
                }else{
                    if(s.getStatus() == SeatStatus.AVAILABLE && s.getType() != SeatType.VIP)
                        row.add(s);
                }
                
            }
            availableSeats.add(row);
        }

        return availableSeats;
    }

    public List<List<Seat>> getBookedSeats() {
        List<List<Seat>> availableSeats = new ArrayList<List<Seat>>();

        for(List<Seat> x : this.seats){
            List<Seat> row = new ArrayList<Seat>();
            for(Seat s: x){
                if(s.getStatus() == SeatStatus.BOOKED)
                    row.add(s);
            }
            if(!row.isEmpty())
                availableSeats.add(row);
        }

        return availableSeats;
    }

    public List<List<Seat>> getSeats() {
        return this.seats;
    }
    

    public int getTotalCapacity(boolean subscribedUser){
        return getAvailableSeatsCount(subscribedUser)+ getBookedSeatsCount();
    }
    public int getAvailableSeatsCount(boolean subscribedUser){
        return (int)this.getAvailableSeats(subscribedUser).stream().flatMap(List::stream).count();
    }

    public int getBookedSeatsCount(){
        return (int) this.getBookedSeats().stream().flatMap(List::stream).count();
    }

    public int getPremiumPrice() {
        return premiumPrice;
    }

    public int getNormalPrice() {
        return normalPrice;
    }

}
